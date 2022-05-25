package com.epam.esm.api;

import com.epam.esm.response.DaoException;
import com.epam.esm.response.ErrorResponse;
import com.epam.esm.response.ErrorResponseCode;
import com.sun.java.accessibility.util.Translator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(DaoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleDaoException(DaoException e) {
        int errorCode = e.getErrorCode();
        String response = ErrorResponseCode.errorResponseCode(errorCode);
        if (e.getObjectId() != 0) {
            response += " = " + e.getObjectId();
        }
        return new ErrorResponse(errorCode,response);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public @ResponseBody ErrorResponse handleMethodNotAllowed() {
        return new ErrorResponse(405,ErrorResponseCode.errorResponseCode(405));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public @ResponseBody ErrorResponse handleMethodNotFound() {
        return new ErrorResponse(404,"Method not found");
    }
}
