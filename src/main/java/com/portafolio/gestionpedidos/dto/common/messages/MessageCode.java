package com.portafolio.gestionpedidos.dto.common.messages;


public enum MessageCode {
    OK("Proceso exitoso.", 200),
    CREATED("Creación exitosa.", 201),
    UPDATED("Actualización exitosa.", 200),
    DELETED("Eliminación exitosa.", 200),

    BAD_REQUEST("Hubo un error en su petición.", 400),
    NOT_FOUND("No existen registros.", 404),
    INTERNAL_ERROR("Error en el servidor.", 500),
    CONFLICT("Conflicto en la petición.", 409);

    private final String message;
    private final int code;
    MessageCode(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
