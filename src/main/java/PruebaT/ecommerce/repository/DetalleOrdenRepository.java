package PruebaT.ecommerce.repository;

import PruebaT.ecommerce.model.DetalleOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Integer> {

    @Query("SELECT d FROM DetalleOrden d WHERE d.pedido.id = :pedido_id")
    List<DetalleOrden> findByPedidoId(@Param("pedido_id") Integer pedido_id);
}
