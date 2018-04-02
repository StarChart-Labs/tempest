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
package org.starchartlabs.tempest.test.core.rest.model;

import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.starchartlabs.tempest.core.rest.model.InvalidPagingArgumentException;
import org.starchartlabs.tempest.core.rest.model.PageRequest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PageRequestTest {

    @Test(expectedExceptions = NullPointerException.class)
    public void constructNullPageNumber() throws Exception {
        new PageRequest(null, 10, "sort asc");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructNullPage() throws Exception {
        new PageRequest(0, null, "sort asc");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructNullSort() throws Exception {
        new PageRequest(0, 10, null);
    }

    @Test(expectedExceptions = InvalidPagingArgumentException.class)
    public void constructInvalidPageNumber() throws Exception {
        new PageRequest(-1, 10, "sort asc");
    }

    @Test(expectedExceptions = InvalidPagingArgumentException.class)
    public void constructInvalidPerPage() throws Exception {
        new PageRequest(0, 0, "sort asc");
    }

    @Test(expectedExceptions = InvalidPagingArgumentException.class)
    public void constructEmptySort() throws Exception {
        new PageRequest(0, 10, " ");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getForPageNumberNegativePage() throws Exception {
        PageRequest request = new PageRequest(0, 10, "sort asc");

        request.getForPageNumber(-1);
    }

    @Test
    public void getForPageNumber() throws Exception {
        PageRequest request = new PageRequest(0, 10, "sort asc");

        PageRequest result = request.getForPageNumber(100);

        Assert.assertEquals(result.getPageNumber().intValue(), 100);
        Assert.assertEquals(result.getPerPage().intValue(), 10);
        Assert.assertEquals(result.getSort(), "sort asc");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void applyUrlQueryNullStringUrl() throws Exception {
        PageRequest request = new PageRequest(0, 10, "sort asc");

        request.applyUrlQuery((String) null);
    }

    @Test
    public void applyUrlQueryStringUrl() throws Exception {
        PageRequest request = new PageRequest(0, 10, "sort asc");

        UriComponents result = request.applyUrlQuery("http://localhost").build();

        MultiValueMap<String, String> parameters = result.getQueryParams();

        Assert.assertEquals(parameters.size(), 3);
        Assert.assertEquals(parameters.get("page").size(), 1);
        Assert.assertEquals(parameters.get("per_page").size(), 1);
        Assert.assertEquals(parameters.get("sort").size(), 1);

        Assert.assertEquals(parameters.getFirst("page"), "0");
        Assert.assertEquals(parameters.getFirst("per_page"), "10");
        Assert.assertEquals(parameters.getFirst("sort"), "sort asc");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void applyUrlQueryNullBuilder() throws Exception {
        PageRequest request = new PageRequest(0, 10, "sort asc");

        request.applyUrlQuery((UriComponentsBuilder) null);
    }

    @Test
    public void applyUrlQueryBuilder() throws Exception {
        PageRequest request = new PageRequest(0, 10, "sort asc");

        UriComponents result = request.applyUrlQuery(UriComponentsBuilder.fromUriString("http://localhost")).build();

        MultiValueMap<String, String> parameters = result.getQueryParams();

        Assert.assertEquals(parameters.size(), 3);
        Assert.assertEquals(parameters.get("page").size(), 1);
        Assert.assertEquals(parameters.get("per_page").size(), 1);
        Assert.assertEquals(parameters.get("sort").size(), 1);

        Assert.assertEquals(parameters.getFirst("page"), "0");
        Assert.assertEquals(parameters.getFirst("per_page"), "10");
        Assert.assertEquals(parameters.getFirst("sort"), "sort asc");
    }

    @Test
    public void getTest() throws Exception {
        PageRequest result = new PageRequest(0, 10, "sort asc");

        Assert.assertEquals(result.getPageNumber().intValue(), 0);
        Assert.assertEquals(result.getPerPage().intValue(), 10);
        Assert.assertEquals(result.getSort(), "sort asc");
    }

    @Test
    public void hashCodeEqualWhenDataEqual() throws Exception {
        PageRequest result1 = new PageRequest(0, 10, "sort asc");
        PageRequest result2 = new PageRequest(0, 10, "sort asc");

        Assert.assertEquals(result1.hashCode(), result2.hashCode());
    }

    @Test
    public void equalsNull() throws Exception {
        PageRequest result = new PageRequest(0, 10, "sort asc");

        Assert.assertFalse(result.equals(null));
    }

    @Test
    public void equalsDifferentClass() throws Exception {
        PageRequest result = new PageRequest(0, 10, "sort asc");

        Assert.assertFalse(result.equals("string"));
    }

    @Test
    public void equalsSelf() throws Exception {
        PageRequest result = new PageRequest(0, 10, "sort asc");

        Assert.assertTrue(result.equals(result));
    }

    @Test
    public void equalsDifferentData() throws Exception {
        PageRequest result1 = new PageRequest(0, 10, "sort asc");
        PageRequest result2 = new PageRequest(1, 10, "sort asc");

        Assert.assertFalse(result1.equals(result2));
    }

    @Test
    public void equalsSameData() throws Exception {
        PageRequest result1 = new PageRequest(0, 10, "sort asc");
        PageRequest result2 = new PageRequest(0, 10, "sort asc");

        Assert.assertTrue(result1.equals(result2));
    }

    @Test
    public void toStringTest() throws Exception {
        PageRequest obj = new PageRequest(0, 10, "sort asc");

        String result = obj.toString();

        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("pageNumber=0"));
        Assert.assertTrue(result.contains("perPage=10"));
        Assert.assertTrue(result.contains("sort=sort asc"));
    }

}
