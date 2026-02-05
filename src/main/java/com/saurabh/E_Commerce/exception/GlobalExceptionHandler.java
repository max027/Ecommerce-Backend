package com.saurabh.E_Commerce.exception;

import com.saurabh.E_Commerce.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handelValidationException(MethodArgumentNotValidException ex){
        Map<String,String>map=new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName=((FieldError)error).getField();
            String errorMessage=error.getDefaultMessage();
            map.put(fieldName,errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(ApiError.class)
    public ResponseEntity<ErrorResponse> handelApiError(ApiError ex, HttpServletRequest request){
        ErrorResponse response=new ErrorResponse();
        response.setTime(LocalDateTime.now());
        response.setPath(request.getRequestURI());
        response.setMessage(ex.getMessage());
        response.setStatusCode(ex.getStatusCode());

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse>handelBadCredential(BadCredentialsException ex,HttpServletRequest request){
        ErrorResponse response=new ErrorResponse();
        response.setTime(LocalDateTime.now());
        response.setPath(request.getRequestURI());
        response.setMessage(ex.getMessage());
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse>handelUserNotFound(UsernameNotFoundException ex,HttpServletRequest request){

        ErrorResponse response=new ErrorResponse();
        response.setTime(LocalDateTime.now());
        response.setPath(request.getRequestURI());
        response.setMessage(ex.getMessage());
        response.setStatusCode(HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse>handelIlleagalException(IllegalArgumentException ex,HttpServletRequest request){

        ErrorResponse response=new ErrorResponse();
        response.setTime(LocalDateTime.now());
        response.setPath(request.getRequestURI());
        response.setMessage(ex.getMessage());
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handelRuntimeError(RuntimeException ex,HttpServletRequest request){
        ErrorResponse response=new ErrorResponse();
        response.setTime(LocalDateTime.now());
        response.setPath(request.getRequestURI());
        response.setMessage("internal server error");
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handelApiError(Exception ex,HttpServletRequest request){
        ErrorResponse response=new ErrorResponse();
        response.setTime(LocalDateTime.now());
        response.setPath(request.getRequestURI());
        response.setMessage("internal server error");
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
