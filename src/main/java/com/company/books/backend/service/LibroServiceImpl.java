package com.company.books.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.books.backend.model.Libro;
import com.company.books.backend.model.dao.ILibroDao;
import com.company.books.backend.response.LibroResponseRest;

@Service
public class LibroServiceImpl implements ILibroService {

	private static final Logger log = LoggerFactory.getLogger(LibroServiceImpl.class);

	@Autowired
	private ILibroDao libroDao;
	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<LibroResponseRest> buscarLibros() {
		log.info("Inicio metodo buscar Libros");
		LibroResponseRest response = new LibroResponseRest();

		try {
			List<Libro> libro = (List<Libro>) libroDao.findAll();
			response.getLibroResponse().setLibro(libro);
			response.setMetadata("Respuesta OK", "200", "Respuesta Exitosa");
		
		} catch (Exception e) {
			response.setMetadata("Respuesta no OK", "-1", "Error al consultar libros");
			log.error("Error al consultar libros: ", e.getMessage());
			e.getStackTrace();

			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);// devuelve 200
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<LibroResponseRest> buscarId(Long id) {
		log.info("Inicio metodo buscarId");
		LibroResponseRest response = new LibroResponseRest();
		List<Libro> lista = new ArrayList<>();

		try {
			Optional<Libro> libro = libroDao.findById(id);

			if (libro.isPresent()) {
				lista.add(libro.get());
				response.getLibroResponse().setLibro(lista);

			} else {
				log.error("Error al consultar libro");
				response.setMetadata("Respuesta no OK", "-1", "Libro no encontrado");
				return new ResponseEntity<LibroResponseRest>(response, HttpStatus.NOT_FOUND);
			
			}
		} catch (Exception e) {
			log.error("Error al consultar libro");
			response.setMetadata("Respuesta no OK", "-1", "Error al consultar libro");
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		response.setMetadata("Respuesta OK", "200", "Respuesta Exitosa");
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);// devuelve 200
		
	}

	@Override
	@Transactional
	public ResponseEntity<LibroResponseRest> crear(Libro libro) {
		log.info("Inicio metodo crear");
		LibroResponseRest response = new LibroResponseRest();
		List<Libro> lista = new ArrayList<>();
		
		try {
			Libro libroGuardada = libroDao.save(libro);
			
			if (libroGuardada != null) {
				lista.add(libroGuardada);
				
				response.getLibroResponse().setLibro(lista);
			}else {
				log.error("Error al crear libro");
				response.setMetadata("Respuesta no OK", "-1", "Libro no guardado");
				return new ResponseEntity<LibroResponseRest>(response, HttpStatus.BAD_REQUEST);
			
			}
			
		} catch (Exception e) {
			log.error("Error al crear libro");
			response.setMetadata("Respuesta no OK", "-1", "Error al crear libro");
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		response.setMetadata("Respuesta OK", "200", "Libro creado");
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);// devuelve 200
	
	}
	
	@Override
	@Transactional
	public ResponseEntity<LibroResponseRest> actualizar(Libro libro, Long id) {
		log.info("Inicio metodo actualizar");
		LibroResponseRest response = new LibroResponseRest();
		List<Libro> lista = new ArrayList<>();
		
		try {
			Optional<Libro> libroBuscado = libroDao.findById(id);
			
			if (libroBuscado.isPresent()) {
				libroBuscado.get().setNombre(libro.getNombre());
				libroBuscado.get().setDescripcion(libro.getDescripcion());
				libroBuscado.get().setCategoria(libro.getCategoria());
				
				Libro libroActualizar = libroDao.save(libroBuscado.get());
				
				if (libroActualizar != null) {
					response.setMetadata("Respuesta OK", "200", "Libro actualizado");
					lista.add(libroActualizar);
					response.getLibroResponse().setLibro(lista);
					
				}else {
					log.error("Error al actualizar libro");
					response.setMetadata("Respuesta no OK", "-1", "Libro no actualizado");
					return new ResponseEntity<LibroResponseRest>(response, HttpStatus.BAD_REQUEST);
				
				}
			}else {
				log.error("Error al actualizar libro");
				response.setMetadata("Respuesta no OK", "-1", "Libro no actualizado");
				return new ResponseEntity<LibroResponseRest>(response, HttpStatus.NOT_FOUND);
			
			}
		} catch (Exception e) {
			log.error("Error al actualizar libro", e.getMessage());
			e.getStackTrace();
			response.setMetadata("Respuesta no OK", "-1", "Error al actualizar libro");
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);// devuelve 200

	}

	@Override
	@Transactional
	public ResponseEntity<LibroResponseRest> eliminar(Long id) {
		log.info("Inicio metodo eliminar");
		LibroResponseRest response = new LibroResponseRest();
		
		try {
			libroDao.deleteById(id);
			response.setMetadata("Respuesta OK", "200", "Libro eliminada");
			
		} catch (Exception e) {
			log.error("Error al eliminar libro", e.getMessage());
			e.getStackTrace();
			response.setMetadata("Respuesta no OK", "-1", "Error al eliminar libro");
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);
	}

}
