package co.edu.unipaz.taquilla_cineverse_backend.repository;

import co.edu.unipaz.taquilla_cineverse_backend.model.Pelicula;
import org.springframework.data.repository.ListCrudRepository;

public interface PeliculaRepository extends ListCrudRepository<Pelicula, Integer> {
}