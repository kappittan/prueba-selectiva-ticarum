package com.ticarum.prueba_selectiva.exceptions;

// Esta es la clase que se va a devolver en el manejador en caso de error. 
// Contiene el status y el mensaje para indicar en que consiste el error y 
// posibilitar una solución en la parte del cliente.
public class ErrorResponse {
    private int status;
    private String mensaje;

    public ErrorResponse(int status, String mensaje) {
        this.status = status;
        this.mensaje = mensaje;
    }

    public int getStatus() {
        return status;
    }

    public String getMensaje() {
        return mensaje;
    }
}
