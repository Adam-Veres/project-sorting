package com.epam.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.epam.dto.CoordinateDto;

public class EcoServiceServiceTest {

	EcoServiceService ecoServiceService = new EcoServiceService();
	
	private Method getCoordinateValidation() throws NoSuchMethodException {
	    Method method = EcoServiceService.class.getDeclaredMethod("coordinateValidation", BigDecimal.class);
	    method.setAccessible(true);
	    return method;
	}
	
	@Test
	void coordinateValueNearPositiveBorder_test() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException{
		//Given
		BigDecimal latitude = BigDecimal.valueOf(184.34);
		BigDecimal expectedResult = BigDecimal.valueOf(-175.66);
		//When
		BigDecimal result = (BigDecimal) getCoordinateValidation().invoke(new EcoServiceService(), latitude);
		//Then
		assertEquals(expectedResult, result);
	}
	
	@Test
	void coordinateValueNearNegativeBorder_test() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException{
		//Given
		BigDecimal latitude = BigDecimal.valueOf(-185.65);
		BigDecimal expectedResult = BigDecimal.valueOf(174.35);
		//When
		BigDecimal result = (BigDecimal) getCoordinateValidation().invoke(new EcoServiceService(), latitude);
		//Then
		assertEquals(expectedResult, result);
	}
	
	@Test
	void coordinateValueInNormalRange_test() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException{
		//Given
		BigDecimal latitude = BigDecimal.valueOf(-165.65);
		BigDecimal expectedResult = BigDecimal.valueOf(-165.65);
		//When
		BigDecimal result = (BigDecimal) getCoordinateValidation().invoke(new EcoServiceService(), latitude);
		//Then
		assertEquals(expectedResult, result);
	}
	
	@Test
	void getServiceFromAreaNullParam_test() {
		//Given
		CoordinateDto coordinate = new CoordinateDto(0, null, null);
		BigDecimal distance = null;
		//When
		//Then
		assertThrows(IllegalArgumentException.class, () -> ecoServiceService.getServiceFromArea(coordinate, distance));
	}
}
