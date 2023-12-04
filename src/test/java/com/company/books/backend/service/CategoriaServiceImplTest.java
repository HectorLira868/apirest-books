package com.company.books.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.company.books.backend.model.Categoria;
import com.company.books.backend.model.dao.ICategoriaDao;
import com.company.books.backend.response.CategoriaResponseRest;

public class CategoriaServiceImplTest {
	@InjectMocks
	CategoriaServiceImpl service;
	
	@Mock
	ICategoriaDao categoriaDao;
	
	List<Categoria> lista = new ArrayList<Categoria>();
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		this.cargarCategorias();
	}
	
	@Test
	public void buscarCategoriasTest() {
		when(categoriaDao.findAll()).thenReturn(lista);
		
		ResponseEntity<CategoriaResponseRest> response = service.bucarCategoria();
		
		assertEquals(4, response.getBody().getCategoriaResponse().getCategoria().size());
		
		verify(categoriaDao, times(1)).findAll();
	}
	
	public void cargarCategorias() {
		Categoria catUno = new Categoria(Long.valueOf(1), "Abarrotes", "Distintos Abarrotes");
		Categoria catDos = new Categoria(Long.valueOf(1), "Lacteos", "Variedad Abarrotes");
		Categoria catTres = new Categoria(Long.valueOf(1), "Bebidas", "Bebidas sin azucar");
		Categoria catCuatro = new Categoria(Long.valueOf(1), "Carnes Blancas", "Distintas Carnes");
		
		lista.add(catUno);
		lista.add(catDos);
		lista.add(catTres);
		lista.add(catCuatro);
		}
}
