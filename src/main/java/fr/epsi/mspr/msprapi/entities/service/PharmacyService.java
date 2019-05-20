package fr.epsi.mspr.msprapi.entities.service;

import java.util.List;

import fr.epsi.mspr.msprapi.entities.Pharmacy;

public interface PharmacyService {

	List<Pharmacy> getPharmacyList(double coordLat, double coordLong, double areaSize);

}
