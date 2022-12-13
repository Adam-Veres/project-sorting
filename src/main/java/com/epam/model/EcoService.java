package com.epam.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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

  @JsonBackReference
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private EcoUser owner;

  @JsonManagedReference
  @OneToMany(mappedBy = "ecoService", fetch = FetchType.EAGER)
  private Set<CommentMessage> comments;

  public EcoService(
      final long id,
      final String serviceName,
      final Set<WasteType> typeOfWastes,
      final Set<PaymentCondition> paymentConditions,
      final Set<DeliveryOption> deliveryOptions,
      final Coordinate coordinate,
      final String description,
      final EcoUser owner) {
    this.id = id;
    this.serviceName = serviceName;
    this.typeOfWastes = typeOfWastes;
    this.paymentConditions = paymentConditions;
    this.deliveryOptions = deliveryOptions;
    this.coordinate = coordinate;
    this.description = description;
    this.owner = owner;
  }
}
