/*
 * Copyright (c) Feb 8, 2018 StarChart Labs Authors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Ryan - initial API and implementation and/or initial documentation
 */
package org.starchartlabs.tempest.main.app.server.impl;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRestServer {

    @RequestMapping(method = RequestMethod.GET, path = "/secured/hello")
    public ResponseEntity<String> getGreeting() {
        Optional<Authentication> authentication = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(a -> a instanceof OAuth2Authentication)
                .map(a -> (OAuth2Authentication) a)
                .map(OAuth2Authentication::getUserAuthentication);

        System.out.println(authentication);

        String username = authentication
                .map(Authentication::getName)
                .orElse("Unidentifier user");

        return new ResponseEntity<>("I'm afraid I can't do that, " + username, HttpStatus.OK);
    }

}
