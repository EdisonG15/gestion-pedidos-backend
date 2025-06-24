package com.portafolio.gestionpedidos.repository.producto;

import com.portafolio.gestionpedidos.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  ProductoRepository extends JpaRepository<Producto, Long> {
}
