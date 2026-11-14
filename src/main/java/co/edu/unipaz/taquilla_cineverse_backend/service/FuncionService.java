package co.edu.unipaz.taquilla_cineverse_backend.service;

import co.edu.unipaz.taquilla_cineverse_backend.model.Funcion;
import co.edu.unipaz.taquilla_cineverse_backend.repository.FuncionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionService {

    private final FuncionRepository funcionRepository;

    public FuncionService(FuncionRepository funcionRepository) {
        this.funcionRepository = funcionRepository;
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
        funcion.setId(id);
        return funcionRepository.save(funcion);
    }

    // Eliminar una función
    public void eliminar(Integer id) {
        funcionRepository.deleteById(id);
    }
}