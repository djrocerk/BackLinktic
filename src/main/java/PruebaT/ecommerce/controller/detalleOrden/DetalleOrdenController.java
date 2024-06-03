package PruebaT.ecommerce.controller.detalleOrden;


import PruebaT.ecommerce.dto.DetalleOrdenDTO;
import PruebaT.ecommerce.service.impl.DetalleOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//http://localhost:8080/api-app
@RequestMapping("api-app")
@CrossOrigin(value = "http://localhost:4200")
public class DetalleOrdenController {
    @Autowired
    private DetalleOrdenService detalleOrdenService;

    @GetMapping("/detalle/{pedido_id}")
    public ResponseEntity<List<DetalleOrdenDTO>> getDetallesPorPedidoId(@PathVariable Integer pedido_id) {
        List<DetalleOrdenDTO> detalles = detalleOrdenService.listarDetalleOrdenIdOrden(pedido_id);
        return ResponseEntity.ok(detalles);
    }


}
