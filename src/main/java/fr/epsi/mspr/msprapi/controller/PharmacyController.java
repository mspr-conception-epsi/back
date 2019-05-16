package fr.epsi.mspr.msprapi.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.epsi.mspr.msprapi.entities.Pharmacy;
import fr.epsi.mspr.msprapi.repository.PharmacyRepository;
import io.swagger.annotations.ApiOperation;
import java.lang.Math;
@RestController
public class PharmacyController {
	final int r_earth = 6371000;
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;

	@ApiOperation(value = "List of pharmacies")
	@GetMapping("/pharmacy/")
	public List<Pharmacy> findAll() {
		return pharmacyRepository.findAll();
	}
	
	@ApiOperation(value = "Create new pharmacy")
	@PostMapping("/pharmacy/create")
    public Pharmacy createPharmacy(@Valid @RequestBody Pharmacy pharmacy) {
        return pharmacyRepository.save(pharmacy);
    }
	
	@ApiOperation(value = "List of pharmacies in area size")
	@GetMapping("/pharmacy/{coordLong}/{coordLat}/{areaSize}")
	public List<Pharmacy> findAllInArea(@PathVariable("coordLong") double coordLong, @PathVariable("coordLat") double coordLat, @PathVariable("areaSize") double areaSize) {
		
		double new_down_latitude  = coordLat  + ((-areaSize) / r_earth) * (180 / Math.PI);
		double new_down_longitude = coordLong + ((-areaSize) / r_earth) * (180 / Math.PI) / Math.cos(coordLong * Math.PI/180);

		double new_up_latitude  = coordLat  + ((areaSize) / r_earth) * (180 / Math.PI);
		double new_up_longitude = coordLong + ((areaSize) / r_earth) * (180 / Math.PI) / Math.cos(coordLong * Math.PI/180);
		
		
	    
	    List <Pharmacy> listPharmacy = pharmacyRepository.findAll();
	    List <Pharmacy> listReturn = new ArrayList<Pharmacy>();
	    for (Pharmacy p : listPharmacy) {
	       if(distance(p.getGpsLat(), coordLat, p.getGpsLong(), coordLong, 0, 0) <= areaSize){
	    	   listReturn.add(p);
	       }
	    }
		return listReturn;
	}
	
	/**
	 * Calculate distance between two points in latitude and longitude taking
	 * into account height difference. If you are not interested in height
	 * difference pass 0.0. Uses Haversine method as its base.
	 * 
	 * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
	 * el2 End altitude in meters
	 * @returns Distance in Meters
	 */
	private double distance(double lat1, double lat2, double lon1,
	        double lon2, double el1, double el2) {

	    final int R = r_earth/1000; // Radius of the earth

	    double latDistance = Math.toRadians(lat2 - lat1);
	    double lonDistance = Math.toRadians(lon2 - lon1);
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters

	    double height = el1 - el2;

	    distance = Math.pow(distance, 2) + Math.pow(height, 2);

	    return Math.sqrt(distance);
	}
}
