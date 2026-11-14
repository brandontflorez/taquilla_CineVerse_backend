package co.edu.unipaz.taquilla_cineverse_backend.service;

import co.edu.unipaz.taquilla_cineverse_backend.model.Pelicula;
import co.edu.unipaz.taquilla_cineverse_backend.repository.PeliculaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeliculaService {

    private final PeliculaRepository peliculaRepository;

    public PeliculaService(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

    // Obtener todas las películas
    public List<Pelicula> obtenerTodas() {
        return peliculaRepository.findAll();
    }

    // Buscar una película por ID
    public Optional<Pelicula> obtenerPorId(Integer id) {
        return peliculaRepository.findById(id);
    }

    // Guardar una nueva película
    public Pelicula guardar(Pelicula pelicula) {
        return peliculaRepository.save(pelicula);
    }

    // Actualizar una película
    public Pelicula actualizar(Integer id, Pelicula pelicula) {
        pelicula.setId(id);
        return peliculaRepository.save(pelicula);
    }

    // Eliminar una película
    public void eliminar(Integer id) {
        peliculaRepository.deleteById(id);
    }
}