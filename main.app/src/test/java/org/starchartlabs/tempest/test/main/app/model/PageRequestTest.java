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

import org.starchartlabs.tempest.main.app.model.InvalidPagingArgumentException;
import org.starchartlabs.tempest.main.app.model.PageRequest;
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
