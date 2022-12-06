package com.epam.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Coordinate {

  @Id @GeneratedValue private long id;
  /** Latitudes are horizontal lines that measures distance north or south of the equator. */
  @Column(scale = 6, precision = 8)
  private BigDecimal latitude;
  /**
   * Longitudes are vertical lines that measure east or west of the meridian in Greenwich, England.
   */
  @Column(scale = 6, precision = 8)
  private BigDecimal longitude;
}
