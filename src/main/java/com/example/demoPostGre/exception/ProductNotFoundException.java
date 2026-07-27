package com.example.demoPostGre.exception;


public class ProductNotFoundException extends RuntimeException{

    public  ProductNotFoundException(String message) {
    super(message);
    }
}

