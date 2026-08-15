package com.icwd.electronic.store.exception;

import com.icwd.electronic.store.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GlobalExceptionHandler {

     @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException ex, ApiResponse api){
         ApiResponse message = ApiResponse.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();
         return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);

     }


     public ResponseEntity<Map<String,Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception)
     {
         List<ObjectError> allErrors = exception.getBindingResult().getAllErrors();
         Map<String,Object> response= new HashMap<>();
         allErrors.stream().forEach(errors->{
             String value = errors.getDefaultMessage();
             String key = ((FieldError) errors).getField();
             response.put(key,value);
         });

         return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
     }

    @ExceptionHandler({BadApiRequest.class})
    public ResponseEntity<ApiResponse> handleBadRequest(BadApiRequest ex, ApiResponse api){
        ApiResponse message = ApiResponse.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(false).build();
        return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);

    }
}
