package com.company.books.backend.ejemplos.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CalculadoraTest {

	Calculadora cal;
	
	@BeforeAll
	public static void primero() {
		System.out.println("primero");
	}
	
	@AfterAll
	public static void ultimo() {
		System.out.println("ultimo");
	}
	
	@BeforeEach
	public void instanciaObjeto() {
		cal = new Calculadora();
		System.out.println("@BeforeEach");
	}
	
	@AfterEach
	private void despuesTest() {
		System.out.println("@AfterEach");
	}
	
	@Test
	@DisplayName("Prueba que ocupa assertEqual")
	@Disabled("Esta prueba no se ejecuta")
	public void calculadoraAssertEqualTest() {
		assertEquals(25, cal.sumar(23, 2));
		assertEquals(21, cal.restar(23, 2));
		assertEquals(9, cal.multiplicar(3, 3));
		assertEquals(1, cal.dividir(9, 9));
		
		System.out.println("calculadoraAssertEqualTest");
	}
	
	@Test
	@DisplayName("Prueba que ocupa assertTrueFalse")
	public void calculadoraTrueFalse() {
		assertTrue(cal.sumar(4, 8) == 12);
		assertTrue(cal.restar(4, 2) == 2);
		assertFalse(cal.multiplicar(5, 2) == 25);
		assertFalse(cal.dividir(9, 3) == 2);
		
		System.out.println("calculadoraTrueFalse");
	}
}
