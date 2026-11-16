package co.edu.unipaz.taquilla_cineverse_backend.service;

import co.edu.unipaz.taquilla_cineverse_backend.model.Compra;
import co.edu.unipaz.taquilla_cineverse_backend.model.Funcion;
import co.edu.unipaz.taquilla_cineverse_backend.model.CineVerseCard;
import co.edu.unipaz.taquilla_cineverse_backend.model.Pelicula;
import co.edu.unipaz.taquilla_cineverse_backend.dto.ComprobanteCompra;

import co.edu.unipaz.taquilla_cineverse_backend.repository.CompraRepository;
import co.edu.unipaz.taquilla_cineverse_backend.repository.FuncionRepository;
import co.edu.unipaz.taquilla_cineverse_backend.repository.CineVerseCardRepository;
import co.edu.unipaz.taquilla_cineverse_backend.repository.PeliculaRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final FuncionRepository funcionRepository;
    private final CineVerseCardRepository cineVerseCardRepository;
    private final PeliculaRepository peliculaRepository;

    public CompraService(
            CompraRepository compraRepository,
            FuncionRepository funcionRepository,
            CineVerseCardRepository cineVerseCardRepository,
            PeliculaRepository peliculaRepository) {

        this.compraRepository = compraRepository;
        this.funcionRepository = funcionRepository;
        this.cineVerseCardRepository = cineVerseCardRepository;
        this.peliculaRepository = peliculaRepository;
    }

    public Iterable<Compra> obtenerTodas() {
        return compraRepository.findAll();
    }

    public Compra obtenerPorId(Integer id) {
        return compraRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Compra no encontrada"));
    }

    @Transactional
    public Compra realizarCompra(Compra compra) {

        Funcion funcion = funcionRepository.findById(compra.getFuncionId())
                .orElseThrow(() -> new RuntimeException("La función no existe"));

        if (funcion.getCuposDisponibles() < compra.getCantidadBoletos()) {
            throw new RuntimeException("No hay suficientes cupos disponibles");
        }

        BigDecimal valorTotal = funcion.getPrecio()
                .multiply(BigDecimal.valueOf(compra.getCantidadBoletos()));

        compra.setValorTotal(valorTotal);

        if ("CINEVERSE_CARD".equals(compra.getMetodoPago())) {

            if (compra.getCodigoPago() == null || compra.getCodigoPago().isBlank()) {
                throw new RuntimeException("Debe ingresar el número de tarjeta");
            }

            CineVerseCard tarjeta = cineVerseCardRepository
                    .findByNumeroTarjeta(compra.getCodigoPago())
                    .orElseThrow(() -> new RuntimeException("Tarjeta no encontrada"));

            if (tarjeta.getSaldo().compareTo(valorTotal) < 0) {
                throw new RuntimeException("Saldo insuficiente en la tarjeta");
            }

            tarjeta.setSaldo(tarjeta.getSaldo().subtract(valorTotal));
            cineVerseCardRepository.save(tarjeta);
        }

        compra.setFechaCompra(LocalDateTime.now());

        funcion.setCuposDisponibles(
                funcion.getCuposDisponibles() - compra.getCantidadBoletos()
        );

        funcionRepository.save(funcion);

        return compraRepository.save(compra);
    }

    public ComprobanteCompra obtenerComprobante(Integer id) {

        Compra compra = obtenerPorId(id);

        Funcion funcion = funcionRepository.findById(compra.getFuncionId())
                .orElseThrow(() -> new RuntimeException("Función no encontrada"));

        Pelicula pelicula = peliculaRepository.findById(funcion.getPeliculaId())
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));

        ComprobanteCompra comprobante = new ComprobanteCompra();

        comprobante.setCompraId(compra.getId());
        comprobante.setCodigoComprobante("CV-" + compra.getId());
        comprobante.setNombreCliente(compra.getNombreCliente());
        comprobante.setPelicula(pelicula.getTitulo());
        comprobante.setFechaFuncion(funcion.getFecha());
        comprobante.setHoraFuncion(funcion.getHora());
        comprobante.setSala(funcion.getSala());
        comprobante.setCantidadBoletos(compra.getCantidadBoletos());
        comprobante.setMetodoPago(compra.getMetodoPago());
        comprobante.setValorTotal(compra.getValorTotal());
        comprobante.setFechaCompra(compra.getFechaCompra());

        return comprobante;
    }

    // Eliminar una compra
    @Transactional
    public void eliminarCompra(Integer id) {

        Compra compra = obtenerPorId(id);

        revertirCompra(compra);

        compraRepository.deleteById(id);
    }

    // Eliminar todas las compras
    @Transactional
    public void eliminarTodasLasCompras() {

        Iterable<Compra> compras = compraRepository.findAll();

        for (Compra compra : compras) {
            revertirCompra(compra);
        }

        compraRepository.deleteAll();
    }

    // Devolver los cupos y el saldo de la tarjeta
    private void revertirCompra(Compra compra) {

        Funcion funcion = funcionRepository.findById(compra.getFuncionId())
                .orElseThrow(() -> new RuntimeException(
                        "No se encontró la función de la compra " + compra.getId()
                ));

        funcion.setCuposDisponibles(
                funcion.getCuposDisponibles() + compra.getCantidadBoletos()
        );

        funcionRepository.save(funcion);

        if ("CINEVERSE_CARD".equals(compra.getMetodoPago())) {

            CineVerseCard tarjeta = cineVerseCardRepository
                    .findByNumeroTarjeta(compra.getCodigoPago())
                    .orElseThrow(() -> new RuntimeException(
                            "No se encontró la tarjeta de la compra " + compra.getId()
                    ));

            BigDecimal valor = compra.getValorTotal() == null
                    ? BigDecimal.ZERO
                    : compra.getValorTotal();

            tarjeta.setSaldo(tarjeta.getSaldo().add(valor));

            cineVerseCardRepository.save(tarjeta);
        }
    }
}
