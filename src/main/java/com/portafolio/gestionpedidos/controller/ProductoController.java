package com.portafolio.gestionpedidos.controller;

import com.portafolio.gestionpedidos.dto.common.messages.MessageCode;
import com.portafolio.gestionpedidos.dto.common.response.StandardResponse;
import com.portafolio.gestionpedidos.dto.producto.request.ProductoRequest;
import com.portafolio.gestionpedidos.dto.producto.response.ProductoResponse;
import com.portafolio.gestionpedidos.service.producto.ProductoService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "API para gestión de productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Operation(summary = "Listar todos los productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<StandardResponse> listarProductos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Map<String, Object> data = productoService.listarProductos(page, size);

        StandardResponse response = new StandardResponse(
                MessageCode.OK.getMessage(),
                String.valueOf(MessageCode.OK.getCode()),
                data
        );

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener producto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<StandardResponse> obtenerProductoPorId(@PathVariable Long id) {
        ProductoResponse producto = productoService.obtenerProductoPorId(id);
        StandardResponse response = new StandardResponse(
                MessageCode.OK.getMessage(),
                String.valueOf(MessageCode.OK.getCode()),
                Map.of("producto", producto)
        );
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Crear un nuevo producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<StandardResponse> crearProducto(@Valid  @RequestBody ProductoRequest request) {
        ProductoResponse creado = productoService.crearProducto(request);
        StandardResponse response = new StandardResponse(
                MessageCode.CREATED.getMessage(),
                String.valueOf(MessageCode.CREATED.getCode()),
                Map.of("producto", creado)
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Actualizar un producto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<StandardResponse> actualizarProducto(@PathVariable Long id, @Valid @RequestBody ProductoRequest request) {
        ProductoResponse actualizado = productoService.actualizarProducto(id, request);
        StandardResponse response = new StandardResponse(
                MessageCode.UPDATED.getMessage(),
                String.valueOf(MessageCode.UPDATED.getCode()),
                Map.of("producto", actualizado)
        );
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar un producto (eliminación lógica)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<StandardResponse> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        StandardResponse response = new StandardResponse(
                MessageCode.DELETED.getMessage(),
                String.valueOf(MessageCode.DELETED.getCode()),
                Map.of("mensaje", "Producto eliminado lógicamente")
        );
        return ResponseEntity.ok(response);
    }
}
