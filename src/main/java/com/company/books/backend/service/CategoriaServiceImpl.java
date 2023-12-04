package com.company.books.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.company.books.backend.model.Categoria;
import com.company.books.backend.model.dao.ICategoriaDao;
import com.company.books.backend.response.CategoriaResponseRest;

@Service
public class CategoriaServiceImpl implements ICategoriaService {

	private static final Logger log = LoggerFactory.getLogger(CategoriaServiceImpl.class);

	@Autowired
	private ICategoriaDao categoriaDao;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoriaResponseRest> bucarCategoria() {
		log.info("Inicio metodo buscar Categorias()");
		CategoriaResponseRest response = new CategoriaResponseRest();

		try {
			List<Categoria> categoria = (List<Categoria>) categoriaDao.findAll();
			response.getCategoriaResponse().setCategoria(categoria);
			response.setMetadata("Respuesta OK", "200", "Respuesta Exitosa");
		
		} catch (Exception e) {
			response.setMetadata("Respuesta no OK", "-1", "Error al consultar categorias");
			log.error("Error al consultar categorias: ", e.getMessage());
			e.getStackTrace();

			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);// devuelve 200
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoriaResponseRest> buscarId(Long id) {
		log.info("Inicio metodo buscarId categoria()");
		CategoriaResponseRest response = new CategoriaResponseRest();
		List<Categoria> lista = new ArrayList<>();

		try {
			Optional<Categoria> categoria = categoriaDao.findById(id);

			if (categoria.isPresent()) {
				lista.add(categoria.get());
				response.getCategoriaResponse().setCategoria(lista);

			} else {
				log.error("Error al consultar categoria");
				response.setMetadata("Respuesta no OK", "-1", "Categoria no encontrada");
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND);
			
			}
		} catch (Exception e) {
			log.error("Error al consultar categoria");
			response.setMetadata("Respuesta no OK", "-1", "Error al consultar categoria");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		response.setMetadata("Respuesta OK", "200", "Respuesta Exitosa");
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);// devuelve 200
	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> crear(Categoria categoria) {
		log.info("Inicio metodo crear categoria()");
		CategoriaResponseRest response = new CategoriaResponseRest();
		List<Categoria> lista = new ArrayList<>();
		
		try {
			Categoria categoriaGuardada = categoriaDao.save(categoria);
			
			if (categoriaGuardada != null) {
				lista.add(categoriaGuardada);
				
				response.getCategoriaResponse().setCategoria(lista);
			}else {
				log.error("Error al crear categoria");
				response.setMetadata("Respuesta no OK", "-1", "Categoria no guardada");
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST);
			
			}
			
		} catch (Exception e) {
			log.error("Error al crear categoria");
			response.setMetadata("Respuesta no OK", "-1", "Error al crear categoria");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		response.setMetadata("Respuesta OK", "200", "Categoria creada");
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);// devuelve 200
	
	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> actualizar(Categoria categoria, Long id) {
		log.info("Inicio metodo actualizar categoria()");
		CategoriaResponseRest response = new CategoriaResponseRest();
		List<Categoria> lista = new ArrayList<>();
		
		try {
			Optional<Categoria> categoriaBuscada = categoriaDao.findById(id);
			
			if (categoriaBuscada.isPresent()) {
				categoriaBuscada.get().setNombre(categoria.getNombre());
				categoriaBuscada.get().setDescripcion(categoria.getDescripcion());
				
				Categoria categoriaActualizar = categoriaDao.save(categoriaBuscada.get());
				
				if (categoriaActualizar != null) {
					response.setMetadata("Respuesta OK", "200", "Categoria actualizada");
					lista.add(categoriaActualizar);
					response.getCategoriaResponse().setCategoria(lista);
					
				}else {
					log.error("Error al actualizar categoria");
					response.setMetadata("Respuesta no OK", "-1", "Categoria no actualizada");
					return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST);
				
				}
			}else {
				log.error("Error al actualizar categoria");
				response.setMetadata("Respuesta no OK", "-1", "Categoria no actualizada");
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND);
			
			}
		} catch (Exception e) {
			log.error("Error al actualizar categoria", e.getMessage());
			e.getStackTrace();
			response.setMetadata("Respuesta no OK", "-1", "Error al actualizar categoria");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);// devuelve 200
	
	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> eliminar(Long id) {
		log.info("Inicio metodo eliminar categoria()");
		CategoriaResponseRest response = new CategoriaResponseRest();
		
		try {
			categoriaDao.deleteById(id);
			response.setMetadata("Respuesta OK", "200", "Categoria eliminada");
			
		} catch (Exception e) {
			log.error("Error al eliminar categoria", e.getMessage());
			e.getStackTrace();
			response.setMetadata("Respuesta no OK", "-1", "Error al eliminar categoria");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
	}

}
