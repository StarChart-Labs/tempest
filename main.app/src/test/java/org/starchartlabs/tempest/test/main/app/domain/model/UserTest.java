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

import org.starchartlabs.tempest.main.app.domain.model.User;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserTest {

    private static final UUID ID = UUID.randomUUID();

    @Test(expectedExceptions = NullPointerException.class)
    public void constructNullId() throws Exception {
        new User(null, "googleId");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructNullGoogleId() throws Exception {
        new User(ID, null);
    }

    @Test
    public void getTest() throws Exception {
        User result = new User(ID, "googleId");

        Assert.assertEquals(result.getId(), ID);
        Assert.assertEquals(result.getGoogleId(), "googleId");
    }

    @Test
    public void hashCodeEqualWhenDataEqual() throws Exception {
        User result1 = new User(ID, "googleId");
        User result2 = new User(ID, "googleId");

        Assert.assertEquals(result1.hashCode(), result2.hashCode());
    }

    @Test
    public void equalsNull() throws Exception {
        User result = new User(ID, "googleId");

        Assert.assertFalse(result.equals(null));
    }

    @Test
    public void equalsDifferentClass() throws Exception {
        User result = new User(ID, "googleId");

        Assert.assertFalse(result.equals("string"));
    }

    @Test
    public void equalsSelf() throws Exception {
        User result = new User(ID, "googleId");

        Assert.assertTrue(result.equals(result));
    }

    @Test
    public void equalsDifferentData() throws Exception {
        User result1 = new User(ID, "googleId1");
        User result2 = new User(ID, "googleId2");

        Assert.assertFalse(result1.equals(result2));
    }

    @Test
    public void equalsSameData() throws Exception {
        User result1 = new User(ID, "googleId");
        User result2 = new User(ID, "googleId");

        Assert.assertTrue(result1.equals(result2));
    }

    @Test
    public void toStringTest() throws Exception {
        User obj = new User(ID, "googleId");

        String result = obj.toString();

        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("id=" + ID.toString()));
        Assert.assertTrue(result.contains("googleId=googleId"));
    }

}
