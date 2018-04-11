/*
 * Copyright (c) Apr 10, 2018 StarChart Labs Authors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    romeara - initial API and implementation and/or initial documentation
 */
package org.starchartlabs.tempest.test.core.rest.model;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.starchartlabs.tempest.core.rest.model.MetaDataView;
import org.starchartlabs.tempest.core.rest.model.PageRequest;
import org.starchartlabs.tempest.core.rest.model.PageView;
import org.starchartlabs.tempest.core.rest.model.PositionView;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PageViewTest {

    private static final List<String> ITEMS = Collections.singletonList("string");

    private static final PositionView POSITION = new PositionView(new PageRequest(0, 10, "sort asc"), 10, 10,
            "http://localhost");

    private static final MetaDataView META_DATA = MetaDataView.builder()
            .setHref("http://localhost")
            .addAllow(HttpMethod.GET)
            .addLink("rel", "http://link")
            .build();

    @Test(expectedExceptions = NullPointerException.class)
    public void constructNullItems() throws Exception {
        new PageView<>(null, POSITION, META_DATA);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructNullPosition() throws Exception {
        new PageView<>(ITEMS, null, META_DATA);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructNullMetaData() throws Exception {
        new PageView<>(ITEMS, POSITION, null);
    }

    @Test
    public void getTest() throws Exception {
        PageView<String> result = new PageView<>(ITEMS, POSITION, META_DATA);

        Assert.assertEquals(result.getItems(), ITEMS);
        Assert.assertEquals(result.getPosition(), POSITION);
        Assert.assertEquals(result.getMetaData(), META_DATA);
    }

    @Test
    public void hashCodeEqualWhenDataEqual() throws Exception {
        PageView<String> result1 = new PageView<>(ITEMS, POSITION, META_DATA);
        PageView<String> result2 = new PageView<>(ITEMS, POSITION, META_DATA);

        Assert.assertEquals(result1.hashCode(), result2.hashCode());
    }

    @Test
    public void equalsNull() throws Exception {
        PageView<String> result = new PageView<>(ITEMS, POSITION, META_DATA);

        Assert.assertFalse(result.equals(null));
    }

    @Test
    public void equalsDifferentClass() throws Exception {
        PageView<String> result = new PageView<>(ITEMS, POSITION, META_DATA);

        Assert.assertFalse(result.equals("string"));
    }

    @Test
    public void equalsSelf() throws Exception {
        PageView<String> result = new PageView<>(ITEMS, POSITION, META_DATA);

        Assert.assertTrue(result.equals(result));
    }

    @Test
    public void equalsDifferentData() throws Exception {
        PageView<String> result1 = new PageView<>(Collections.singletonList("string1"), POSITION, META_DATA);
        PageView<String> result2 = new PageView<>(Collections.singletonList("string2"), POSITION, META_DATA);

        Assert.assertFalse(result1.equals(result2));
    }

    @Test
    public void equalsSameData() throws Exception {
        PageView<String> result1 = new PageView<>(ITEMS, POSITION, META_DATA);
        PageView<String> result2 = new PageView<>(ITEMS, POSITION, META_DATA);

        Assert.assertTrue(result1.equals(result2));
    }

    @Test
    public void toStringTest() throws Exception {
        PageView<String> obj = new PageView<>(ITEMS, POSITION, META_DATA);

        String result = obj.toString();

        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("items=" + ITEMS.toString()));
        Assert.assertTrue(result.contains("position=" + POSITION.toString()));
        Assert.assertTrue(result.contains("metaData=" + META_DATA.toString()));
    }

}
