/*
 * Copyright (c) Mar 22, 2018 StarChart Labs Authors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    romeara - initial API and implementation and/or initial documentation
 */
package org.starchartlabs.tempest.test.core.rest.model;

import org.starchartlabs.tempest.core.rest.model.LinkView;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class LinkViewTest {

    @Test(expectedExceptions = NullPointerException.class)
    public void constructNullRel() throws Exception {
        new LinkView(null, "href");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructNullHref() throws Exception {
        new LinkView("rel", null);
    }

    @Test
    public void getTest() throws Exception {
        LinkView result = new LinkView("rel", "href");

        Assert.assertEquals(result.getRel(), "rel");
        Assert.assertEquals(result.getHref(), "href");
    }

    @Test
    public void hashCodeEqualWhenDataEqual() throws Exception {
        LinkView result1 = new LinkView("rel", "href");
        LinkView result2 = new LinkView("rel", "href");

        Assert.assertEquals(result1.hashCode(), result2.hashCode());
    }

    @Test
    public void equalsNull() throws Exception {
        LinkView result = new LinkView("rel", "href");

        Assert.assertFalse(result.equals(null));
    }

    @Test
    public void equalsDifferentClass() throws Exception {
        LinkView result = new LinkView("rel", "href");

        Assert.assertFalse(result.equals("string"));
    }

    @Test
    public void equalsSelf() throws Exception {
        LinkView result = new LinkView("rel", "href");

        Assert.assertTrue(result.equals(result));
    }

    @Test
    public void equalsDifferentData() throws Exception {
        LinkView result1 = new LinkView("rel1", "href");
        LinkView result2 = new LinkView("rel2", "href");

        Assert.assertFalse(result1.equals(result2));
    }

    @Test
    public void equalsSameData() throws Exception {
        LinkView result1 = new LinkView("rel", "href");
        LinkView result2 = new LinkView("rel", "href");

        Assert.assertTrue(result1.equals(result2));
    }

    @Test
    public void toStringTest() throws Exception {
        LinkView obj = new LinkView("rel", "href");

        String result = obj.toString();

        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("rel=rel"));
        Assert.assertTrue(result.contains("href=href"));
    }

    @Test
    public void toLinkHeader() throws Exception {
        String result = new LinkView("rel-data", "href-data").toLinkHeader();

        Assert.assertEquals(result, "<href-data>; rel=\"rel-data\"");
    }

    @Test
    public void toJson() throws Exception {
        ObjectWriter writer = new ObjectMapper().writer();
        String json = writer.writeValueAsString(new LinkView("rel", "href"));

        // Use different framework for verification to avoid error masking
        JsonObject result = new JsonParser().parse(json).getAsJsonObject();

        Assert.assertEquals(result.get("rel").getAsString(), "rel");
        Assert.assertEquals(result.get("href").getAsString(), "href");
    }

}
