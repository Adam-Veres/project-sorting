package com.epam.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Coordinate {

	@Id
	@GeneratedValue
	private long id;
	/**
	 * Latitudes are horizontal lines that measures distance north or south of the equator.
	 */
	@Column(scale = 6, precision = 8)
	private BigDecimal latitude;
	/**
	 * Longitudes are vertical lines that measure east or west of the meridian in Greenwich, England.
	 */
	@Column(scale = 6, precision = 8)
	private BigDecimal longitude;
	
	public Coordinate() {
	}

	public Coordinate(long id, BigDecimal latitude, BigDecimal longitude) {
		this.id = id;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	
}
