package PruebaT.ecommerce.service.impl;


import PruebaT.ecommerce.dto.DetalleOrdenDTO;
import PruebaT.ecommerce.dto.OrdenDTO;
import PruebaT.ecommerce.exception.ProductoNoEncontradoException;
import PruebaT.ecommerce.exception.RecursoNoEncontradoException;
import PruebaT.ecommerce.exception.StockInsuficienteException;
import PruebaT.ecommerce.model.DetalleOrden;
import PruebaT.ecommerce.model.Orden;
import PruebaT.ecommerce.model.Productos;
import PruebaT.ecommerce.repository.DetalleOrdenRepository;
import PruebaT.ecommerce.repository.OrdenRepository;
import PruebaT.ecommerce.repository.ProductosRepository;
import PruebaT.ecommerce.service.IOrdenService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdenService implements IOrdenService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private DetalleOrdenRepository detalleOrdenRepository;

    @Autowired
    private ProductosRepository productosRepository;

    @Override
    public List<OrdenDTO> listarOrden() {
        var ordenes = this.ordenRepository.findAll();
        return ordenes.stream()
                .map(orden -> this.modelMapper.map(orden , OrdenDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrdenDTO buscarOrdenId(Integer id_orden) {
        var orden =  this.ordenRepository.findById(id_orden)
                .orElseThrow(() -> new RecursoNoEncontradoException("orden no encontrado"));
        return this.modelMapper.map(orden, OrdenDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrdenDTO crearOrden(List<DetalleOrdenDTO> detalleOrden) {
        try {
            double total = 0.0;
            for (DetalleOrdenDTO detalle : detalleOrden) {
                int productoId = detalle.getProductos().getIdProducto();
                Productos producto = productosRepository.findById(productoId)
                        .orElseThrow(() -> new ProductoNoEncontradoException("No existe el producto con ID: " + productoId));

                int cantidad = detalle.getCantidad();
                if (producto.getStock() < cantidad) {
                    throw new StockInsuficienteException("No hay suficiente stock para el producto: " + producto.getNombre());
                }

                double precioUnitario = producto.getPrecio();
                double subtotal = cantidad * precioUnitario;
                total += subtotal;

                producto.setStock(producto.getStock() - cantidad);
                productosRepository.save(producto);

                detalle.setPrecioUnitario(precioUnitario);
                detalle.setSubtotal(subtotal);
            }

            Orden orden = new Orden();
            orden.setFecha(new Date());
            orden.setTotal(total);

            // Guardar la orden en la base de datos
            orden = ordenRepository.save(orden);

            for (DetalleOrdenDTO detalleOrdenDTO : detalleOrden) {
                DetalleOrden detalleOrden1 = modelMapper.map(detalleOrdenDTO, DetalleOrden.class);
                detalleOrden1.setPedido(orden);
                detalleOrdenRepository.save(detalleOrden1);
            }

            return modelMapper.map(orden, OrdenDTO.class);
        } catch (ProductoNoEncontradoException | StockInsuficienteException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear la orden: " + e.getMessage(), e);
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void eliminarOrden(Integer id_orden) {
        OrdenDTO ordenDTO = buscarOrdenId(id_orden);
        List<DetalleOrden> detallesOrden = ordenDTO.getDetalles();
        for (DetalleOrden detalleOrden : detallesOrden) {
            Productos producto = detalleOrden.getProductos();
            producto.setStock(producto.getStock() + detalleOrden.getCantidad());
            productosRepository.save(producto);
        }
        ordenRepository.deleteById(ordenDTO.getIdPedido());
    }
}
