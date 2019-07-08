package fr.epsi.mspr.msprapi;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fr.epsi.mspr.msprapi.entities.Pharmacy;
import fr.epsi.mspr.msprapi.repository.PharmacyRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PharmacyTests {
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Test
	public void addAndRemovePharmacy() {
		Pharmacy pharmacy = new Pharmacy();
		try {
			pharmacy.setName("junit test");
			pharmacy.setAddress("junit address");
			pharmacy.setGpsLat(10);
			pharmacy.setGpsLong(20);
			Pharmacy createdPharmacy = pharmacyRepository.save(pharmacy);
			pharmacyRepository.delete(createdPharmacy);
			assertEquals(pharmacy.getName(), createdPharmacy.getName());
		} catch(Exception ex) {
			System.out.println(ex);
			pharmacyRepository.delete(pharmacy);
		}
	}

}
