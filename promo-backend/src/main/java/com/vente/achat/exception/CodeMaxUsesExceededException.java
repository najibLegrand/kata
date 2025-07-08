package com.vente.achat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// exception/CodeMaxUsesExceededException.java
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public class CodeMaxUsesExceededException extends DiscountException {
        public CodeMaxUsesExceededException(String code) { super("Limite d’utilisation atteinte pour " + code); }
    }
