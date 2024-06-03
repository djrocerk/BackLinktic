package PruebaT.ecommerce.dto;

import PruebaT.ecommerce.model.Orden;
import PruebaT.ecommerce.model.Productos;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleOrdenDTO {
    private Integer idDetallepedido;
    private Productos productos;
    private Orden pedido;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
}
