package com.portafolio.gestionpedidos.service;


import com.portafolio.gestionpedidos.dto.common.exception.ResourceNotFoundException;
import com.portafolio.gestionpedidos.dto.producto.request.ProductoRequest;
import com.portafolio.gestionpedidos.dto.producto.response.ProductoResponse;
import com.portafolio.gestionpedidos.entity.Producto;

import com.portafolio.gestionpedidos.mapper.ProductoMapper;
import com.portafolio.gestionpedidos.repository.producto.ProductoRepository;
import com.portafolio.gestionpedidos.service.producto.ProductoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoServiceImpl productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarProductos_deberiaRetornarMapaConProductos() {
        // Arrange: Crear productos de prueba
        Producto p1 = new Producto();
        p1.setNombre("P1");
        Producto p2 = new Producto();
        p2.setNombre("P2");

        List<Producto> productos = List.of(p1, p2);
        Page<Producto> mockPage = new PageImpl<>(productos);

        // Mock del repositorio
        when(productoRepository.findAll(PageRequest.of(0, 10))).thenReturn(mockPage);

        // Act
        Map<String, Object> resultado = productoService.listarProductos(0, 10);

        // Assert
        List<ProductoResponse> productosResponse = (List<ProductoResponse>) resultado.get("productos");
        long total = (long) resultado.get("total");
        int pages = (int) resultado.get("pages");

        assertEquals(2, productosResponse.size());
        assertEquals("P1", productosResponse.get(0).getNombre());
        assertEquals(2L, total);
        assertEquals(1, pages);
    }


    @Test
    void obtenerProductoPorId_cuandoExiste_deberiaRetornarProducto() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Test");

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        ProductoResponse response = productoService.obtenerProductoPorId(1L);

        assertNotNull(response);
        assertEquals("Test", response.getNombre());
    }

    @Test
    void obtenerProductoPorId_cuandoNoExiste_deberiaLanzarExcepcion() {
        when(productoRepository.findById(999L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            productoService.obtenerProductoPorId(999L);
        });

        assertEquals("Producto no encontrado con ID: 999", ex.getMessage());
    }

    @Test
    void crearProducto_deberiaGuardarYRetornarProducto() {
        ProductoRequest request = new ProductoRequest();
        request.setNombre("Nuevo");
        request.setDescripcion("Desc");
        request.setPrecio(BigDecimal.valueOf(10.0));
        request.setStock(5);
        request.setEstado("ACTIVO");
        Producto entity = ProductoMapper.toEntity(request);
        entity.setId(1L);

        when(productoRepository.save(any())).thenReturn(entity);

        ProductoResponse response = productoService.crearProducto(request);

        assertNotNull(response);
        assertEquals("Nuevo", response.getNombre());
    }

    @Test
    void actualizarProducto_cuandoExiste_deberiaActualizarYRetornar() {
        Producto productoExistente = new Producto();
        productoExistente.setId(1L);
        productoExistente.setNombre("Viejo");

        ProductoRequest request = new ProductoRequest();
        request.setNombre("Actualizado");
        request.setDescripcion("Nueva Desc");
        request.setPrecio(BigDecimal.valueOf(99.0));
        request.setStock(50);
        request.setEstado("ACTIVO");
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoExistente));
        when(productoRepository.save(any())).thenReturn(productoExistente);

        ProductoResponse response = productoService.actualizarProducto(1L, request);

        assertEquals("Actualizado", response.getNombre());
    }

    @Test
    void actualizarProducto_cuandoNoExiste_deberiaLanzarExcepcion() {
        when(productoRepository.findById(123L)).thenReturn(Optional.empty());

        ProductoRequest request = new ProductoRequest();
        request.setNombre("Cualquiera");

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            productoService.actualizarProducto(123L, request);
        });

        assertTrue(ex.getMessage().contains("Producto no encontrado"));
    }

    @Test
    void eliminarProducto_cuandoExiste_deberiaActualizarStock() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setStock(10);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        productoService.eliminarProducto(1L);

        assertEquals(0, producto.getStock());
        verify(productoRepository).save(producto);
    }

    @Test
    void eliminarProducto_cuandoNoExiste_deberiaLanzarExcepcion() {
        when(productoRepository.findById(888L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            productoService.eliminarProducto(888L);
        });
    }
}
