package com.portafolio.gestionpedidos.mapper;

import com.portafolio.gestionpedidos.dto.producto.request.ProductoRequest;
import com.portafolio.gestionpedidos.dto.producto.response.ProductoResponse;
import com.portafolio.gestionpedidos.entity.Producto;
import com.portafolio.gestionpedidos.enums.EstadoProducto;

import java.util.List;
import java.util.stream.Collectors;

public class ProductoMapper {
    public static Producto toEntity(ProductoRequest request) {
        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        try {
            producto.setEstado(EstadoProducto.valueOf(request.getEstado().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado inválido: " + request.getEstado());
        }
        return producto;
    }

    public static ProductoResponse toProductoResponse(Producto producto) {
        return new ProductoResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getEstado()
        );
    }

    public static List<ProductoResponse> toResponseList(List<Producto> productos) {
        return productos.stream()
                .map(ProductoMapper::toProductoResponse)
                .collect(Collectors.toList());
    }
}
