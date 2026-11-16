
package co.edu.unipaz.taquilla_cineverse_backend.service;

import co.edu.unipaz.taquilla_cineverse_backend.model.Funcion;
import co.edu.unipaz.taquilla_cineverse_backend.repository.FuncionRepository;
import co.edu.unipaz.taquilla_cineverse_backend.repository.CompraRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionService {

    private final FuncionRepository funcionRepository;
    private final CompraRepository compraRepository;

    public FuncionService(
            FuncionRepository funcionRepository,
            CompraRepository compraRepository) {

        this.funcionRepository = funcionRepository;
        this.compraRepository = compraRepository;
    }

    // Obtener todas las funciones
    public List<Funcion> obtenerTodas() {
        return funcionRepository.findAll();
    }

    // Buscar una función por ID
    public Optional<Funcion> obtenerPorId(Integer id) {
        return funcionRepository.findById(id);
    }

    // Crear una función
    public Funcion guardar(Funcion funcion) {
        return funcionRepository.save(funcion);
    }

    // Actualizar una función
    public Funcion actualizar(Integer id, Funcion funcion) {

        Funcion existente = funcionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("La función no existe"));

        existente.setPeliculaId(funcion.getPeliculaId());
        existente.setFecha(funcion.getFecha());
        existente.setHora(funcion.getHora());
        existente.setSala(funcion.getSala());
        existente.setPrecio(funcion.getPrecio());
        existente.setCuposDisponibles(funcion.getCuposDisponibles());

        return funcionRepository.save(existente);
    }

    // Eliminar una función
    @Transactional
    public void eliminar(Integer id) {

        Funcion existente = funcionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("La función no existe"));

        // Verificar si existen compras asociadas
        boolean tieneCompras = compraRepository.existsByFuncionId(id);

        if (tieneCompras) {
            throw new RuntimeException(
                    "No se puede eliminar esta función porque tiene compras registradas."
            );
        }

        // Eliminar solamente si no tiene compras
        funcionRepository.delete(existente);
    }
}
