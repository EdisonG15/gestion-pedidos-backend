package com.portafolio.gestionpedidos.dto.producto.response;

import com.portafolio.gestionpedidos.enums.EstadoProducto;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private EstadoProducto estado;

//    @Schema(description = "Nombre del producto", example = "Camiseta")
//    private String nombre;
//
//    @Schema(description = "Descripción del producto", example = "Camiseta 100% algodón")
//    private String descripcion;
//
//    @Schema(description = "Precio del producto", example = "49.99")
//    private BigDecimal precio;
//
//    @Schema(description = "Stock disponible", example = "100")
//    private Integer stock;
//
//    @Schema(description = "Estado del producto", example = "ACTIVO")
//    private EstadoProducto estado;

//    public ProductoResponse(Long id, String nombre, String descripcion, BigDecimal precio, Integer stock, EstadoProducto estado) {
//    }
}
