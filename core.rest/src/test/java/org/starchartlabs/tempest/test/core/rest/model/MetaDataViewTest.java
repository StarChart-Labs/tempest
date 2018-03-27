/*
 * Copyright (c) Mar 26, 2018 StarChart Labs Authors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    romeara - initial API and implementation and/or initial documentation
 */
package org.starchartlabs.tempest.test.core.rest.model;

import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpMethod;
import org.starchartlabs.tempest.core.rest.model.LinkView;
import org.starchartlabs.tempest.core.rest.model.MetaDataView;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MetaDataViewTest {

    @Test(expectedExceptions = NullPointerException.class)
    public void setHrefNull() throws Exception {
        MetaDataView.builder()
        .setHref(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void addAllowNullArray() throws Exception {
        MetaDataView.builder()
        .addAllow((HttpMethod[]) null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addAllowNullElement() throws Exception {
        MetaDataView.builder()
        .addAllow((HttpMethod) null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void addLinkNullRel() throws Exception {
        MetaDataView.builder()
        .addLink(null, "href");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void addLinkNullHref() throws Exception {
        MetaDataView.builder()
        .addLink("rel", null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void addLinkNullLinkView() throws Exception {
        MetaDataView.builder()
        .addLink(null);
    }

    @Test
    public void build() throws Exception {
        MetaDataView result = MetaDataView.builder()
                .setHref("http://localhost")
                .addAllow(HttpMethod.GET, HttpMethod.DELETE)
                .addLink("rel1", "http://link1")
                .addLink(new LinkView("rel2", "http://link2"))
                .build();

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getHref(), "http://localhost");
        Assert.assertEquals(result.getAllow().size(), 2);
        Assert.assertTrue(result.getAllow().contains(HttpMethod.GET));
        Assert.assertTrue(result.getAllow().contains(HttpMethod.DELETE));

        Optional<LinkView> link1 = result.getLinks().stream()
                .filter(link -> Objects.equals(link.getRel(), "rel1"))
                .findAny();

        Assert.assertTrue(link1.isPresent());
        Assert.assertEquals(link1.get().getHref(), "http://link1");

        Optional<LinkView> link2 = result.getLinks().stream()
                .filter(link -> Objects.equals(link.getRel(), "rel2"))
                .findAny();

        Assert.assertTrue(link2.isPresent());
        Assert.assertEquals(link2.get().getHref(), "http://link2");
    }

}
