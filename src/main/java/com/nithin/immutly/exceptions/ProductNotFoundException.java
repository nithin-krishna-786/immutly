package com.nithin.immutly.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -8361188947692787335L;

	public ProductNotFoundException(String message) {
        super(message);
    }

}
