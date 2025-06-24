package com.portafolio.gestionpedidos.dto.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardResponse {
    private String message;
    private String code;
    private Map<String, Object> data;
}
