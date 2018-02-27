/*
 * Copyright (c) Feb 26, 2018 StarChart Labs Authors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Ryan - initial API and implementation and/or initial documentation
 */
package org.starchartlabs.tempest.test.main.app.domain.model;

import java.util.UUID;

import org.starchartlabs.tempest.main.app.domain.model.Organization;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OrganizationTest {

    private static final UUID ID = UUID.randomUUID();

    @Test(expectedExceptions = NullPointerException.class)
    public void constructNullId() throws Exception {
        new Organization(null, "name");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructNullName() throws Exception {
        new Organization(ID, null);
    }

    @Test
    public void getTest() throws Exception {
        Organization result = new Organization(ID, "name");

        Assert.assertEquals(result.getId(), ID);
        Assert.assertEquals(result.getName(), "name");
    }

    @Test
    public void hashCodeEqualWhenDataEqual() throws Exception {
        Organization result1 = new Organization(ID, "name");
        Organization result2 = new Organization(ID, "name");

        Assert.assertEquals(result1.hashCode(), result2.hashCode());
    }

    @Test
    public void equalsNull() throws Exception {
        Organization result = new Organization(ID, "name");

        Assert.assertFalse(result.equals(null));
    }

    @Test
    public void equalsDifferentClass() throws Exception {
        Organization result = new Organization(ID, "name");

        Assert.assertFalse(result.equals("string"));
    }

    @Test
    public void equalsSelf() throws Exception {
        Organization result = new Organization(ID, "name");

        Assert.assertTrue(result.equals(result));
    }

    @Test
    public void equalsDifferentData() throws Exception {
        Organization result1 = new Organization(ID, "name1");
        Organization result2 = new Organization(ID, "name2");

        Assert.assertFalse(result1.equals(result2));
    }

    @Test
    public void equalsSameData() throws Exception {
        Organization result1 = new Organization(ID, "name");
        Organization result2 = new Organization(ID, "name");

        Assert.assertTrue(result1.equals(result2));
    }

    @Test
    public void toStringTest() throws Exception {
        Organization obj = new Organization(ID, "name");

        String result = obj.toString();

        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("id=" + ID.toString()));
        Assert.assertTrue(result.contains("name=name"));
    }

}
