package com.vente.achat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// exception/CodeNotApplicableException.java
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public class CodeNotApplicableException extends DiscountException {
        public CodeNotApplicableException(String code) { super("Code " + code + " non applicable"); }
    }
