package com.epam.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class CoordinateDto {

	long id;
	@Min(value = -180)
	@Max(value = 180)
	BigDecimal latitude;
	@Min(value = -180)
	@Max(value = 180)
	BigDecimal longitude;
	
	public CoordinateDto() {
	}

	public CoordinateDto(long id, BigDecimal latitude, BigDecimal longitude) {
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
