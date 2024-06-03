package PruebaT.ecommerce.service.impl;

import PruebaT.ecommerce.dto.DetalleOrdenDTO;
import PruebaT.ecommerce.dto.OrdenDTO;
import PruebaT.ecommerce.exception.RecursoNoEncontradoException;
import PruebaT.ecommerce.model.DetalleOrden;
import PruebaT.ecommerce.model.Orden;
import PruebaT.ecommerce.model.Productos;
import PruebaT.ecommerce.repository.DetalleOrdenRepository;
import PruebaT.ecommerce.repository.OrdenRepository;
import PruebaT.ecommerce.repository.ProductosRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrdenServiceTest {

    @Mock
    private ModelMapper mockModelMapper;
    @Mock
    private OrdenRepository mockOrdenRepository;
    @Mock
    private DetalleOrdenRepository mockDetalleOrdenRepository;
    @Mock
    private ProductosRepository mockProductosRepository;

    @InjectMocks
    private OrdenService ordenServiceUnderTest;

    @Test
    void testListarOrden() {

        final Orden orden = new Orden();
        orden.setIdPedido(0);
        orden.setFecha(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        orden.setTotal(0.0);
        final DetalleOrden detalleOrden = new DetalleOrden();
        detalleOrden.setIdDetallepedido(0);
        orden.setDetalles(List.of(detalleOrden));
        final List<Orden> ordens = List.of(orden);
        when(mockOrdenRepository.findAll()).thenReturn(ordens);

        final OrdenDTO ordenDTO = new OrdenDTO();
        ordenDTO.setIdPedido(0);
        ordenDTO.setFecha(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        ordenDTO.setTotal(0.0);
        final Orden source = new Orden();
        source.setIdPedido(0);
        source.setFecha(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        source.setTotal(0.0);
        final DetalleOrden detalleOrden1 = new DetalleOrden();
        detalleOrden1.setIdDetallepedido(0);
        source.setDetalles(List.of(detalleOrden1));
        when(mockModelMapper.map(source, OrdenDTO.class)).thenReturn(ordenDTO);

        final List<OrdenDTO> result = ordenServiceUnderTest.listarOrden();

    }

    @Test
    void testListarOrden_OrdenRepositoryReturnsNoItems() {
        when(mockOrdenRepository.findAll()).thenReturn(Collections.emptyList());

        final List<OrdenDTO> result = ordenServiceUnderTest.listarOrden();
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testBuscarOrdenId() {
        final Orden orden1 = new Orden();
        orden1.setIdPedido(0);
        orden1.setFecha(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        orden1.setTotal(0.0);
        final DetalleOrden detalleOrden = new DetalleOrden();
        detalleOrden.setIdDetallepedido(0);
        orden1.setDetalles(List.of(detalleOrden));
        final Optional<Orden> orden = Optional.of(orden1);
        when(mockOrdenRepository.findById(0)).thenReturn(orden);

        final OrdenDTO ordenDTO = new OrdenDTO();
        ordenDTO.setIdPedido(0);
        ordenDTO.setFecha(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        ordenDTO.setTotal(0.0);
        final Orden source = new Orden();
        source.setIdPedido(0);
        source.setFecha(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        source.setTotal(0.0);
        final DetalleOrden detalleOrden1 = new DetalleOrden();
        detalleOrden1.setIdDetallepedido(0);
        source.setDetalles(List.of(detalleOrden1));
        when(mockModelMapper.map(source, OrdenDTO.class)).thenReturn(ordenDTO);

        final OrdenDTO result = ordenServiceUnderTest.buscarOrdenId(0);
    }

    @Test
    void testBuscarOrdenId_OrdenRepositoryReturnsAbsent() {
        when(mockOrdenRepository.findById(0)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ordenServiceUnderTest.buscarOrdenId(0))
                .isInstanceOf(RecursoNoEncontradoException.class);
    }

    @Test
    void testCrearOrden() {
        final DetalleOrdenDTO detalleOrdenDTO = new DetalleOrdenDTO();
        final Productos productos = new Productos();
        productos.setIdProducto(0);
        detalleOrdenDTO.setProductos(productos);
        detalleOrdenDTO.setCantidad(1);
        final List<DetalleOrdenDTO> detalleOrden = List.of(detalleOrdenDTO);

        final Productos productos2 = new Productos();
        productos2.setIdProducto(0);
        productos2.setNombre("nombre");
        productos2.setDescripcion("descripcion");
        productos2.setStock(10);
        productos2.setPrecio(100.0);
        final Optional<Productos> productos1 = Optional.of(productos2);
        when(mockProductosRepository.findById(0)).thenReturn(productos1);

        when(mockOrdenRepository.save(any(Orden.class))).thenAnswer(invocation -> {
            Orden argument = invocation.getArgument(0);
            argument.setIdPedido(1);
            return argument;
        });

        when(mockModelMapper.map(any(Object.class), eq(DetalleOrden.class))).thenAnswer(invocation -> {
            DetalleOrdenDTO detalle = invocation.getArgument(0);
            DetalleOrden detalleOrden1 = new DetalleOrden();
            detalleOrden1.setIdDetallepedido(0);
            detalleOrden1.setProductos(productos2);
            return detalleOrden1;
        });

        final OrdenDTO result = ordenServiceUnderTest.crearOrden(detalleOrden);

        verify(mockProductosRepository).save(productos2);

        final DetalleOrden entity2 = new DetalleOrden();
        entity2.setIdDetallepedido(0);
        entity2.setProductos(productos2);

        ArgumentMatcher<DetalleOrden> detalleOrdenMatcher = new ArgumentMatcher<>() {
            @Override
            public boolean matches(DetalleOrden detalleOrden) {
                return detalleOrden.getIdDetallepedido() == entity2.getIdDetallepedido() &&
                        detalleOrden.getProductos().equals(entity2.getProductos()) &&
                        detalleOrden.getCantidad() == entity2.getCantidad() &&
                        detalleOrden.getPrecioUnitario() == entity2.getPrecioUnitario() &&
                        detalleOrden.getSubtotal() == entity2.getSubtotal();
            }
        };

        verify(mockDetalleOrdenRepository).save(argThat(detalleOrdenMatcher));
    }

    @Test
    void testCrearOrden_ProductosRepositoryFindByIdReturnsAbsent() {
        final DetalleOrdenDTO detalleOrdenDTO = new DetalleOrdenDTO();
        final Productos productos = new Productos();
        productos.setIdProducto(0);
        productos.setStock(0);
        productos.setPrecio(0.0);
        detalleOrdenDTO.setProductos(productos);
        detalleOrdenDTO.setCantidad(0);
        detalleOrdenDTO.setPrecioUnitario(0.0);
        detalleOrdenDTO.setSubtotal(0.0);
        final List<DetalleOrdenDTO> detalleOrden = List.of(detalleOrdenDTO);
        when(mockProductosRepository.findById(0)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ordenServiceUnderTest.crearOrden(detalleOrden))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void testEliminarPedido_OrdenRepositoryFindByIdReturnsAbsent() {
        when(mockOrdenRepository.findById(0)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ordenServiceUnderTest.eliminarOrden(0))
                .isInstanceOf(RecursoNoEncontradoException.class);
    }
}
