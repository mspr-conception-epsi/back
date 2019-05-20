package fr.epsi.mspr.msprapi.entities.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import fr.epsi.mspr.msprapi.entities.Pharmacy;
import fr.epsi.mspr.msprapi.entities.service.PharmacyService;
import fr.epsi.mspr.msprapi.repository.PharmacyRepository;

public class PharmacyServiceImpl implements PharmacyService {

	@Autowired
	private PharmacyRepository pharmacyRepository;

	@Override
	public List<Pharmacy> getPharmacyList(double coordLat, double coordLong, double areaSize) {
		List<Pharmacy> listPharmacy = pharmacyRepository.findAll();
		List<Pharmacy> listReturn = new ArrayList<>();
		for (Pharmacy p : listPharmacy) {
			if (distance(p.getGpsLat(), coordLat, p.getGpsLong(), coordLong, 0, 0) <= areaSize) {
				listReturn.add(p);
			}
		}
		return listReturn;
	}

	private double distance(double lat1, double lat2, double lon1, double lon2, double el1, double el2) {
		final int R = 6371000 / 1000; // Radius of the earth
		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c * 1000; // convert to meters
		double height = el1 - el2;
		distance = Math.pow(distance, 2) + Math.pow(height, 2);
		return Math.sqrt(distance);
	}
}
