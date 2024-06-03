package PruebaT.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "pedido")
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer idPedido;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "total")
    private Double total;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("pedido")
    private List<DetalleOrden> detalles;

}
