package co.edu.unipaz.taquilla_cineverse_backend.controller;

import co.edu.unipaz.taquilla_cineverse_backend.model.Compra;
import co.edu.unipaz.taquilla_cineverse_backend.service.CompraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/compras")
@CrossOrigin(origins = "*")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    // Obtener todas las compras
    @GetMapping
    public List<Compra> obtenerTodas() {
        return compraService.obtenerTodas();
    }

    // Obtener una compra por ID
    @GetMapping("/{id}")
    public ResponseEntity<Compra> obtenerPorId(@PathVariable Integer id) {
        return compraService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Realizar una compra
    @PostMapping
    public ResponseEntity<?> realizarCompra(@Valid @RequestBody Compra compra) {
        try {
            Compra nuevaCompra = compraService.realizarCompra(compra);
            return ResponseEntity.status(201).body(nuevaCompra);

        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }
}