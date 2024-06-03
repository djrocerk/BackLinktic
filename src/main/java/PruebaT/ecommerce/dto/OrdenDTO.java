package PruebaT.ecommerce.dto;

import PruebaT.ecommerce.model.DetalleOrden;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrdenDTO {
    private Integer idPedido;
    private Date fecha;
    private Double total;
    private List<DetalleOrden> detalles;
}
