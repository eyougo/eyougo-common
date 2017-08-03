package com.eyougo.common.web.resolver;

import com.eyougo.common.result.BooleanResult;
import com.eyougo.common.result.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * User: mei
 * Date: 10/13/16
 * Time: 10:12
 */
public class CompositeViewOutputStackMappingExceptionResolver extends SimpleMappingExceptionResolver {
    /**
     * The default name of the exception stack trace string attribute: "exceptionStack".
     */
    public static final String DEFAULT_EXCEPTION_STACK_ATTRIBUTE = "exceptionStack";

    private String exceptionStackAttribute = DEFAULT_EXCEPTION_STACK_ATTRIBUTE;

    public void setExceptionStackAttribute(String exceptionStackAttribute) {
        this.exceptionStackAttribute = exceptionStackAttribute;
    }

    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView modelAndView;
        //先看之前的handler
        if (handler != null && handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            modelAndView = getModelAndViewByHandlerMethod(method, request, response, ex);
        } else {
            // 如果没有之前的handler，看请求的Accept情况
            HttpInputMessage inputMessage = new ServletServerHttpRequest(request);
            List<MediaType> acceptedMediaTypes = inputMessage.getHeaders().getAccept();
            if (acceptedMediaTypes.isEmpty()) {
                acceptedMediaTypes = Collections.singletonList(MediaType.ALL);
            }
            MediaType.sortByQualityValue(acceptedMediaTypes);
            modelAndView = getModelAndViewByAcceptMediaTypes(acceptedMediaTypes, request, response, ex);
        }
        if (modelAndView == null) {
            modelAndView = super.doResolveException(request, response, handler, ex);
            modelAndView.addObject(this.exceptionStackAttribute, getErrorStackTrace(ex));
            return modelAndView;
        } else {
            return modelAndView;
        }
    }

    protected ModelAndView getModelAndViewByHandlerMethod(HandlerMethod method, HttpServletRequest request,
                                                          HttpServletResponse response, Exception ex) {
        if (method.getMethodAnnotation(ResponseBody.class) != null && BooleanResult.class.isAssignableFrom(method.getMethod().getReturnType())) {
            // ResponseBody返回JsonResult
            return getErrorJsonResultView(ex, request, response);
        }
        return null;
    }

    protected ModelAndView getModelAndViewByAcceptMediaTypes(List<MediaType> acceptedMediaTypes, HttpServletRequest request,
                                                             HttpServletResponse response, Exception ex) {
        if (acceptedMediaTypes.get(0).equals(MediaType.APPLICATION_JSON)) {//如果前端优先要求json
            return getErrorJsonResultView(ex, request, response);
        }
        return null;
    }

    protected ModelAndView getErrorJsonResultView(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setExtractValueFromSingleKeyModel(true);
        Locale locale = RequestContextUtils.getLocale(request);
        DataResult<String> errorResult = DataResult.failed(getErrorStackTrace(ex), ex.getMessage(), new Object[]{})
                .localizeMessage(messageSource, locale);

        String viewName = determineViewName(ex, request);
        if (viewName != null) {
            // Apply HTTP status code for error views, if specified.
            // Only apply it if we're processing a top-level request.
            Integer statusCode = determineStatusCode(request, viewName);
            if (statusCode != null) {
                applyStatusCodeIfPossible(request, response, statusCode);
            }
        }
        return new ModelAndView(view, "error", errorResult);
    }



    protected String getErrorStackTrace(Exception ex) {
        StringBuilder sb = new StringBuilder();
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        ex.printStackTrace(printWriter);
        sb.append(stringWriter.getBuffer());
        printWriter.close();
        return sb.toString();
    }
}
