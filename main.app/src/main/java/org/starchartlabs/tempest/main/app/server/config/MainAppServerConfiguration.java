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
package org.starchartlabs.tempest.main.app.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.starchartlabs.tempest.main.app.server.impl.HelloRestServer;

@Configuration
public class MainAppServerConfiguration {

    @Bean
    public HelloRestServer helloRestServer() {
        return new HelloRestServer();
    }

}
