package com.ticarum.prueba_selectiva.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ManejadorGlobalExcepciones {

    // Este manejador va a capturar todas las excepciones que hereden de
    // ExcepcionBase, las cuales son las que hemos definido de manera personalizada
    // para esta apliación web.
    @ExceptionHandler(ExcepcionBase.class)
    public ResponseEntity<ErrorResponse> manejarExcepcionPropia(ExcepcionBase ex) {
        System.out.println("Manejando ExcepcionBase: " + ex.getMessage());
        ErrorResponse error = new ErrorResponse(ex.getStatus(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.valueOf(ex.getStatus()));
    }

    // Este otro manejador va a capturar el resto de excepciones que no capture el
    // primero.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarExcepcionGenerica(Exception ex) {
        System.out.println("Manejando Exception genérica: " + ex.getMessage());
        ErrorResponse error = new ErrorResponse(500, "Error interno del servidor");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
