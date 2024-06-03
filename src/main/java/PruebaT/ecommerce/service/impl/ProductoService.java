package PruebaT.ecommerce.service.impl;

import PruebaT.ecommerce.dto.ProductosDTO;
import PruebaT.ecommerce.exception.RecursoNoEncontradoException;
import PruebaT.ecommerce.model.Productos;
import PruebaT.ecommerce.repository.ProductosRepository;
import PruebaT.ecommerce.service.IProductoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService implements IProductoService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductosRepository productosRepository;

    @Override
    public List<ProductosDTO> listarProducto() {
       var productos = this.productosRepository.findAll();
       return productos.stream()
               .map(producto -> this.modelMapper.map(producto, ProductosDTO.class))
               .collect(Collectors.toList());
    }

    @Override
    public ProductosDTO buscarProductoId(Integer id_producto) {
       var producto =  this.productosRepository.findById(id_producto)
               .orElseThrow(() -> new RecursoNoEncontradoException("producto no encontrado"));
       return this.modelMapper.map(producto, ProductosDTO.class);
    }

    @Override
    public ProductosDTO actualizarProducto(ProductosDTO producto) {
        var productoEncontrado = this.productosRepository.findById(producto.getIdProducto())
                .orElseThrow(()-> new RecursoNoEncontradoException("producto no encontrado"));
        productoEncontrado.setNombre(producto.getNombre());
        productoEncontrado.setDescripcion(producto.getDescripcion());
        productoEncontrado.setStock(producto.getStock());
        productoEncontrado.setPrecio(producto.getPrecio());
        productoEncontrado.setImagen(producto.getImagen());

        var productoActualizado = productosRepository.save(productoEncontrado);
        return this.modelMapper.map(productoActualizado , ProductosDTO.class);
    }
    @Override
    public ProductosDTO guardarProducto(ProductosDTO producto) {
        var productoGuardado = this.productosRepository.save(modelMapper.map(producto, Productos.class));
        return modelMapper.map(productoGuardado, ProductosDTO.class);

    }

    @Override
    public void eliminarProducto(Integer id_producto) {
        ProductosDTO productosDTO = buscarProductoId(id_producto);
        this.productosRepository.deleteById(productosDTO.getIdProducto());
    }
}
