package co.edu.unipaz.taquilla_cineverse_backend.controller;

import co.edu.unipaz.taquilla_cineverse_backend.model.Compra;
import co.edu.unipaz.taquilla_cineverse_backend.dto.ComprobanteCompra;
import co.edu.unipaz.taquilla_cineverse_backend.service.CompraService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compras")
@CrossOrigin(origins = "http://localhost:4200")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @GetMapping
    public Iterable<Compra> obtenerTodas() {
        return compraService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Compra obtenerPorId(@PathVariable Integer id) {
        return compraService.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<?> realizarCompra(@RequestBody Compra compra) {
        try {
            Compra nuevaCompra = compraService.realizarCompra(compra);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCompra);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/comprobante")
    public ComprobanteCompra obtenerComprobante(@PathVariable Integer id) {
        return compraService.obtenerComprobante(id);
    }

    // Eliminar una compra
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCompra(@PathVariable Integer id) {
        try {
            compraService.eliminarCompra(id);
            return ResponseEntity.ok("Compra eliminada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Eliminar todo el historial
    @DeleteMapping
    public ResponseEntity<?> eliminarTodasLasCompras() {
        try {
            compraService.eliminarTodasLasCompras();
            return ResponseEntity.ok("Historial eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
