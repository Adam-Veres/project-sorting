package com.epam;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import com.epam.model.*;
import com.epam.repository.EcoUserRepository;
import com.epam.security.EcoUserRole;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.epam.repository.CoordinateRepository;
import com.epam.repository.EcoServiceRepository;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;


@SpringBootApplication
public class ProjectSortingApplication implements CommandLineRunner {

	@Autowired
	EcoServiceRepository ecoServiceRepository;
	
	@Autowired
	CoordinateRepository coordinateRepository;

	@Autowired
	EcoUserRepository ecoUserRepository;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(ProjectSortingApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		if(ecoUserRepository.findAll().isEmpty()){
			initUsersDb();
		}
		if((ecoServiceRepository.findAll()).isEmpty()) {
			initDb();
		}
	}

	private void initUsersDb() {
		EcoUserBuilder userTemplate = EcoUserBuilder.anEcoUser()
				.withPassword(passwordEncoder.encode("123456789"))
				.withUserRole(EcoUserRole.USER);
		EcoUserBuilder serviceTemplate = EcoUserBuilder.anEcoUser()
				.withPassword(passwordEncoder.encode("123456789"))
				.withUserRole(EcoUserRole.SERVICE);
		LinkedList<EcoUser> users = new LinkedList<>();
		IntStream.rangeClosed(1,5).forEach(i->users.add(serviceTemplate.withUsername("service"+i).withEmail("service"+i+"@service.com").build()));
		IntStream.rangeClosed(1,5).forEach(i->users.add(userTemplate.withUsername("user"+i).withEmail("user"+i+"@user.com").build()));
		ecoUserRepository.saveAll(users);
	}

	public void initDb() {
    final List<EcoUser> allServiceUsers =
        ecoUserRepository.findAllByUserRole(EcoUserRole.SERVICE);

		Coordinate cord1 = coordinateRepository.save(new Coordinate(0, BigDecimal.valueOf(50.448589), BigDecimal.valueOf(30.5333339)));
		Coordinate cord2 = coordinateRepository.save(new Coordinate(0, BigDecimal.valueOf(50.412123), BigDecimal.valueOf(30.512486)));
		Coordinate cord3 = coordinateRepository.save(new Coordinate(0, BigDecimal.valueOf(50.482625), BigDecimal.valueOf(30.4603376)));
		Coordinate cord4 = coordinateRepository.save(new Coordinate(0, BigDecimal.valueOf(50.400695), BigDecimal.valueOf(30.487846)));
		Coordinate cord5 = coordinateRepository.save(new Coordinate(0, BigDecimal.valueOf(50.419861), BigDecimal.valueOf(30.424913)));
		Coordinate cord6 = coordinateRepository.save(new Coordinate(0, BigDecimal.valueOf(50.505302), BigDecimal.valueOf(30.415446)));
		Coordinate cord7 = coordinateRepository.save(new Coordinate(0, BigDecimal.valueOf(50.523009), BigDecimal.valueOf(30.361424)));
		Coordinate cord8 = coordinateRepository.save(new Coordinate(0, BigDecimal.valueOf(50.419151), BigDecimal.valueOf(30.626520)));
		Coordinate cord9 = coordinateRepository.save(new Coordinate(0, BigDecimal.valueOf(50.415602), BigDecimal.valueOf(30.635431)));
		Coordinate cord10 = coordinateRepository.save(new Coordinate(0, BigDecimal.valueOf(50.528674), BigDecimal.valueOf(30.623179)));
		Coordinate cord11 = coordinateRepository.save(new Coordinate(0, BigDecimal.valueOf(50.522655), BigDecimal.valueOf(30.614825)));
		Coordinate cord12 = coordinateRepository.save(new Coordinate(0, BigDecimal.valueOf(50.527612), BigDecimal.valueOf(30.601459)));
		
		JSONArray ja = new JSONArray();
		ja.put("You can find delivery and disposal pricing information here!");
		JSONObject jo_delivey = new JSONObject();
		jo_delivey.put("Delivery priceing", "");
		jo_delivey.put("Self", "free");
		JSONObject jo_waste = new JSONObject();
		jo_waste.put("Glass", "free");
		jo_waste.put("Paper", "0.05 EUR/kg");
		ja.put(jo_delivey);
		ja.put(jo_waste);
		
		EcoService es1 = new EcoService(0, "Super Eco", new HashSet<WasteType>(List.of(WasteType.GLASS, WasteType.PAPER)),
				new HashSet<PaymentCondition>(List.of(PaymentCondition.CARD)), new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)), cord1, ja.toString(), BigDecimal.ZERO, BigDecimal.ZERO, allServiceUsers.get(0));
//		EcoService es2 = new EcoService(0, "Recycle Hero", new HashSet<WasteType>(List.of(WasteType.GLASS, WasteType.PAPER, WasteType.PLASTIC)), 
//				new HashSet<PaymentCondition>(List.of(PaymentCondition.CASH, PaymentCondition.CARD)), new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)), cord2,allServiceUsers.get(0));
//		EcoService es3 = new EcoService(0, "Plastic Eliminator", new HashSet<WasteType>(List.of(WasteType.PLASTIC)), 
//				new HashSet<PaymentCondition>(List.of(PaymentCondition.CASH, PaymentCondition.CARD)), new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)), cord3,allServiceUsers.get(0));
//		EcoService es4 = new EcoService(0, "Wall-e, electronic waste collector", new HashSet<WasteType>(List.of(WasteType.ELECTRONIC)), 
//				new HashSet<PaymentCondition>(List.of(PaymentCondition.CASH, PaymentCondition.CARD)), new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)), cord4,allServiceUsers.get(1));
//		EcoService es5 = new EcoService(0, "Eco Station I", new HashSet<WasteType>(List.of(WasteType.GLASS, WasteType.PAPER, WasteType.PLASTIC)), 
//				new HashSet<PaymentCondition>(List.of(PaymentCondition.FREE)), new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)), cord5,allServiceUsers.get(2));
//		EcoService es6 = new EcoService(0, "Metal man", new HashSet<WasteType>(List.of(WasteType.METALS)), 
//				new HashSet<PaymentCondition>(List.of(PaymentCondition.FREE)), new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF, DeliveryOption.VAN, DeliveryOption.TRUCK)), cord6,allServiceUsers.get(2));
//		EcoService es7 = new EcoService(0, "Make clean, not waste!", new HashSet<WasteType>(List.of(WasteType.PAPER)), 
//				new HashSet<PaymentCondition>(List.of(PaymentCondition.FREE)), new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF, DeliveryOption.VAN)), cord7,allServiceUsers.get(2));
//		EcoService es8 = new EcoService(0, "ReMetalizer", new HashSet<WasteType>(List.of(WasteType.METALS)), 
//				new HashSet<PaymentCondition>(List.of(PaymentCondition.CARD, PaymentCondition.CASH)), new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF, DeliveryOption.TRUCK)), cord8,allServiceUsers.get(3));
//		EcoService es9 = new EcoService(0, "Eco Station II", new HashSet<WasteType>(List.of(WasteType.GLASS, WasteType.PAPER, WasteType.PLASTIC)), 
//				new HashSet<PaymentCondition>(List.of(PaymentCondition.FREE)), new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)), cord9,allServiceUsers.get(3));
//		EcoService es10 = new EcoService(0, "All glass melts fast", new HashSet<WasteType>(List.of(WasteType.GLASS)), 
//				new HashSet<PaymentCondition>(List.of(PaymentCondition.CASH, PaymentCondition.CARD)), new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)), cord10,allServiceUsers.get(3));
//		EcoService es11 = new EcoService(0, "Wall-e, electronic waste collector", new HashSet<WasteType>(List.of(WasteType.ELECTRONIC)), 
//				new HashSet<PaymentCondition>(List.of(PaymentCondition.CARD)), new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF, DeliveryOption.VAN)), cord11,allServiceUsers.get(4));
//		EcoService es12 = new EcoService(0, "Eco Station III", new HashSet<WasteType>(List.of(WasteType.GLASS, WasteType.PAPER, WasteType.PLASTIC)), 
//				new HashSet<PaymentCondition>(List.of(PaymentCondition.FREE)), new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)), cord12,allServiceUsers.get(4));

		
		
//		EcoService es1 = new EcoService(0, "Super Eco", new HashSet<WasteType>(List.of(WasteType.GLASS, WasteType.PAPER)), 
//				new HashSet<PaymentCondition>(List.of(PaymentCondition.CARD)), new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)), cord1,
//				ja.toString(), BigDecimal.ZERO, BigDecimal.ZERO);
//		EcoService es2 = new EcoService(0, "Recycle Hero", new HashSet<WasteType>(List.of(WasteType.GLASS, WasteType.PAPER, WasteType.PLASTIC)), 
//				new HashSet<PaymentCondition>(List.of(PaymentCondition.CASH, PaymentCondition.CARD)), new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)),
//				cord2, "desc", BigDecimal.ZERO, BigDecimal.ZERO);
//		EcoService es3 = new EcoService(0, "Plastic Eliminator", new HashSet<WasteType>(List.of(WasteType.PLASTIC)), 
//				new HashSet<PaymentCondition>(List.of(PaymentCondition.CASH, PaymentCondition.CARD)), new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)),
//				cord3, "desc", BigDecimal.ZERO, BigDecimal.ZERO);
//		EcoService es4 = new EcoService(0, "Wall-e, electronic waste collector", new HashSet<WasteType>(List.of(WasteType.ELECTRONIC)), 
//				new HashSet<PaymentCondition>(List.of(PaymentCondition.CASH, PaymentCondition.CARD)), new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)),
//				cord4, "desc", BigDecimal.ZERO, BigDecimal.ZERO);
//		EcoService es5 = new EcoService(0, "Eco Station I", new HashSet<WasteType>(List.of(WasteType.GLASS, WasteType.PAPER, WasteType.PLASTIC)), 
//				new HashSet<PaymentCondition>(List.of(PaymentCondition.FREE)), new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)), cord5,
//				"desc", BigDecimal.ZERO, BigDecimal.ZERO);
//		EcoService es6 = new EcoService(0, "Metal man", new HashSet<WasteType>(List.of(WasteType.METALS)), 
//				new HashSet<PaymentCondition>(List.of(PaymentCondition.FREE)), new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF, DeliveryOption.VAN,
//						DeliveryOption.TRUCK)), cord6, "desc", BigDecimal.ZERO, BigDecimal.ZERO);
//		EcoService es7 = new EcoService(0, "Make clean, not waste!", new HashSet<WasteType>(List.of(WasteType.PAPER)), 
//				new HashSet<PaymentCondition>(List.of(PaymentCondition.FREE)), new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF, DeliveryOption.VAN)),
//				cord7, "desc", BigDecimal.ZERO, BigDecimal.ZERO);
//		EcoService es8 = new EcoService(0, "ReMetalizer", new HashSet<WasteType>(List.of(WasteType.METALS)), 
//				new HashSet<PaymentCondition>(List.of(PaymentCondition.CARD, PaymentCondition.CASH)), new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF,
//						DeliveryOption.TRUCK)), cord8, "desc", BigDecimal.ZERO, BigDecimal.ZERO);
//		EcoService es9 = new EcoService(0, "Eco Station II", new HashSet<WasteType>(List.of(WasteType.GLASS, WasteType.PAPER, WasteType.PLASTIC)), 
//				new HashSet<PaymentCondition>(List.of(PaymentCondition.FREE)), new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)), cord9,
//				"desc", BigDecimal.ZERO, BigDecimal.ZERO);
//		EcoService es10 = new EcoService(0, "All glass melts fast", new HashSet<WasteType>(List.of(WasteType.GLASS)), 
//				new HashSet<PaymentCondition>(List.of(PaymentCondition.CASH, PaymentCondition.CARD)), new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)),
//				cord10, "desc", BigDecimal.ZERO, BigDecimal.ZERO);
//		EcoService es11 = new EcoService(0, "Wall-e, electronic waste collector", new HashSet<WasteType>(List.of(WasteType.ELECTRONIC)), 
//				new HashSet<PaymentCondition>(List.of(PaymentCondition.CARD)), new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF, DeliveryOption.VAN)),
//				cord11, "desc", BigDecimal.ZERO, BigDecimal.ZERO);
//		EcoService es12 = new EcoService(0, "Eco Station III", new HashSet<WasteType>(List.of(WasteType.GLASS, WasteType.PAPER, WasteType.PLASTIC)), 
//				new HashSet<PaymentCondition>(List.of(PaymentCondition.FREE)), new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)), cord12,
//				"desc", BigDecimal.ZERO, BigDecimal.ZERO);

		
		ecoServiceRepository.save(es1);
//		ecoServiceRepository.save(es2);
//		ecoServiceRepository.save(es3);
//		ecoServiceRepository.save(es4);
//		ecoServiceRepository.save(es5);
//		ecoServiceRepository.save(es6);
//		ecoServiceRepository.save(es7);
//		ecoServiceRepository.save(es8);
//		ecoServiceRepository.save(es9);
//		ecoServiceRepository.save(es10);
//		ecoServiceRepository.save(es11);
//		ecoServiceRepository.save(es12);
	}

}
