/*
 * Copyright (C) 2017 The StarChart-Labs@github.com Authors
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
package org.starchartlabs.tempest.main.app;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.starchartlabs.tempest.core.rest.model.RequestPagingArgumentResolver;
import org.starchartlabs.tempest.main.app.server.config.MainAppServerConfiguration;
import org.starchartlabs.tempest.main.app.server.config.WebSecurityConfiguration;

@SpringBootApplication
@Import({ WebSecurityConfiguration.class,
    MainAppServerConfiguration.class })
public class Tempest implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(Tempest.class, args);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new RequestPagingArgumentResolver());
    }

}
