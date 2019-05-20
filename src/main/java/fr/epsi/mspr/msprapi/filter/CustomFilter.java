package fr.epsi.mspr.msprapi.filter;

import java.io.IOException;
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

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.epsi.mspr.msprapi.entities.User;
import fr.epsi.mspr.msprapi.repository.UserRepository;

public class CustomFilter extends GenericFilterBean {

	private static final String AUTH_SIGNIN = "/auth/signin";
	private UserRepository userRepository;

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

		if (AUTH_SIGNIN.equals(pathWithinApplication)) {
			Optional<String> username = Optional.ofNullable(httpRequest.getHeader("X-Auth-Username"));
			Optional<String> password = Optional.ofNullable(httpRequest.getHeader("X-Auth-Password"));
			if (username.isPresent() && password.isPresent()) {
				Optional<User> optionalUser = userRepository.findByName(username.get());
				if (optionalUser.isPresent()) {
					User user = optionalUser.get();
					if (user.getPassword().equals(password.get())) {
						String generatedToken = UUID.randomUUID().toString();
						user.setToken(generatedToken);
						userRepository.save(user);
						setValidReponseWithToken(httpResponse, generatedToken);
					} else {
						sendInvalidReponse(httpResponse, "Invalid password");
					}
				} else {
					sendInvalidReponse(httpResponse, "User not found");
				}
			} else {
				sendInvalidReponse(httpResponse, "Bad credentials");
			}
		} else {
			Optional<String> token = Optional.ofNullable(httpRequest.getHeader("X-Auth-Token"));
			if (!token.isPresent() || !userRepository.findByToken(token.get()).isPresent()) {
				sendInvalidReponse(httpResponse, "Invalid token");
				return;
			}
			chain.doFilter(request, response);
		}
	}

	private void setValidReponseWithToken(HttpServletResponse httpResponse, String generatedToken) throws IOException {
		httpResponse.setStatus(HttpServletResponse.SC_OK);
		Map<String, String> reponse = new HashMap<>();
		reponse.put("token", generatedToken);
		httpResponse.addHeader("Content-Type", "application/json");
		httpResponse.getWriter().print(new ObjectMapper().writeValueAsString(reponse));
	}
	
	private void sendInvalidReponse(HttpServletResponse httpResponse, String message) throws IOException {
		httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		Map<String, String> reponse = new HashMap<>();
		reponse.put("error", message);
		httpResponse.addHeader("Content-Type", "application/json");
		httpResponse.getWriter().print(new ObjectMapper().writeValueAsString(reponse));
	}

	private HttpServletRequest asHttp(ServletRequest request) {
		return (HttpServletRequest) request;
	}

	private HttpServletResponse asHttp(ServletResponse response) {
		return (HttpServletResponse) response;
	}

}