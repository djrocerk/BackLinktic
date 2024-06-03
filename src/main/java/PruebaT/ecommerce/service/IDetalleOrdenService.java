package PruebaT.ecommerce.service;

import PruebaT.ecommerce.dto.DetalleOrdenDTO;

import java.util.List;

public interface IDetalleOrdenService {
    public List<DetalleOrdenDTO> listarDetalleOrdenIdOrden(Integer pedido_id);
}
