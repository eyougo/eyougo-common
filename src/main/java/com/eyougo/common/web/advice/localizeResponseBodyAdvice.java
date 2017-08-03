package com.eyougo.common.web.advice;

import com.eyougo.common.result.BooleanResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Locale;

/**
 * Created by mei on 03/08/2017.
 */
@ControllerAdvice
public class localizeResponseBodyAdvice implements ResponseBodyAdvice {

    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return BooleanResult.class.isAssignableFrom(returnType.getDeclaringClass());
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        BooleanResult booleanResult = (BooleanResult) body;
        Locale locale = Locale.getDefault();
        if (request instanceof ServletServerHttpRequest) {
            locale = RequestContextUtils.getLocale(((ServletServerHttpRequest) request).getServletRequest());
        }
        return booleanResult.localizeMessage(messageSource, locale);
    }
}
