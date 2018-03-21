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
package org.starchartlabs.tempest.core.rest.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation which indicates that a method parameter should be bound to a multiple web request parameters specifying
 * paging behavior on a response with variable length
 *
 * <p>
 * Used to specify a paging parameter representation, and defaults for values not explictly specified in a request
 *
 * <p>
 * Clients should configure Spring MVC with {@link RequestPagingArgumentResolver} to add support for use of this
 * annotation
 *
 * @author romeara
 * @since 0.1.0
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestPaging {

    /**
     * @return Query parameter to read the page index to read from on requests. Defaults to "page"
     * @since 0.1.0
     */
    String pageName() default "page";

    /**
     * @return Query parameter to read the maximum number of elements to include in a single response from on requests.
     *         Defaults to "per_page"
     * @since 0.1.0
     */
    String perPageName() default "per_page";

    /**
     * @return Query parameter to read sort specifications from on requests. Defaults to "sort"
     * @since 0.1.0
     */
    String sortName() default "sort";

    /**
     * @return The default page index to read if no explicit value is provided with a request (0-indexed). Defaults to
     *         "0"
     * @since 0.1.0
     */
    String defaultPage() default "0";

    /**
     * @return The default maximum number of elements to include in a single response if no explicit value is provided
     *         with a request. Defaults to "10"
     * @since 0.1.0
     */
    String defaultPerPage() default "10";

    /**
     * @return The default representation of a sort specification to assign to requests without an explicit sort
     *         defined. Required, as a reasonable default will vary based on sort specifications and the representations
     *         being sorted
     * @since 0.1.0
     */
    String defaultSort();

}
