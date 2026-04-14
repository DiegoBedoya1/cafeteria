package PruebaPractica.Bedoya.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String,String>> manejarExcepciones(RuntimeException ex){
        Map<String,String> mapa = new HashMap<>();
        mapa.put("error","regla de negocio");
        mapa.put("mensaje: ",ex.getMessage());
        return new ResponseEntity<>(mapa, HttpStatus.BAD_REQUEST);
    }
}
