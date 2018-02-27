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

import org.starchartlabs.tempest.main.app.domain.model.Idea;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IdeaTest {

    private static final UUID PROJECT_ID = UUID.randomUUID();

    private static final UUID ID = UUID.randomUUID();

    @Test(expectedExceptions = NullPointerException.class)
    public void constructNullProjectId() throws Exception {
        new Idea(null, ID, "name", "description");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructNullId() throws Exception {
        new Idea(PROJECT_ID, null, "name", "description");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructNullName() throws Exception {
        new Idea(PROJECT_ID, ID, null, "description");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructNullDescription() throws Exception {
        new Idea(PROJECT_ID, ID, "name", null);
    }

    @Test
    public void getTest() throws Exception {
        Idea result = new Idea(PROJECT_ID, ID, "name", "description");

        Assert.assertEquals(result.getProjectId(), PROJECT_ID);
        Assert.assertEquals(result.getId(), ID);
        Assert.assertEquals(result.getName(), "name");
        Assert.assertEquals(result.getDescription(), "description");
    }

    @Test
    public void hashCodeEqualWhenDataEqual() throws Exception {
        Idea result1 = new Idea(PROJECT_ID, ID, "name", "description");
        Idea result2 = new Idea(PROJECT_ID, ID, "name", "description");

        Assert.assertEquals(result1.hashCode(), result2.hashCode());
    }

    @Test
    public void equalsNull() throws Exception {
        Idea result = new Idea(PROJECT_ID, ID, "name", "description");

        Assert.assertFalse(result.equals(null));
    }

    @Test
    public void equalsDifferentClass() throws Exception {
        Idea result = new Idea(PROJECT_ID, ID, "name", "description");

        Assert.assertFalse(result.equals("string"));
    }

    @Test
    public void equalsSelf() throws Exception {
        Idea result = new Idea(PROJECT_ID, ID, "name", "description");

        Assert.assertTrue(result.equals(result));
    }

    @Test
    public void equalsDifferentData() throws Exception {
        Idea result1 = new Idea(PROJECT_ID, ID, "name1", "description");
        Idea result2 = new Idea(PROJECT_ID, ID, "name2", "description");

        Assert.assertFalse(result1.equals(result2));
    }

    @Test
    public void equalsSameData() throws Exception {
        Idea result1 = new Idea(PROJECT_ID, ID, "name", "description");
        Idea result2 = new Idea(PROJECT_ID, ID, "name", "description");

        Assert.assertTrue(result1.equals(result2));
    }

    @Test
    public void toStringTest() throws Exception {
        Idea obj = new Idea(PROJECT_ID, ID, "name", "description");

        String result = obj.toString();

        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("projectId=" + PROJECT_ID.toString()));
        Assert.assertTrue(result.contains("id=" + ID.toString()));
        Assert.assertTrue(result.contains("name=name"));
        Assert.assertTrue(result.contains("description=description"));
    }

}
