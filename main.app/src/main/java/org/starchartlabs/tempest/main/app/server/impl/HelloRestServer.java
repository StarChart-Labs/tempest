/*
 * Copyright 2018 StarChart Labs Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
