package com.portafolio.gestionpedidos.dto.producto.request;

import lombok.*;
import java.math.BigDecimal;
import jakarta.validation.constraints.*;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductoRequest {
    @NotBlank(message = "El nombre es obligatorio.")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria.")
    @Size(max = 255, message = "La descripción no debe superar 255 caracteres.")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio.")
    @Positive(message = "El precio debe ser mayor a cero.")
    private BigDecimal precio;

    @NotNull(message = "El stock es obligatorio.")
    @Min(value = 0, message = "El stock no puede ser negativo.")
    private Integer stock;

    @NotBlank(message = "El estado es obligatorio.")
    private String estado;
}
