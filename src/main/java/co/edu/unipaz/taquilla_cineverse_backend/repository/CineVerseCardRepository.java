package co.edu.unipaz.taquilla_cineverse_backend.repository;

import co.edu.unipaz.taquilla_cineverse_backend.model.CineVerseCard;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface CineVerseCardRepository
        extends ListCrudRepository<CineVerseCard, Integer> {

    Optional<CineVerseCard> findByNumeroTarjeta(String numeroTarjeta);
}
