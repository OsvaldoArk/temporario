package com.projeto.ecommerce.exceptionhandler;

import java.net.http.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ProjetoExceptionHandler extends ResponseEntityExceptionHandler{
    @Autowired
    private MessageSource messageSource;
    
    protected ResponseEntity<Object> handleHttpMensageNotReadable(HttpMessageNotReadableException ex, 
                                    org.springframework.http.HttpHeaders headers, HttpStatus status,
                                     WebRequest request){
        
        String mensagemUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
        
        return handleExceptionInternal(ex, mensagemUsuario , headers, HttpStatus.BAD_REQUEST, request);
    }
}
