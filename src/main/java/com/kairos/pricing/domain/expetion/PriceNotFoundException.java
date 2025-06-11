package com.kairos.pricing.domain.expetion;

public class PriceNotFoundException extends RuntimeException{
    public PriceNotFoundException(String message){ super(message); }
}
