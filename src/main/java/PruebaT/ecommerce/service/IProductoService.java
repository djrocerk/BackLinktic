package PruebaT.ecommerce.service;

import PruebaT.ecommerce.dto.ProductosDTO;

import java.util.List;

public interface IProductoService {
    public List<ProductosDTO> listarProducto();
    public ProductosDTO buscarProductoId(Integer id_producto);
    public ProductosDTO actualizarProducto(ProductosDTO producto);
    public ProductosDTO guardarProducto(ProductosDTO producto);
    void eliminarProducto(Integer id_producto);
}
