package com.epam;

import com.epam.model.Coordinate;
import com.epam.model.DeliveryOption;
import com.epam.model.EcoService;
import com.epam.model.EcoUser;
import com.epam.model.PaymentCondition;
import com.epam.model.WasteType;
import com.epam.repository.EcoServiceRepository;
import com.epam.repository.EcoUserRepository;
import com.epam.security.EcoUserRole;
import com.epam.service.OldCommentsRemover;
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

  private final EcoUserRepository ecoUserRepository;

  private final PasswordEncoder passwordEncoder;

  private final OldCommentsRemover oldCommentsRemover;

  public static void main(String[] args) {
    SpringApplication.run(ProjectSortingApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    oldCommentsRemover.run();
    if (ecoUserRepository.findAll().isEmpty()) {
      initUsersDb();
    }
    if ((ecoServiceRepository.findAll()).isEmpty()) {
      initDb();
    }
  }

  private void initUsersDb() {
    final EcoUser.EcoUserBuilder userTemplate =
        EcoUser.builder().password(passwordEncoder.encode("123456789")).userRole(EcoUserRole.USER);
    final EcoUser.EcoUserBuilder serviceTemplate =
        EcoUser.builder()
            .password(passwordEncoder.encode("123456789"))
            .userRole(EcoUserRole.SERVICE);
    final LinkedList<EcoUser> users = new LinkedList<>();
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

    final Coordinate cord1 =
        new Coordinate(0, BigDecimal.valueOf(50.448589), BigDecimal.valueOf(30.533339));
    final Coordinate cord2 =
        new Coordinate(0, BigDecimal.valueOf(50.412123), BigDecimal.valueOf(30.512486));
    final Coordinate cord3 =
        new Coordinate(0, BigDecimal.valueOf(50.482625), BigDecimal.valueOf(30.4603376));
    final Coordinate cord4 =
        new Coordinate(0, BigDecimal.valueOf(50.400695), BigDecimal.valueOf(30.487846));
    final Coordinate cord5 =
        new Coordinate(0, BigDecimal.valueOf(50.419861), BigDecimal.valueOf(30.424913));
    final Coordinate cord6 =
        new Coordinate(0, BigDecimal.valueOf(50.505302), BigDecimal.valueOf(30.415446));
    final Coordinate cord7 =
        new Coordinate(0, BigDecimal.valueOf(50.523009), BigDecimal.valueOf(30.361424));
    final Coordinate cord8 =
        new Coordinate(0, BigDecimal.valueOf(50.419151), BigDecimal.valueOf(30.626520));
    final Coordinate cord9 =
        new Coordinate(0, BigDecimal.valueOf(50.415602), BigDecimal.valueOf(30.635431));
    final Coordinate cord10 =
        new Coordinate(0, BigDecimal.valueOf(50.528674), BigDecimal.valueOf(30.623179));
    final Coordinate cord11 =
        new Coordinate(0, BigDecimal.valueOf(50.522655), BigDecimal.valueOf(30.614825));
    final Coordinate cord12 =
        new Coordinate(0, BigDecimal.valueOf(50.527612), BigDecimal.valueOf(30.601459));

    final JSONArray ja = new JSONArray();
    ja.put("You can find delivery and disposal pricing information here!");
    JSONObject jo_delivery = new JSONObject();
    JSONObject jo_waste = new JSONObject();
    jo_waste.put(WasteType.GLASS.toString(), "free");
    jo_waste.put(WasteType.PAPER.toString(), "0.05 EUR/kg");
    ja.put(jo_delivery);
    ja.put(jo_waste);

    final EcoService es1 =
        new EcoService(
            0,
            "Super Eco",
            new HashSet<WasteType>(List.of(WasteType.GLASS, WasteType.PAPER)),
            new HashSet<PaymentCondition>(List.of(PaymentCondition.CARD)),
            new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)),
            cord1,
            ja.toString(),
            allServiceUsers.get(0));

    jo_waste.put(WasteType.PLASTIC.toString(), "free");
    final EcoService es2 =
        new EcoService(
            0,
            "Recycle Hero",
            new HashSet<WasteType>(List.of(WasteType.GLASS, WasteType.PAPER, WasteType.PLASTIC)),
            new HashSet<PaymentCondition>(List.of(PaymentCondition.CASH, PaymentCondition.CARD)),
            new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)),
            cord2,
            ja.toString(),
            allServiceUsers.get(0));

    jo_waste.remove(WasteType.PAPER.toString());
    jo_waste.remove(WasteType.GLASS.toString());
    jo_waste.remove(WasteType.PLASTIC.toString());
    jo_waste.put(WasteType.PLASTIC.toString(), "0.01 EUR/kg");
    final EcoService es3 =
        new EcoService(
            0,
            "Plastic Eliminator",
            new HashSet<WasteType>(List.of(WasteType.PLASTIC)),
            new HashSet<PaymentCondition>(List.of(PaymentCondition.CASH, PaymentCondition.CARD)),
            new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)),
            cord3,
            ja.toString(),
            allServiceUsers.get(0));

    jo_waste.remove(WasteType.PLASTIC.toString());
    jo_waste.put(WasteType.ELECTRONIC.toString(), "0.05 EUR/kg");
    final EcoService es4 =
        new EcoService(
            0,
            "Wall-e, electronic waste collector",
            new HashSet<WasteType>(List.of(WasteType.ELECTRONIC)),
            new HashSet<PaymentCondition>(List.of(PaymentCondition.CASH, PaymentCondition.CARD)),
            new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)),
            cord4,
            ja.toString(),
            allServiceUsers.get(1));

    jo_waste.remove(WasteType.ELECTRONIC.toString());
    jo_waste.put(WasteType.GLASS.toString(), "free");
    jo_waste.put(WasteType.PAPER.toString(), "free");
    jo_waste.put(WasteType.PLASTIC.toString(), "free");
    final EcoService es5 =
        new EcoService(
            0,
            "Eco Station I",
            new HashSet<WasteType>(List.of(WasteType.GLASS, WasteType.PAPER, WasteType.PLASTIC)),
            new HashSet<PaymentCondition>(List.of(PaymentCondition.FREE)),
            new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)),
            cord5,
            ja.toString(),
            allServiceUsers.get(2));

    jo_waste.remove(WasteType.PAPER.toString());
    jo_waste.remove(WasteType.GLASS.toString());
    jo_waste.remove(WasteType.PLASTIC.toString());
    jo_waste.put(WasteType.METALS.toString(), "From 0.2 to 5 EUR/kg depends on type of metal.");
    jo_delivery.put(DeliveryOption.VAN.toString(), "1 EUR/ 20kg");
    jo_delivery.put(DeliveryOption.TRUCK.toString(), "1 EUR/ 25kg");
    final EcoService es6 =
        new EcoService(
            0,
            "Metal man",
            new HashSet<WasteType>(List.of(WasteType.METALS)),
            new HashSet<PaymentCondition>(List.of(PaymentCondition.FREE)),
            new HashSet<DeliveryOption>(
                List.of(DeliveryOption.SELF, DeliveryOption.VAN, DeliveryOption.TRUCK)),
            cord6,
            ja.toString(),
            allServiceUsers.get(2));

    jo_waste.remove(WasteType.METALS.toString());
    jo_waste.put(WasteType.PAPER.toString(), "0.03 EUR/kg");
    jo_delivery.remove(DeliveryOption.TRUCK.toString());
    final EcoService es7 =
        new EcoService(
            0,
            "Make clean, not waste!",
            new HashSet<WasteType>(List.of(WasteType.PAPER)),
            new HashSet<PaymentCondition>(List.of(PaymentCondition.FREE)),
            new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF, DeliveryOption.VAN)),
            cord7,
            ja.toString(),
            allServiceUsers.get(2));

    jo_waste.remove(WasteType.PAPER.toString());
    jo_waste.put(WasteType.METALS.toString(), "From 0.2 to 5.5 EUR/kg depends on type of metal.");
    jo_delivery.remove(DeliveryOption.VAN.toString());
    jo_delivery.put(DeliveryOption.TRUCK.toString(), "1 EUR/ 30kg");
    final EcoService es8 =
        new EcoService(
            0,
            "ReMetalizer",
            new HashSet<WasteType>(List.of(WasteType.METALS)),
            new HashSet<PaymentCondition>(List.of(PaymentCondition.CARD, PaymentCondition.CASH)),
            new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF, DeliveryOption.TRUCK)),
            cord8,
            ja.toString(),
            allServiceUsers.get(3));

    jo_waste.remove(WasteType.METALS.toString());
    jo_waste.put(WasteType.GLASS.toString(), "free");
    jo_waste.put(WasteType.PAPER.toString(), "free");
    jo_waste.put(WasteType.PLASTIC.toString(), "free");
    jo_delivery.remove(DeliveryOption.TRUCK.toString());
    final EcoService es9 =
        new EcoService(
            0,
            "Eco Station II",
            new HashSet<WasteType>(List.of(WasteType.GLASS, WasteType.PAPER, WasteType.PLASTIC)),
            new HashSet<PaymentCondition>(List.of(PaymentCondition.FREE)),
            new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)),
            cord9,
            ja.toString(),
            allServiceUsers.get(3));

    jo_waste.remove(WasteType.PAPER.toString());
    jo_waste.remove(WasteType.GLASS.toString());
    jo_waste.remove(WasteType.PLASTIC.toString());
    jo_waste.put(WasteType.GLASS.toString(), "0.04 EUR/kg");
    final EcoService es10 =
        new EcoService(
            0,
            "All glass melts fast",
            new HashSet<WasteType>(List.of(WasteType.GLASS)),
            new HashSet<PaymentCondition>(List.of(PaymentCondition.CASH, PaymentCondition.CARD)),
            new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)),
            cord10,
            ja.toString(),
            allServiceUsers.get(3));

    jo_waste.remove(WasteType.GLASS.toString());
    jo_waste.put(WasteType.ELECTRONIC.toString(), "0.05 EUR/kg");
    jo_delivery.put(DeliveryOption.VAN.toString(), "1 EUR/ 10kg");
    final EcoService es11 =
        new EcoService(
            0,
            "Wall-e, electronic waste collector",
            new HashSet<WasteType>(List.of(WasteType.ELECTRONIC)),
            new HashSet<PaymentCondition>(List.of(PaymentCondition.CARD)),
            new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF, DeliveryOption.VAN)),
            cord11,
            ja.toString(),
            allServiceUsers.get(4));

    jo_waste.remove(WasteType.ELECTRONIC.toString());
    jo_waste.put(WasteType.GLASS.toString(), "free");
    jo_waste.put(WasteType.PAPER.toString(), "free");
    jo_waste.put(WasteType.PLASTIC.toString(), "free");
    jo_delivery.remove(DeliveryOption.VAN.toString());
    final EcoService es12 =
        new EcoService(
            0,
            "Eco Station III",
            new HashSet<WasteType>(List.of(WasteType.GLASS, WasteType.PAPER, WasteType.PLASTIC)),
            new HashSet<PaymentCondition>(List.of(PaymentCondition.FREE)),
            new HashSet<DeliveryOption>(List.of(DeliveryOption.SELF)),
            cord12,
            ja.toString(),
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
