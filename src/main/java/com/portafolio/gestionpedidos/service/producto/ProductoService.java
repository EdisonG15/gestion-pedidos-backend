package com.portafolio.gestionpedidos.service.producto;

import com.portafolio.gestionpedidos.dto.producto.request.ProductoRequest;
import com.portafolio.gestionpedidos.dto.producto.response.ProductoResponse;


import java.util.List;
import java.util.Map;

public interface ProductoService {
    Map<String, Object> listarProductos(int page, int size);

    ProductoResponse obtenerProductoPorId(Long id);
    ProductoResponse crearProducto(ProductoRequest request);
    ProductoResponse actualizarProducto(Long id, ProductoRequest request);
    void eliminarProducto(Long id);

}
