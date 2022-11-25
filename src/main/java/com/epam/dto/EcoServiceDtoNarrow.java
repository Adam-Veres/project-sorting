package com.epam.dto;

import com.epam.model.Coordinate;

public class EcoServiceDtoNarrow {

	long id;
	String serviceName;
	Coordinate coordinate;
	
	public EcoServiceDtoNarrow() {}
	
	public EcoServiceDtoNarrow(long id, String serviceName, Coordinate coordinate) {
		this.id = id;
		this.serviceName = serviceName;
		this.coordinate = coordinate;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getServiceName() {
		return serviceName;
	}
	
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public Coordinate getCoordinate() {
		return coordinate;
	}
	
	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	
}
