package co.edu.unipaz.taquilla_cineverse_backend.service;

import co.edu.unipaz.taquilla_cineverse_backend.model.Compra;
import co.edu.unipaz.taquilla_cineverse_backend.model.Funcion;
import co.edu.unipaz.taquilla_cineverse_backend.repository.CompraRepository;
import co.edu.unipaz.taquilla_cineverse_backend.repository.FuncionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final FuncionRepository funcionRepository;

    public CompraService(CompraRepository compraRepository,
                         FuncionRepository funcionRepository) {
        this.compraRepository = compraRepository;
        this.funcionRepository = funcionRepository;
    }

    // Obtener todas las compras
    public List<Compra> obtenerTodas() {
        return compraRepository.findAll();
    }

    // Buscar una compra por ID
    public Optional<Compra> obtenerPorId(Integer id) {
        return compraRepository.findById(id);
    }

    // Realizar una compra
    @Transactional
    public Compra realizarCompra(Compra compra) {

        // Buscar la función
        Funcion funcion = funcionRepository.findById(compra.getFuncionId())
                .orElseThrow(() ->
                        new RuntimeException("La función no existe"));

        // Verificar que haya suficientes cupos
        if (funcion.getCuposDisponibles() < compra.getCantidadBoletos()) {
            throw new RuntimeException("No hay suficientes cupos disponibles");
        }

        // Calcular el valor total
        BigDecimal valorTotal = funcion.getPrecio()
                .multiply(BigDecimal.valueOf(compra.getCantidadBoletos()));

        compra.setValorTotal(valorTotal);

        // Registrar la fecha de compra
        compra.setFechaCompra(LocalDateTime.now());

        // Descontar los cupos
        funcion.setCuposDisponibles(
                funcion.getCuposDisponibles() - compra.getCantidadBoletos()
        );

        // Guardar los cambios de la función
        funcionRepository.save(funcion);

        // Guardar la compra
        return compraRepository.save(compra);
    }
}