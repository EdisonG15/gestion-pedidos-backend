package com.portafolio.gestionpedidos.entity;

import com.portafolio.gestionpedidos.enums.EstadoProducto;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "productos")
@Data
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String descripcion;

    private BigDecimal precio;

    private Integer stock;

    @Enumerated(EnumType.STRING)
    private EstadoProducto estado;


    @OneToMany(mappedBy = "producto")
    private List<DetallePedido> detalles;

}
