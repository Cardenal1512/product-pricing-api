package com.kairos.pricing.domain.expetion;

import org.apache.coyote.BadRequestException;

public class RequestParamException extends BadRequestException {
    public RequestParamException(String message){super(message);}
}
