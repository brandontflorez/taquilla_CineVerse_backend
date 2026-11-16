
package co.edu.unipaz.taquilla_cineverse_backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;

@Table("cineverse_cards")
public class CineVerseCard {

    @Id
    private Integer id;

    @Column("numero_tarjeta")
    private String numeroTarjeta;

    @Column("nombre_titular")
    private String nombreTitular;

    @Column("saldo")
    private BigDecimal saldo;

    public CineVerseCard() {
    }

    public CineVerseCard(Integer id, String numeroTarjeta,
                         String nombreTitular, BigDecimal saldo) {
        this.id = id;
        this.numeroTarjeta = numeroTarjeta;
        this.nombreTitular = nombreTitular;
        this.saldo = saldo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getNombreTitular() {
        return nombreTitular;
    }

    public void setNombreTitular(String nombreTitular) {
        this.nombreTitular = nombreTitular;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
