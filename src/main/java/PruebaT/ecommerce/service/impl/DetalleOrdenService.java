package PruebaT.ecommerce.service.impl;

import PruebaT.ecommerce.dto.DetalleOrdenDTO;
import PruebaT.ecommerce.model.DetalleOrden;
import PruebaT.ecommerce.repository.DetalleOrdenRepository;
import PruebaT.ecommerce.service.IDetalleOrdenService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetalleOrdenService implements IDetalleOrdenService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DetalleOrdenRepository detalleOrdenRepository;

    @Override
    public List<DetalleOrdenDTO> listarDetalleOrdenIdOrden(Integer pedido_id) {
        List<DetalleOrden> detalleOrden = detalleOrdenRepository.findByPedidoId(pedido_id);
        return detalleOrden.stream()
                .map(detalle -> modelMapper.map(detalle, DetalleOrdenDTO.class))
                .collect(Collectors.toList());
    }
}
