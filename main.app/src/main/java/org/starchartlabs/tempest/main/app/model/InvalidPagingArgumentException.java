/*
 * Copyright (c) Mar 8, 2018 StarChart Labs Authors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    romeara - initial API and implementation and/or initial documentation
 */
package org.starchartlabs.tempest.main.app.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Represents an out-of-bounds value for a paging parameter from a web request
 *
 * @author romeara
 * @since 0.1.0
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid value for paging operations provided")
public class InvalidPagingArgumentException extends RuntimeException {

    private static final long serialVersionUID = 6342347949663666619L;

    /**
     * @param message
     *            Description of the specific issue with the paging parameters
     * @since 0.1.0
     */
    public InvalidPagingArgumentException(String message) {
        super(message);
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method
     *
     * @param expression
     *            a boolean expression
     * @param message
     *            The message to use for the exception, should the expression be false
     * @throws InvalidPagingArgumentException
     *             If {@code expression} is false
     * @since 0.1.0
     */
    public static void checkArgument(boolean expression, String message) {
        if (!expression) {
            throw new InvalidPagingArgumentException(message);
        }
    }

}
