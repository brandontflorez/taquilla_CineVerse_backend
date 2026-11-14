package co.edu.unipaz.taquilla_cineverse_backend.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("compras")
public class Compra {

    @Id
    private Integer id;

    @NotNull(message = "Debe seleccionar una función")
    private Integer funcionId;

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String nombreCliente;

    @NotNull(message = "La cantidad de boletos es obligatoria")
    @Min(value = 1, message = "Debe comprar al menos un boleto")
    private Integer cantidadBoletos;

    private BigDecimal valorTotal;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCompra;

    public Compra() {
    }

    public Compra(Integer id, Integer funcionId, String nombreCliente,
                  Integer cantidadBoletos, BigDecimal valorTotal,
                  LocalDateTime fechaCompra) {
        this.id = id;
        this.funcionId = funcionId;
        this.nombreCliente = nombreCliente;
        this.cantidadBoletos = cantidadBoletos;
        this.valorTotal = valorTotal;
        this.fechaCompra = fechaCompra;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFuncionId() {
        return funcionId;
    }

    public void setFuncionId(Integer funcionId) {
        this.funcionId = funcionId;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Integer getCantidadBoletos() {
        return cantidadBoletos;
    }

    public void setCantidadBoletos(Integer cantidadBoletos) {
        this.cantidadBoletos = cantidadBoletos;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }
}