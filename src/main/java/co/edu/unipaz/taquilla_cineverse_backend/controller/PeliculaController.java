package co.edu.unipaz.taquilla_cineverse_backend.controller;

import co.edu.unipaz.taquilla_cineverse_backend.model.Pelicula;
import co.edu.unipaz.taquilla_cineverse_backend.service.PeliculaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/peliculas")
@CrossOrigin(origins = "*")
public class PeliculaController {

    private final PeliculaService peliculaService;

    public PeliculaController(PeliculaService peliculaService) {
        this.peliculaService = peliculaService;
    }

    // Obtener todas las películas
    @GetMapping
    public List<Pelicula> obtenerTodas() {
        return peliculaService.obtenerTodas();
    }

    // Obtener una película por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pelicula> obtenerPorId(@PathVariable Integer id) {
        return peliculaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear una película
    @PostMapping
    public ResponseEntity<Pelicula> crear(@Valid @RequestBody Pelicula pelicula) {
        Pelicula nuevaPelicula = peliculaService.guardar(pelicula);
        return ResponseEntity.status(201).body(nuevaPelicula);
    }

    // Actualizar una película
    @PutMapping("/{id}")
    public ResponseEntity<Pelicula> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody Pelicula pelicula) {

        Pelicula peliculaActualizada = peliculaService.actualizar(id, pelicula);
        return ResponseEntity.ok(peliculaActualizada);
    }

    // Eliminar una película
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        peliculaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}