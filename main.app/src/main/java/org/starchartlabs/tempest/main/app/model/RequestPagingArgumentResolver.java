/*
 * Copyright (c) Mar 7, 2018 StarChart Labs Authors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    romeara - initial API and implementation and/or initial documentation
 */
package org.starchartlabs.tempest.main.app.model;

import java.util.Optional;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Implementation of {@link HandlerMethodArgumentResolver} which handles spring MVC {@link PageRequest} input parameters
 * annotated with {@link RequestPaging}
 *
 * <p>
 * Utilizes annotation values to determine the query parameters to read paging values from, and to determine default
 * values if none are specified on the request
 *
 * <p>
 * Intended to be configured on a Spring Web MVC Configurer to use on MVC endpoints. In Spring 5.0 and later, done via:
 *
 * <pre>
 * public class Example implements WebMvcConfigurer {
 *
 *     &#64;Override
 *     public void addArgumentResolvers(List&lt;HandlerMethodArgumentResolver&gt; argumentResolvers) {
 *         argumentResolvers.add(new RequestPagingArgumentResolver());
 *     }
 *
 * }
 * </pre>
 *
 * <p>
 * In Spring 4.x and earlier, done via:
 *
 * <pre>
 * public class Example extends WebMvcConfigurerAdapter {
 *
 *     &#64;Override
 *     public void addArgumentResolvers(List&lt;HandlerMethodArgumentResolver&gt; argumentResolvers) {
 *         argumentResolvers.add(new RequestPagingArgumentResolver());
 *     }
 *
 * }
 * </pre>
 *
 * @author romeara
 * @since 0.1.0
 */
public class RequestPagingArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(PageRequest.class)
                && parameter.getParameterAnnotation(RequestPaging.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String pageParameter = parameter.getParameterAnnotation(RequestPaging.class).pageName();
        String perPageParameter = parameter.getParameterAnnotation(RequestPaging.class).perPageName();
        String sortParameter = parameter.getParameterAnnotation(RequestPaging.class).sortName();

        String defaultPage = parameter.getParameterAnnotation(RequestPaging.class).defaultPage();
        String defaultPerPage = parameter.getParameterAnnotation(RequestPaging.class).defaultPerPage();
        String defaultSort = parameter.getParameterAnnotation(RequestPaging.class).defaultSort();

        Integer page = getInteger(pageParameter, defaultPage, webRequest, parameter);
        Integer perPage = getInteger(perPageParameter, defaultPerPage, webRequest, parameter);
        String sort = Optional.ofNullable(webRequest.getParameter(sortParameter))
                .orElse(defaultSort);

        return new PageRequest(page, perPage, sort);
    }

    /**
     * Reads and converts a query parameter expected to be an integer from the current request
     *
     * <p>
     * Throws an exception if the effective value is not a number
     *
     * @param parameterName
     *            The name of the query parameter to read the request
     * @param defaultValue
     *            The default value to use if the current request does not have a value specified
     * @param webRequest
     *            The current web request
     * @param parameter
     *            The method parameter the read value will contribute to
     * @return Integer representation of the value the application should consume
     */
    private Integer getInteger(String parameterName, String defaultValue, NativeWebRequest webRequest,
            MethodParameter parameter) {
        String value = Optional.ofNullable(webRequest.getParameter(parameterName))
                .orElse(defaultValue);

        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            throw new MethodArgumentTypeMismatchException(value, Integer.class, parameterName, parameter, e);
        }
    }

}
