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

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRestServer {

    @RequestMapping(method = RequestMethod.GET, path = "/secured/hello")
    public ResponseEntity<String> getGreeting() {
        return new ResponseEntity<>("Hello, fellow human", HttpStatus.OK);
    }

}
