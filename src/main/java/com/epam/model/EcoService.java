package com.epam.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class EcoService {

  @Id @GeneratedValue private long id;
  private String serviceName;

  @ElementCollection(targetClass = WasteType.class, fetch = FetchType.EAGER)
  @Enumerated(EnumType.STRING)
  private Set<WasteType> typeOfWastes;

  @ElementCollection(targetClass = PaymentCondition.class, fetch = FetchType.EAGER)
  @Enumerated(EnumType.STRING)
  private Set<PaymentCondition> paymentConditions;

  @ElementCollection(targetClass = DeliveryOption.class, fetch = FetchType.EAGER)
  @Enumerated(EnumType.STRING)
  private Set<DeliveryOption> deliveryOptions;

  @OneToOne(cascade = CascadeType.ALL)
  private Coordinate coordinate;

  private String description;

  @Setter(value = AccessLevel.NONE)
  private BigDecimal numOfRatings;

  @Setter(value = AccessLevel.NONE)
  private BigDecimal sumOfRatings;

  @Setter(value = AccessLevel.NONE)
  @Getter(value = AccessLevel.NONE)
  @Transient
  private BigDecimal rating;

  @JsonBackReference
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private EcoUser owner;

  public EcoService(
      final long id,
      final String serviceName,
      final Set<WasteType> typeOfWastes,
      final Set<PaymentCondition> paymentConditions,
      final Set<DeliveryOption> deliveryOptions,
      final Coordinate coordinate,
      final String description,
      final BigDecimal numOfRatings,
      final BigDecimal sumOfRatings,
      final EcoUser owner) {
    this.id = id;
    this.serviceName = serviceName;
    this.typeOfWastes = typeOfWastes;
    this.paymentConditions = paymentConditions;
    this.deliveryOptions = deliveryOptions;
    this.coordinate = coordinate;
    this.description = description;
    this.numOfRatings = numOfRatings;
    this.sumOfRatings = sumOfRatings;
    this.owner = owner;
  }

  public void addRating(final Optional<BigDecimal> rating) {
    final BigDecimal rate =
        rating.orElseThrow(() -> new IllegalArgumentException("Rating do not be null!"));
    if (this.sumOfRatings == null) {
      this.sumOfRatings = rate;
      this.numOfRatings = BigDecimal.ONE;
    } else {
      this.sumOfRatings = this.sumOfRatings.add(rate);
      this.numOfRatings = this.numOfRatings.add(BigDecimal.ONE);
    }
  }

  private void countRating() {
    System.out.println("Count rating");
    if (this.sumOfRatings != null
        && this.numOfRatings != null
        && (this.numOfRatings.compareTo(BigDecimal.ZERO) != 0)) {
      this.rating =
          this.sumOfRatings.divide(this.numOfRatings, new MathContext(2, RoundingMode.HALF_UP));
    } else {
      this.rating = BigDecimal.ZERO;
    }
  }

  public BigDecimal getRating() {
    countRating();
    return this.rating;
  }
}
