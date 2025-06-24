package com.portafolio.gestionpedidos.service.producto;

import com.portafolio.gestionpedidos.dto.common.exception.ResourceNotFoundException;
import com.portafolio.gestionpedidos.dto.producto.request.ProductoRequest;
import com.portafolio.gestionpedidos.dto.producto.response.ProductoResponse;
import com.portafolio.gestionpedidos.entity.Producto;
import com.portafolio.gestionpedidos.enums.EstadoProducto;
import com.portafolio.gestionpedidos.mapper.ProductoMapper;
import com.portafolio.gestionpedidos.repository.producto.ProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ProductoServiceImpl implements ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Map<String, Object> listarProductos(int page, int size) {
        log.info("Listando productos paginados - página: {}, tamaño: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<Producto> productosPage = productoRepository.findAll(pageable);

        List<ProductoResponse> productos = ProductoMapper.toResponseList(productosPage.getContent());

        return Map.of(
                "productos", productos,
                "total", productosPage.getTotalElements(),
                "pages", productosPage.getTotalPages()
        );
    }


    @Override
    public ProductoResponse obtenerProductoPorId(Long id) {
        log.info("Buscando producto con ID: {}", id);
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
        return ProductoMapper.toProductoResponse(producto);
    }

    @Override
    public ProductoResponse crearProducto(ProductoRequest request) {
        log.info("Creando producto: {}", request.getNombre());
        Producto producto = ProductoMapper.toEntity(request);
        Producto guardado = productoRepository.save(producto);
        log.info("Producto creado exitosamente con ID: {}", guardado.getId());
        return ProductoMapper.toProductoResponse(guardado);
    }

    @Override
    public ProductoResponse actualizarProducto(Long id, ProductoRequest request) {
        log.info("Actualizando producto con ID: {}", id);
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Producto no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("Producto no encontrado con ID: " + id);
                });
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        Producto actualizado = productoRepository.save(producto);
        log.info("Producto actualizado con ID: {}", actualizado.getId());
        return ProductoMapper.toProductoResponse(actualizado);
    }

    @Override
    public void eliminarProducto(Long id) {
        log.info("Eliminando lógicamente producto con ID: {}", id);
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Producto no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("Producto no encontrado con ID: " + id);
                });
        producto.setStock(0);
        producto.setEstado(EstadoProducto.INACTIVO);
        productoRepository.save(producto);
        log.info("Producto eliminado lógicamente con ID: {}", id);

    }



}
