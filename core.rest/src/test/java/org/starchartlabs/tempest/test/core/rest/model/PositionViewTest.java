/*
 * Copyright (c) Apr 2, 2018 StarChart Labs Authors.
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
import org.starchartlabs.tempest.core.rest.model.PageRequest;
import org.starchartlabs.tempest.core.rest.model.PositionView;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PositionViewTest {

    private static final PageRequest PAGE_REQUEST = new PageRequest(1, 10, "sort asc");

    @Test(expectedExceptions = NullPointerException.class)
    public void constructNullPageRequest() throws Exception {
        new PositionView(null, 1, 1, "http://localhost");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructNullBaseUrl() throws Exception {
        new PositionView(PAGE_REQUEST, 1, 1, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void constructNegativeElementsInPage() throws Exception {
        new PositionView(PAGE_REQUEST, -1, 1, "http://localhost");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void constructNegativeTotalElements() throws Exception {
        new PositionView(PAGE_REQUEST, 1, -1, "http://localhost");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void constructMoreElementsInPageThanTotalElements() throws Exception {
        new PositionView(PAGE_REQUEST, 2, 1, "http://localhost");
    }

    @Test
    public void singlePage() throws Exception {
        PageRequest pageRequest = new PageRequest(0, 10, "sort asc");

        PositionView result = new PositionView(pageRequest, 10, 10, "http://localhost");

        Assert.assertEquals(result.getIndex().intValue(), 0);
        Assert.assertEquals(result.getLastIndex().intValue(), 0);
        Assert.assertEquals(result.getTotalElements().intValue(), 10);

        Assert.assertNull(result.getFirstPage());
        Assert.assertNull(result.getPreviousPage());
        Assert.assertNull(result.getNextPage());
        Assert.assertNull(result.getLastPage());
    }

    @Test
    public void firstPage() throws Exception {
        PageRequest pageRequest = new PageRequest(0, 10, "sort asc");

        PositionView result = new PositionView(pageRequest, 10, 100, "http://localhost");

        Assert.assertEquals(result.getIndex().intValue(), 0);
        Assert.assertEquals(result.getLastIndex().intValue(), 9);
        Assert.assertEquals(result.getTotalElements().intValue(), 100);

        Assert.assertNull(result.getFirstPage());
        Assert.assertNull(result.getPreviousPage());
        assertUrl(result.getNextPage(), 1, 10, "sort asc");
        assertUrl(result.getLastPage(), 9, 10, "sort asc");
    }

    @Test
    public void lastPage() throws Exception {
        PageRequest pageRequest = new PageRequest(9, 10, "sort asc");

        PositionView result = new PositionView(pageRequest, 10, 100, "http://localhost");

        Assert.assertEquals(result.getIndex().intValue(), 9);
        Assert.assertEquals(result.getLastIndex().intValue(), 9);
        Assert.assertEquals(result.getTotalElements().intValue(), 100);

        assertUrl(result.getFirstPage(), 0, 10, "sort asc");
        assertUrl(result.getPreviousPage(), 8, 10, "sort asc");
        Assert.assertNull(result.getNextPage());
        Assert.assertNull(result.getLastPage());
    }

    @Test
    public void middlePage() throws Exception {
        PageRequest pageRequest = new PageRequest(5, 10, "sort asc");

        PositionView result = new PositionView(pageRequest, 10, 100, "http://localhost");

        Assert.assertEquals(result.getIndex().intValue(), 5);
        Assert.assertEquals(result.getLastIndex().intValue(), 9);
        Assert.assertEquals(result.getTotalElements().intValue(), 100);

        assertUrl(result.getFirstPage(), 0, 10, "sort asc");
        assertUrl(result.getPreviousPage(), 4, 10, "sort asc");
        assertUrl(result.getNextPage(), 6, 10, "sort asc");
        assertUrl(result.getLastPage(), 9, 10, "sort asc");
    }

    @Test
    public void hashCodeEqualWhenDataEqual() throws Exception {
        PositionView result1 = new PositionView(PAGE_REQUEST, 1, 1, "http://localhost");
        PositionView result2 = new PositionView(PAGE_REQUEST, 1, 1, "http://localhost");

        Assert.assertEquals(result1.hashCode(), result2.hashCode());
    }

    @Test
    public void equalsNull() throws Exception {
        PositionView result = new PositionView(PAGE_REQUEST, 1, 1, "http://localhost");

        Assert.assertFalse(result.equals(null));
    }

    @Test
    public void equalsDifferentClass() throws Exception {
        PositionView result = new PositionView(PAGE_REQUEST, 1, 1, "http://localhost");

        Assert.assertFalse(result.equals("string"));
    }

    @Test
    public void equalsSelf() throws Exception {
        PositionView result = new PositionView(PAGE_REQUEST, 1, 1, "http://localhost");

        Assert.assertTrue(result.equals(result));
    }

    @Test
    public void equalsDifferentData() throws Exception {
        PositionView result1 = new PositionView(PAGE_REQUEST, 1, 1, "http://localhost1");
        PositionView result2 = new PositionView(PAGE_REQUEST, 1, 1, "http://localhost2");

        Assert.assertFalse(result1.equals(result2));
    }

    @Test
    public void equalsSameData() throws Exception {
        PositionView result1 = new PositionView(PAGE_REQUEST, 1, 1, "http://localhost");
        PositionView result2 = new PositionView(PAGE_REQUEST, 1, 1, "http://localhost");

        Assert.assertTrue(result1.equals(result2));
    }

    @Test
    public void toStringTest() throws Exception {
        PositionView obj = new PositionView(PAGE_REQUEST, 10, 100, "http://localhost");

        String result = obj.toString();

        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("index=1"));
        Assert.assertTrue(result.contains("lastIndex=9"));
        Assert.assertTrue(result.contains("totalElements=100"));
        Assert.assertTrue(result.contains("firstPage=http://localhost?page=0&per_page=10&sort=sort asc"));
        Assert.assertTrue(result.contains("previousPage=http://localhost?page=0&per_page=10&sort=sort asc"));
        Assert.assertTrue(result.contains("nextPage=http://localhost?page=2&per_page=10&sort=sort asc"));
        Assert.assertTrue(result.contains("lastPage=http://localhost?page=9&per_page=10&sort=sort asc"));
    }

    private void assertUrl(String url, int page, int perPage, String sort) {
        UriComponents components = UriComponentsBuilder.fromUriString(url).build();

        MultiValueMap<String, String> parameters = components.getQueryParams();

        Assert.assertEquals(parameters.get("page").size(), 1);
        Assert.assertEquals(parameters.get("per_page").size(), 1);
        Assert.assertEquals(parameters.get("sort").size(), 1);

        Assert.assertEquals(parameters.getFirst("page"), String.valueOf(page));
        Assert.assertEquals(parameters.getFirst("per_page"), String.valueOf(perPage));
        Assert.assertEquals(parameters.getFirst("sort"), sort);
    }

}
