package com.company.books.backend.ejemplos.junit;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class AssertArrayEquals {
	@Test
	public void prueba() {
		String[] ar1 = {"a", "b"};		
		String[] ar2 = {"a", "b"};	
		String[] ar3 = {"a", "b", "c"};	
		assertArrayEquals(ar1, ar2);
//		assertArrayEquals(ar1, ar3);
//		assertArrayEquals(ar2, ar3);
	}
}
