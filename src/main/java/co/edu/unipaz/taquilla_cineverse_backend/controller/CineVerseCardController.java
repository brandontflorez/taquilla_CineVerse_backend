
package co.edu.unipaz.taquilla_cineverse_backend.controller;

import co.edu.unipaz.taquilla_cineverse_backend.model.CineVerseCard;
import co.edu.unipaz.taquilla_cineverse_backend.repository.CineVerseCardRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/cineverse-cards")
@CrossOrigin(origins = "http://localhost:4200")
public class CineVerseCardController {

    private final CineVerseCardRepository cineVerseCardRepository;

    public CineVerseCardController(
            CineVerseCardRepository cineVerseCardRepository) {

        this.cineVerseCardRepository = cineVerseCardRepository;
    }

    // ==========================================
    // CONSULTAR TARJETA POR NUMERO
    // ==========================================

    @GetMapping("/numero/{numeroTarjeta}")
    public ResponseEntity<?> consultarPorNumero(
            @PathVariable String numeroTarjeta) {

        return cineVerseCardRepository
                .findByNumeroTarjeta(numeroTarjeta)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Tarjeta no encontrada."));
    }

    // ==========================================
    // RECARGAR SALDO DE LA TARJETA
    // ==========================================

    @PostMapping("/{id}/recargar")
    public ResponseEntity<?> recargarSaldo(
            @PathVariable Integer id,
            @RequestBody Map<String, BigDecimal> datos) {

        BigDecimal valor = datos.get("valor");

        // Validar valor de recarga
        if (valor == null ||
                valor.compareTo(BigDecimal.ZERO) <= 0) {

            return ResponseEntity.badRequest()
                    .body("El valor de la recarga debe ser mayor que cero.");
        }

        // Buscar tarjeta
        CineVerseCard tarjeta = cineVerseCardRepository
                .findById(id)
                .orElse(null);

        if (tarjeta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró la tarjeta.");
        }

        // Obtener saldo actual
        BigDecimal saldoActual = tarjeta.getSaldo();

        if (saldoActual == null) {
            saldoActual = BigDecimal.ZERO;
        }

        // Sumar recarga
        tarjeta.setSaldo(saldoActual.add(valor));

        // Guardar cambios
        CineVerseCard tarjetaActualizada =
                cineVerseCardRepository.save(tarjeta);

        return ResponseEntity.ok(tarjetaActualizada);
    }
}
