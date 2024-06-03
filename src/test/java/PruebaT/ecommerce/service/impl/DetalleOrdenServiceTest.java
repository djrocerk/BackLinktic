package PruebaT.ecommerce.service.impl;

import PruebaT.ecommerce.dto.DetalleOrdenDTO;
import PruebaT.ecommerce.model.DetalleOrden;
import PruebaT.ecommerce.model.Productos;
import PruebaT.ecommerce.repository.DetalleOrdenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DetalleOrdenServiceTest {

    @Mock
    private ModelMapper mockModelMapper;
    @Mock
    private DetalleOrdenRepository mockDetalleOrdenRepository;

    @InjectMocks
    private DetalleOrdenService detalleOrdenServiceUnderTest;

    @Test
    void testListarDetalleOrdenIdOrden() {

        final DetalleOrden detalleOrden = new DetalleOrden();
        detalleOrden.setIdDetallepedido(0);
        final Productos productos = new Productos();
        productos.setIdProducto(0);
        productos.setNombre("nombre");
        productos.setDescripcion("descripcion");
        detalleOrden.setProductos(productos);
        final List<DetalleOrden> detalleOrdens = List.of(detalleOrden);
        when(mockDetalleOrdenRepository.findByPedidoId(0)).thenReturn(detalleOrdens);

        final DetalleOrdenDTO detalleOrdenDTO = new DetalleOrdenDTO();
        detalleOrdenDTO.setIdDetallepedido(0);
        final Productos productos1 = new Productos();
        productos1.setIdProducto(0);
        productos1.setNombre("nombre");
        productos1.setDescripcion("descripcion");
        detalleOrdenDTO.setProductos(productos1);
        final DetalleOrden source = new DetalleOrden();
        source.setIdDetallepedido(0);
        final Productos productos2 = new Productos();
        productos2.setIdProducto(0);
        productos2.setNombre("nombre");
        productos2.setDescripcion("descripcion");
        source.setProductos(productos2);
        when(mockModelMapper.map(source, DetalleOrdenDTO.class)).thenReturn(detalleOrdenDTO);

        final List<DetalleOrdenDTO> result = detalleOrdenServiceUnderTest.listarDetalleOrdenIdOrden(0);

    }

    @Test
    void testListarDetalleOrdenIdOrden_DetalleOrdenRepositoryReturnsNoItems() {
        when(mockDetalleOrdenRepository.findByPedidoId(0)).thenReturn(Collections.emptyList());

        final List<DetalleOrdenDTO> result = detalleOrdenServiceUnderTest.listarDetalleOrdenIdOrden(0);
        assertThat(result).isEqualTo(Collections.emptyList());
    }
}
