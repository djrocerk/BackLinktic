package PruebaT.ecommerce.controller.producto;

import PruebaT.ecommerce.dto.ProductosDTO;
import PruebaT.ecommerce.service.impl.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//http://localhost:8080/api-app
@RequestMapping("api-app")
@CrossOrigin(value = "http://localhost:4200")
public class ProductoController {

    private static final Logger logger =
            LoggerFactory.getLogger(ProductoController.class);


    @Autowired
    private ProductoService productoService;

    @GetMapping("/producto")
    public List<ProductosDTO> listarProducto() {
        return this.productoService.listarProducto();
    }

    @GetMapping("/producto/{id}")
    public ResponseEntity<ProductosDTO> obtenerProductoPorId(@PathVariable int id){
        return ResponseEntity.ok(this.productoService.buscarProductoId(id));
    }

    @PostMapping("/producto")
    public ProductosDTO agregarProducto(@RequestBody ProductosDTO producto){
        var productoGuardado = this.productoService.guardarProducto(producto);
        logger.info("producto agregado" + producto);
        return productoGuardado;
    }

    @PutMapping("/producto")
    public ResponseEntity<ProductosDTO> actualizarProducto(@RequestBody ProductosDTO producto){
        ProductosDTO productoActualizado = productoService.actualizarProducto(producto);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/producto/{id}")
    public ResponseEntity EliminarProducto(@PathVariable int id){
        productoService.eliminarProducto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
