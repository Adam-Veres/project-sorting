package com.epam.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    indexes = {
      @Index(
          name = "creatorEcoServiceIndex",
          columnList = "creator_id,eco_service_id",
          unique = true)
    })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRating {
  @Id @GeneratedValue private Long id;

  private int rating;

  @Setter(value = AccessLevel.NONE)
  @ManyToOne
  private EcoUser creator;

  @Setter(value = AccessLevel.NONE)
  @ManyToOne
  private EcoService ecoService;
}
