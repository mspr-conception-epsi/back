package fr.epsi.mspr.msprapi.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.epsi.mspr.msprapi.entities.User;
import fr.epsi.mspr.msprapi.repository.UserRepository;

public class CustomFilter extends GenericFilterBean {

	private static final String LOGOUT = "/logout";
	private static final String AUTH_SIGNIN = "/auth/signin";
	private UserRepository userRepository;
	private static final String[] WHITELIST = { "/swagger-resources", "/swagger-ui.html", "/v2/api-docs", "/webjars",
			"/swagger-ui.html", "/error" };
	private static final String[] ADMIN_RESTRICTED = { "/user/create", "/user/delete", "/user", "/formation/create",
			"/formation/delete" };

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		if (userRepository == null) {
			ServletContext servletContext = request.getServletContext();
			WebApplicationContext webApplicationContext = WebApplicationContextUtils
					.getWebApplicationContext(servletContext);
			userRepository = webApplicationContext.getBean(UserRepository.class);
		}

		HttpServletRequest httpRequest = asHttp(request);
		HttpServletResponse httpResponse = asHttp(response);

		String pathWithinApplication = new UrlPathHelper().getPathWithinApplication(httpRequest);

		System.out.println("received request at " + pathWithinApplication);

		for (final String value : WHITELIST) {
			if (pathWithinApplication.contains(value)) {
				chain.doFilter(request, response);
				return;
			}
		}

		System.out.println("method> " + httpRequest.getMethod());

		if (AUTH_SIGNIN.startsWith(pathWithinApplication)) {
			System.out.println("try to auth");
			Optional<String[]> data = getConnectionData(httpRequest);
			if (data.isPresent()) {
				Optional<String> username = Optional.ofNullable(data.get()[0]);
				Optional<String> password = Optional.ofNullable(data.get()[1]);
				if (username.isPresent() && password.isPresent()) {
					Optional<User> optionalUser = userRepository.findByName(username.get());
					if (optionalUser.isPresent()) {
						User user = optionalUser.get();
						System.out.println(password.get());
						try {
							byte[] inputCryptPassword = getCryptPassword(password.get());
							System.out.println("pass > " + inputCryptPassword);
							if (user.getPassword().getBytes() == inputCryptPassword) {
								String generatedToken = UUID.randomUUID().toString();
								user.setToken(generatedToken);
								userRepository.save(user);
								setValidReponseWithToken(httpRequest, httpResponse, generatedToken);
								System.out.println("return " + generatedToken);
							} else {
								sendInvalidReponse(httpRequest, httpResponse, "Mot de passe incorrect.");
							}
						} catch (NoSuchAlgorithmException e) {
							e.printStackTrace();
						}
					} else {
						sendInvalidReponse(httpRequest, httpResponse, "Utilisateur introuvable.");
					}
				} else {
					sendInvalidReponse(httpRequest, httpResponse, "Mot de passe ou utilisateur non spécifié");
				}
			} else {
				sendInvalidReponse(httpRequest, httpResponse, "Invalid data : Please use Basic header authorization");
			}
		} else {
			String token = getBearerToken(httpRequest);
			if (token == null) {
				sendInvalidReponse(httpRequest, httpResponse, "Token not found");
			} else {
				Optional<User> optionalUser = userRepository.findByToken(token);
				if (optionalUser.isPresent()) {
					User user = optionalUser.get();
					if (Arrays.asList(ADMIN_RESTRICTED).contains(pathWithinApplication)) {
						if (user.isAdmin()) {
							chain.doFilter(request, response);
						} else {
							sendInvalidReponse(httpRequest, httpResponse, "Vous devez etre un admin");
						}
					} else {
						if (LOGOUT.equals(pathWithinApplication)) {
							user.setToken(null);
							userRepository.save(user);
							sendLogoutReponse(httpRequest, httpResponse);
						} else {
							chain.doFilter(request, response);
						}
					}
				} else {
					sendInvalidReponse(httpRequest, httpResponse, "Token invalide. Veuillez vous reconnecter");
				}
			}
		}
	}

	private void sendLogoutReponse(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
			throws JsonProcessingException, IOException {
		httpResponse.setStatus(HttpServletResponse.SC_OK);
		Map<String, String> reponse = new HashMap<>();
		reponse.put("success", "Déconnexion effectuée");
		setHeaders(httpResponse);
		httpResponse.getWriter().print(new ObjectMapper().writeValueAsString(reponse));
	}

	private void setValidReponseWithToken(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			String generatedToken) throws IOException {
		httpResponse.setStatus(HttpServletResponse.SC_OK);
		Map<String, String> reponse = new HashMap<>();
		reponse.put("token", generatedToken);
		setHeaders(httpResponse);
		httpResponse.getWriter().print(new ObjectMapper().writeValueAsString(reponse));
	}

	private void sendInvalidReponse(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String message)
			throws IOException {
		System.out.println("invalid response : " + message);
		httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		Map<String, String> reponse = new HashMap<>();
		reponse.put("error", message);
		setHeaders(httpResponse);
		httpResponse.getWriter().print(new ObjectMapper().writeValueAsString(reponse));
	}

	private void setHeaders(HttpServletResponse httpResponse) {
		httpResponse.setHeader("Access-Control-Allow-Origin", "*");
		httpResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");
		httpResponse.setHeader("Access-Control-Allow-Headers", "*");
		httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
		httpResponse.setHeader("Access-Control-Max-Age", "180");
	}

	private String getBearerToken(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		System.out.println("header token > " + authHeader);
		if (authHeader != null && authHeader.toLowerCase().startsWith("bearer")) {
			return authHeader.substring("Bearer ".length());
		}
		return null;
	}

	private Optional<String[]> getConnectionData(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		System.out.println("header user/pass > " + authHeader);
		if (authHeader != null && authHeader.toLowerCase().startsWith("basic")) {
			String base64Credentials = authHeader.substring("Basic".length()).trim();
			byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
			String credentials = new String(credDecoded, StandardCharsets.UTF_8);
			return Optional.ofNullable(credentials.split(":", 2));
		}
		return Optional.empty();
	}

	private HttpServletRequest asHttp(ServletRequest request) {
		return (HttpServletRequest) request;
	}

	private HttpServletResponse asHttp(ServletResponse response) {
		return (HttpServletResponse) response;
	}
	
	private byte[] getCryptPassword(String password) throws NoSuchAlgorithmException {
		return getDigest().digest(password.getBytes(StandardCharsets.UTF_8));
	}
	@Bean
	private MessageDigest getDigest() throws NoSuchAlgorithmException {
		return MessageDigest.getInstance("SHA-256");
	}

}