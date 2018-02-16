/*
 * Copyright (C) 2017 The StarChart-Labs@github.com Authors
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
package org.starchartlabs.tempest.main.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.starchartlabs.tempest.main.app.server.config.MainAppServerConfiguration;
import org.starchartlabs.tempest.main.app.server.config.WebSecurityConfiguration;

@SpringBootApplication
@Import({ WebSecurityConfiguration.class,
    MainAppServerConfiguration.class })
public class Tempest {

    public static void main(String[] args) {
        SpringApplication.run(Tempest.class, args);
    }

}
