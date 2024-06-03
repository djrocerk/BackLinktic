package PruebaT.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "DetallePedido")
public class DetalleOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer idDetallepedido;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Productos productos;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Orden pedido;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "precioUnitario")
    private Double precioUnitario;

    @Column(name = "subtotal")
    private Double subtotal;

}
