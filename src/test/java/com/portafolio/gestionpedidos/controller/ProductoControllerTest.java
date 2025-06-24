package com.portafolio.gestionpedidos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portafolio.gestionpedidos.dto.producto.response.ProductoResponse;
import com.portafolio.gestionpedidos.service.producto.ProductoService;

import com.portafolio.gestionpedidos.dto.producto.request.ProductoRequest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listarProductos_deberiaRetornar200() throws Exception {
        // Arrange: simulamos un ProductoResponse
        ProductoResponse mockProducto = new ProductoResponse();
        mockProducto.setNombre("Test Producto");

        // Simulamos la respuesta paginada que el servicio devolverá
        Map<String, Object> mockData = Map.of(
                "productos", List.of(mockProducto),
                "total", 1L,
                "pages", 1
        );

        // Mock del servicio con paginación
        Mockito.when(productoService.listarProductos(0, 10)).thenReturn(mockData);

        // Act & Assert
        mockMvc.perform(get("/api/productos")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.productos[0].nombre").value("Test Producto"))
                .andExpect(jsonPath("$.data.total").value(1))
                .andExpect(jsonPath("$.data.pages").value(1));
    }

    @Test
    void obtenerProductoPorId_deberiaRetornar200() throws Exception {
        ProductoResponse mockProducto = new ProductoResponse();
        mockProducto.setNombre("Producto X");

        Mockito.when(productoService.obtenerProductoPorId(1L)).thenReturn(mockProducto);

        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.producto.nombre").value("Producto X"));
    }


    @Test
    void crearProducto_deberiaRetornar201() throws Exception {
        ProductoRequest request = new ProductoRequest();
        request.setNombre("Producto A");
        request.setDescripcion("Descripción A");
        request.setPrecio(BigDecimal.valueOf(10.0));
        request.setStock(5);
        request.setEstado("ACTIVO");

        ProductoResponse response = new ProductoResponse();
        response.setNombre("Producto A");

        Mockito.when(productoService.crearProducto(Mockito.any())).thenReturn(response);

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("201"))
                .andExpect(jsonPath("$.data.producto.nombre").value("Producto A"));
    }
    @Test
    void actualizarProducto_deberiaRetornar200() throws Exception {
        ProductoRequest request = new ProductoRequest();
        request.setNombre("Nuevo Nombre");
        request.setDescripcion("Actualizado");
        request.setPrecio(BigDecimal.valueOf(99.0));
        request.setStock(100);
        request.setEstado("ACTIVO");
        ProductoResponse response = new ProductoResponse();
        response.setNombre("Nuevo Nombre");

        Mockito.when(productoService.actualizarProducto(Mockito.eq(1L), Mockito.any()))
                .thenReturn(response);

        mockMvc.perform(put("/api/productos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.producto.nombre").value("Nuevo Nombre"));
    }
    @Test
    void eliminarProducto_deberiaRetornar200() throws Exception {
        Mockito.doNothing().when(productoService).eliminarProducto(1L);

        mockMvc.perform(delete("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.mensaje").value("Producto eliminado lógicamente"));
    }

}
