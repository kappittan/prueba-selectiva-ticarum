package com.ticarum.prueba_selectiva.exceptions;

// Esta es la excepción base de la que heredan todas las demás excepciones personalizadas.
public class ExcepcionBase extends RuntimeException {

    private final int status;

    public ExcepcionBase(String mensaje, int status) {
        super(mensaje);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
