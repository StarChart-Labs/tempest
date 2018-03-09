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
package org.starchartlabs.tempest.test.main.app.model;

import java.util.Objects;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.starchartlabs.tempest.main.app.model.PageRequest;
import org.starchartlabs.tempest.main.app.model.RequestPaging;
import org.starchartlabs.tempest.main.app.model.RequestPagingArgumentResolver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RequestPagingArgumentResolverTest {

    private static final String DEFAULTS = "/defaults";

    private static final String NO_DEFAULTS = "/no-defaults";

    private static final String DEFAULT_SORT = "test asc";

    /** Logger reference to output information to the application log files */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Mock
    private ReceivedParameterCapture receivedParameterCapture;

    private MockMvc mockMvc;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(new TestServer(receivedParameterCapture))
                .setCustomArgumentResolvers(new RequestPagingArgumentResolver())
                .build();
    }

    @AfterMethod(alwaysRun = false)
    public void verifyNoMoreInteractions(ITestResult result) {
        logger.trace("Verifying interactions for {}", result.getMethod().getMethodName());

        Mockito.verifyNoMoreInteractions(receivedParameterCapture);
    }

    @Test
    public void defaultsNoQueryParameters() throws Exception {
        PageRequest expected = new PageRequest(0, 10, DEFAULT_SORT);
        String url = DEFAULTS;

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url));

        result.andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(receivedParameterCapture).capturePageRequest(expected);
    }

    @Test
    public void defaultsAllQueryParameters() throws Exception {
        PageRequest expected = new PageRequest(10, 100, "override desc");
        String url = DEFAULTS;

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .param("page", expected.getPageNumber().toString())
                .param("per_page", expected.getPerPage().toString())
                .param("sort", expected.getSort()));

        result.andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(receivedParameterCapture).capturePageRequest(expected);
    }

    @Test
    public void defaultsMalformedPerPage() throws Exception {
        String url = DEFAULTS;

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .param("per_page", "nan"));

        result.andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void defaultsInvalidPerPage() throws Exception {
        String url = DEFAULTS;

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .param("per_page", "0"));

        result.andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void defaultsMalformedPage() throws Exception {
        String url = DEFAULTS;

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .param("page", "nan"));

        result.andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void defaultsInvalidPage() throws Exception {
        String url = DEFAULTS;

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .param("page", "-1"));

        result.andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void defaultsInvalidSort() throws Exception {
        String url = DEFAULTS;

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .param("sort", " "));

        result.andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void noDefaultsNoQueryParameters() throws Exception {
        PageRequest expected = new PageRequest(1, 20, "sort desc");
        String url = NO_DEFAULTS;

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url));

        result.andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(receivedParameterCapture).capturePageRequest(expected);
    }

    @Test
    public void noDefaultsAllQueryParameters() throws Exception {
        PageRequest expected = new PageRequest(30, 300, "something desc");
        String url = NO_DEFAULTS;

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .param("p", expected.getPageNumber().toString())
                .param("pp", expected.getPerPage().toString())
                .param("s", expected.getSort()));

        result.andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(receivedParameterCapture).capturePageRequest(expected);
    }

    @Test
    public void noDefaultsMalformedPerPage() throws Exception {
        String url = NO_DEFAULTS;

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .param("pp", "nan"));

        result.andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void noDefaultsInvalidPerPage() throws Exception {
        String url = NO_DEFAULTS;

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .param("pp", "0"));

        result.andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void noDefaultsMalformedPage() throws Exception {
        String url = NO_DEFAULTS;

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .param("p", "nan"));

        result.andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void noDefaultsInvalidPage() throws Exception {
        String url = NO_DEFAULTS;

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .param("p", "-1"));

        result.andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void noDefaultsInvalidSort() throws Exception {
        String url = NO_DEFAULTS;

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .param("s", " "));

        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @RestController
    public static class TestServer {

        private final ReceivedParameterCapture receivedParameterCapture;

        public TestServer(ReceivedParameterCapture receivedParameterCapture) {
            this.receivedParameterCapture = Objects.requireNonNull(receivedParameterCapture);
        }

        @RequestMapping(method = { RequestMethod.GET }, path = DEFAULTS)
        public ResponseEntity<Void> getPagedResourceDefaults(
                @RequestPaging(defaultSort = DEFAULT_SORT) PageRequest pageRequest) {
            receivedParameterCapture.capturePageRequest(pageRequest);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        @RequestMapping(method = { RequestMethod.GET }, path = NO_DEFAULTS)
        public ResponseEntity<Void> getPagedResourceNoDefaults(
                @RequestPaging(pageName = "p", perPageName = "pp", sortName = "s",
                defaultPage = "1", defaultPerPage = "20", defaultSort = "sort desc") PageRequest pageRequest) {
            receivedParameterCapture.capturePageRequest(pageRequest);

            return new ResponseEntity<>(HttpStatus.OK);
        }

    }

    public static interface ReceivedParameterCapture {

        void capturePageRequest(PageRequest pageRequest);

    }
}
