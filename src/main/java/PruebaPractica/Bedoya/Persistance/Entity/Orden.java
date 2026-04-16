package PruebaPractica.Bedoya.Persistance.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orden")
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdenProducto> detalles;
    private LocalDate fecha;
    private Double total;
    private Boolean estado = false;
    @Column(name = "estado_proceso")
    private String estadoProceso;

    @Column(name = "orden_padre_id")
    private Long ordenPadreId;

    @ManyToOne
    @JoinColumn(name = "orden_padre_id", insertable = false, updatable = false)
    private Orden ordenPadre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public List<OrdenProducto> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<OrdenProducto> detalles) {
        this.detalles = detalles;
    }

    public String getEstadoProceso() {
        return estadoProceso;
    }

    public void setEstadoProceso(String estadoProceso) {
        this.estadoProceso = estadoProceso;
    }

    public Long getOrdenPadreId() {
        return ordenPadreId;
    }

    public void setOrdenPadreId(Long ordenPadreId) {
        this.ordenPadreId = ordenPadreId;
    }

    public Orden getOrdenPadre() {
        return ordenPadre;
    }

    public void setOrdenPadre(Orden ordenPadre) {
        this.ordenPadre = ordenPadre;
    }
}
