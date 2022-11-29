package com.epam;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.epam.model.Coordinate;
import com.epam.model.DeliveryOption;
import com.epam.model.EcoService;
import com.epam.model.PaymentCondition;
import com.epam.model.WasteType;
import com.epam.repository.CoordinateRepository;
import com.epam.repository.EcoServiceRepository;

@SpringBootApplication
public class ProjectSortingApplication implements CommandLineRunner {

	@Autowired
	EcoServiceRepository ecoServiceRepository;
	
	@Autowired
	CoordinateRepository coordinateRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ProjectSortingApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		if((ecoServiceRepository.findAll()).isEmpty()) {
			initDb();
		}
	}
	
	public void initDb() {
		Coordinate cord1 = coordinateRepository.save(new Coordinate(0, BigDecimal.valueOf(50.448589), BigDecimal.valueOf(30.5333339)));
		Coordinate cord2 = coordinateRepository.save(new Coordinate(0, BigDecimal.valueOf(50.412123), BigDecimal.valueOf(30.512486)));
		Coordinate cord3 = coordinateRepository.save(new Coordinate(0, BigDecimal.valueOf(50.482625), BigDecimal.valueOf(30.4603376)));
		
		EcoService es1 = new EcoService(0, "Super Eco", new HashSet<WasteType>(List.of(WasteType.GLASS, WasteType.PAPER)), 
				new HashSet<PaymentCondition>(List.of(PaymentCondition.CARD)), new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)), cord1);
		EcoService es2 = new EcoService(0, "Recycle Hero", new HashSet<WasteType>(List.of(WasteType.GLASS, WasteType.PAPER, WasteType.PLASTIC)), 
				new HashSet<PaymentCondition>(List.of(PaymentCondition.CASH, PaymentCondition.CARD)), new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)), cord2);
		EcoService es3 = new EcoService(0, "Plastic Eliminator", new HashSet<WasteType>(List.of(WasteType.PLASTIC)), 
				new HashSet<PaymentCondition>(List.of(PaymentCondition.CASH, PaymentCondition.CARD)), new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)), cord3);
		
		ecoServiceRepository.save(es1);
		ecoServiceRepository.save(es2);
		ecoServiceRepository.save(es3);
	}

}
