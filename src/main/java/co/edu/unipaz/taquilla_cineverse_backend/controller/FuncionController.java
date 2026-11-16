package co.edu.unipaz.taquilla_cineverse_backend.controller;

import co.edu.unipaz.taquilla_cineverse_backend.model.Funcion;
import co.edu.unipaz.taquilla_cineverse_backend.service.FuncionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/funciones")
@CrossOrigin(origins = "*")
public class FuncionController {

    private final FuncionService funcionService;

    public FuncionController(FuncionService funcionService) {
        this.funcionService = funcionService;
    }

    // Obtener todas las funciones
    @GetMapping
    public List<Funcion> obtenerTodas() {
        return funcionService.obtenerTodas();
    }

    // Obtener una función por ID
    @GetMapping("/{id}")
    public ResponseEntity<Funcion> obtenerPorId(@PathVariable Integer id) {
        return funcionService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear una función
    @PostMapping
    public ResponseEntity<Funcion> crear(@Valid @RequestBody Funcion funcion) {
        Funcion nuevaFuncion = funcionService.guardar(funcion);
        return ResponseEntity.status(201).body(nuevaFuncion);
    }

    // Actualizar una función
    @PutMapping("/{id}")
    public ResponseEntity<Funcion> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody Funcion funcion) {

        Funcion funcionActualizada = funcionService.actualizar(id, funcion);
        return ResponseEntity.ok(funcionActualizada);
    }

    // Eliminar una función
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {

        try {
            funcionService.eliminar(id);
            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

}