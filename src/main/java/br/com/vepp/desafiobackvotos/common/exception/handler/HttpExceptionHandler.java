package br.com.vepp.desafiobackvotos.common.exception.handler;


import br.com.vepp.desafiobackvotos.common.exception.BadRequestException;
import br.com.vepp.desafiobackvotos.common.exception.NotFoundException;
import br.com.vepp.desafiobackvotos.domain.models.ErrorDTO;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.enums.ErrorTypeEnum;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestControllerAdvice
public class HttpExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public Mono<ResponseEntity<Object>> handleNotFoundException(NotFoundException e, ServerWebExchange request) {
        ErrorDTO error = new ErrorDTO(
                HttpStatus.NOT_FOUND.value(),
                ErrorTypeEnum.NOT_FOUND.toString(),
                ErrorTypeEnum.NOT_FOUND.getMessage(),
                new Date().getTime(),
                e.getMessage(),
                request.getRequest().getURI().toString());
        return handleExceptionInternal(e, error, null, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(BadRequestException.class)
    public Mono<ResponseEntity<Object>> handleBadRequestException(BadRequestException e, ServerWebExchange request) {

        ErrorDTO error = new ErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                ErrorTypeEnum.BAD_REQUEST.toString(),
                ErrorTypeEnum.BAD_REQUEST.getMessage(),
                new Date().getTime(),
                e.getMessage(),
                request.getRequest().getURI().toString());
        return handleExceptionInternal(e, error, null, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(FeignException.class)
    public Mono<ResponseEntity<Object>> handleFeignException(FeignException e, ServerWebExchange request) {
        ErrorDTO error = new ErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                ErrorTypeEnum.BAD_REQUEST.toString(),
                ErrorTypeEnum.BAD_REQUEST.getMessage(),
                new Date().getTime(),
                e.getMessage(),
                request.getRequest().getURI().toString());
        return handleExceptionInternal(e, error, null, HttpStatus.NOT_FOUND, request);
    }
}
