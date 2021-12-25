package jm.spring.rest.controller;

import jm.spring.rest.controller.exception.NoDataFound;
import jm.spring.rest.controller.exception.NoDataFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyAdviceController {

    /**
     * Обрабатывает исключения NoDataFoundException
     */
    @ExceptionHandler
    public ResponseEntity<NoDataFound> handlerException(NoDataFoundException exception) {
        NoDataFound data = new NoDataFound();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    /**
     * Обрабатывает исключения всех типов
     */
    @ExceptionHandler
    public ResponseEntity<NoDataFound> handlerException(Exception exception) {
        NoDataFound data = new NoDataFound();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
