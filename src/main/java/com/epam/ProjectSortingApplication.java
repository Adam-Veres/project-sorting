package com.epam;

import com.epam.model.Coordinate;
import com.epam.model.DeliveryOption;
import com.epam.model.EcoService;
import com.epam.model.EcoUser;
import com.epam.model.PaymentCondition;
import com.epam.model.WasteType;
import com.epam.repository.CoordinateRepository;
import com.epam.repository.EcoServiceRepository;
import com.epam.repository.EcoUserRepository;
import com.epam.security.EcoUserRole;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@SpringBootApplication
public class ProjectSortingApplication implements CommandLineRunner {

  private final EcoServiceRepository ecoServiceRepository;

  private final CoordinateRepository coordinateRepository;

  private final EcoUserRepository ecoUserRepository;

  private final PasswordEncoder passwordEncoder;

  public static void main(String[] args) {
    SpringApplication.run(ProjectSortingApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    if (ecoUserRepository.findAll().isEmpty()) {
      initUsersDb();
    }
    if ((ecoServiceRepository.findAll()).isEmpty()) {
      initDb();
    }
  }

  private void initUsersDb() {
    EcoUser.EcoUserBuilder userTemplate =
        EcoUser.builder().password(passwordEncoder.encode("123456789")).userRole(EcoUserRole.USER);
    EcoUser.EcoUserBuilder serviceTemplate =
        EcoUser.builder()
            .password(passwordEncoder.encode("123456789"))
            .userRole(EcoUserRole.SERVICE);
    LinkedList<EcoUser> users = new LinkedList<>();
    IntStream.rangeClosed(1, 5)
        .forEach(
            i ->
                users.add(
                    serviceTemplate
                        .username("service" + i)
                        .email("service" + i + "@service.com")
                        .build()));
    IntStream.rangeClosed(1, 5)
        .forEach(
            i ->
                users.add(
                    userTemplate.username("user" + i).email("user" + i + "@user.com").build()));
    ecoUserRepository.saveAll(users);
  }

  public void initDb() {
    final List<EcoUser> allServiceUsers = ecoUserRepository.findAllByUserRole(EcoUserRole.SERVICE);

    Coordinate cord1 =
        new Coordinate(0, BigDecimal.valueOf(50.448589), BigDecimal.valueOf(30.533339));
    Coordinate cord2 =
        new Coordinate(0, BigDecimal.valueOf(50.412123), BigDecimal.valueOf(30.512486));
    Coordinate cord3 =
        new Coordinate(0, BigDecimal.valueOf(50.482625), BigDecimal.valueOf(30.4603376));
    Coordinate cord4 =
        new Coordinate(0, BigDecimal.valueOf(50.400695), BigDecimal.valueOf(30.487846));
    Coordinate cord5 =
        new Coordinate(0, BigDecimal.valueOf(50.419861), BigDecimal.valueOf(30.424913));
    Coordinate cord6 =
        new Coordinate(0, BigDecimal.valueOf(50.505302), BigDecimal.valueOf(30.415446));
    Coordinate cord7 =
        new Coordinate(0, BigDecimal.valueOf(50.523009), BigDecimal.valueOf(30.361424));
    Coordinate cord8 =
        new Coordinate(0, BigDecimal.valueOf(50.419151), BigDecimal.valueOf(30.626520));
    Coordinate cord9 =
        new Coordinate(0, BigDecimal.valueOf(50.415602), BigDecimal.valueOf(30.635431));
    Coordinate cord10 =
        new Coordinate(0, BigDecimal.valueOf(50.528674), BigDecimal.valueOf(30.623179));
    Coordinate cord11 =
        new Coordinate(0, BigDecimal.valueOf(50.522655), BigDecimal.valueOf(30.614825));
    Coordinate cord12 =
        new Coordinate(0, BigDecimal.valueOf(50.527612), BigDecimal.valueOf(30.601459));

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

    EcoService es1 =
        new EcoService(
            0L,
            "Super Eco",
            new HashSet<WasteType>(List.of(WasteType.GLASS, WasteType.PAPER)),
            new HashSet<PaymentCondition>(List.of(PaymentCondition.CARD)),
            new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)),
            cord1,
            ja.toString(),
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            allServiceUsers.get(0));
    EcoService es2 =
        new EcoService(
            0,
            "Recycle Hero",
            new HashSet<WasteType>(List.of(WasteType.GLASS, WasteType.PAPER, WasteType.PLASTIC)),
            new HashSet<PaymentCondition>(List.of(PaymentCondition.CASH, PaymentCondition.CARD)),
            new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)),
            cord2,
            "",
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            allServiceUsers.get(0));
    EcoService es3 =
        new EcoService(
            0,
            "Plastic Eliminator",
            new HashSet<WasteType>(List.of(WasteType.PLASTIC)),
            new HashSet<PaymentCondition>(List.of(PaymentCondition.CASH, PaymentCondition.CARD)),
            new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)),
            cord3,
            "",
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            allServiceUsers.get(0));
    EcoService es4 =
        new EcoService(
            0,
            "Wall-e, electronic waste collector",
            new HashSet<WasteType>(List.of(WasteType.ELECTRONIC)),
            new HashSet<PaymentCondition>(List.of(PaymentCondition.CASH, PaymentCondition.CARD)),
            new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)),
            cord4,
            "",
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            allServiceUsers.get(1));
    EcoService es5 =
        new EcoService(
            0,
            "Eco Station I",
            new HashSet<WasteType>(List.of(WasteType.GLASS, WasteType.PAPER, WasteType.PLASTIC)),
            new HashSet<PaymentCondition>(List.of(PaymentCondition.FREE)),
            new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)),
            cord5,
            "",
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            allServiceUsers.get(2));
    EcoService es6 =
        new EcoService(
            0,
            "Metal man",
            new HashSet<WasteType>(List.of(WasteType.METALS)),
            new HashSet<PaymentCondition>(List.of(PaymentCondition.FREE)),
            new HashSet<DeliveryOption>(
                List.of(DeliveryOption.SELF, DeliveryOption.VAN, DeliveryOption.TRUCK)),
            cord6,
            "",
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            allServiceUsers.get(2));
    EcoService es7 =
        new EcoService(
            0,
            "Make clean, not waste!",
            new HashSet<WasteType>(List.of(WasteType.PAPER)),
            new HashSet<PaymentCondition>(List.of(PaymentCondition.FREE)),
            new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF, DeliveryOption.VAN)),
            cord7,
            "",
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            allServiceUsers.get(2));
    EcoService es8 =
        new EcoService(
            0,
            "ReMetalizer",
            new HashSet<WasteType>(List.of(WasteType.METALS)),
            new HashSet<PaymentCondition>(List.of(PaymentCondition.CARD, PaymentCondition.CASH)),
            new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF, DeliveryOption.TRUCK)),
            cord8,
            "",
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            allServiceUsers.get(3));
    EcoService es9 =
        new EcoService(
            0,
            "Eco Station II",
            new HashSet<WasteType>(List.of(WasteType.GLASS, WasteType.PAPER, WasteType.PLASTIC)),
            new HashSet<PaymentCondition>(List.of(PaymentCondition.FREE)),
            new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)),
            cord9,
            "",
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            allServiceUsers.get(3));
    EcoService es10 =
        new EcoService(
            0,
            "All glass melts fast",
            new HashSet<WasteType>(List.of(WasteType.GLASS)),
            new HashSet<PaymentCondition>(List.of(PaymentCondition.CASH, PaymentCondition.CARD)),
            new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)),
            cord10,
            "",
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            allServiceUsers.get(3));
    EcoService es11 =
        new EcoService(
            0,
            "Wall-e, electronic waste collector",
            new HashSet<WasteType>(List.of(WasteType.ELECTRONIC)),
            new HashSet<PaymentCondition>(List.of(PaymentCondition.CARD)),
            new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF, DeliveryOption.VAN)),
            cord11,
            "",
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            allServiceUsers.get(4));
    EcoService es12 =
        new EcoService(
            0,
            "Eco Station III",
            new HashSet<WasteType>(List.of(WasteType.GLASS, WasteType.PAPER, WasteType.PLASTIC)),
            new HashSet<PaymentCondition>(List.of(PaymentCondition.FREE)),
            new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)),
            cord12,
            "",
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            allServiceUsers.get(4));

    ecoServiceRepository.save(es1);
    ecoServiceRepository.save(es2);
    ecoServiceRepository.save(es3);
    ecoServiceRepository.save(es4);
    ecoServiceRepository.save(es5);
    ecoServiceRepository.save(es6);
    ecoServiceRepository.save(es7);
    ecoServiceRepository.save(es8);
    ecoServiceRepository.save(es9);
    ecoServiceRepository.save(es10);
    ecoServiceRepository.save(es11);
    ecoServiceRepository.save(es12);
  }
}
