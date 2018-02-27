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

import org.starchartlabs.tempest.main.app.domain.model.Goal;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GoalTest {

    private static final UUID PROJECT_ID = UUID.randomUUID();

    private static final UUID ID = UUID.randomUUID();

    private static final UUID ASSOCIATED_IDEA_ID = UUID.randomUUID();

    @Test(expectedExceptions = NullPointerException.class)
    public void constructNullProjectId() throws Exception {
        new Goal(null, ID, "name", ASSOCIATED_IDEA_ID);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructNullId() throws Exception {
        new Goal(PROJECT_ID, null, "name", ASSOCIATED_IDEA_ID);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructNullName() throws Exception {
        new Goal(PROJECT_ID, ID, null, ASSOCIATED_IDEA_ID);
    }

    @Test
    public void constructNullAssociatedIdeaId() throws Exception {
        Goal result = new Goal(PROJECT_ID, ID, "name", null);

        Assert.assertNotNull(result.getAssociatedIdeaId());
        Assert.assertFalse(result.getAssociatedIdeaId().isPresent());
    }

    @Test
    public void getTest() throws Exception {
        Goal result = new Goal(PROJECT_ID, ID, "name", ASSOCIATED_IDEA_ID);

        Assert.assertEquals(result.getProjectId(), PROJECT_ID);
        Assert.assertEquals(result.getId(), ID);
        Assert.assertEquals(result.getName(), "name");
        Assert.assertEquals(result.getAssociatedIdeaId().get(), ASSOCIATED_IDEA_ID);
    }

    @Test
    public void hashCodeEqualWhenDataEqual() throws Exception {
        Goal result1 = new Goal(PROJECT_ID, ID, "name", ASSOCIATED_IDEA_ID);
        Goal result2 = new Goal(PROJECT_ID, ID, "name", ASSOCIATED_IDEA_ID);

        Assert.assertEquals(result1.hashCode(), result2.hashCode());
    }

    @Test
    public void equalsNull() throws Exception {
        Goal result = new Goal(PROJECT_ID, ID, "name", ASSOCIATED_IDEA_ID);

        Assert.assertFalse(result.equals(null));
    }

    @Test
    public void equalsDifferentClass() throws Exception {
        Goal result = new Goal(PROJECT_ID, ID, "name", ASSOCIATED_IDEA_ID);

        Assert.assertFalse(result.equals("string"));
    }

    @Test
    public void equalsSelf() throws Exception {
        Goal result = new Goal(PROJECT_ID, ID, "name", ASSOCIATED_IDEA_ID);

        Assert.assertTrue(result.equals(result));
    }

    @Test
    public void equalsDifferentData() throws Exception {
        Goal result1 = new Goal(PROJECT_ID, ID, "name1", ASSOCIATED_IDEA_ID);
        Goal result2 = new Goal(PROJECT_ID, ID, "name2", ASSOCIATED_IDEA_ID);

        Assert.assertFalse(result1.equals(result2));
    }

    @Test
    public void equalsSameData() throws Exception {
        Goal result1 = new Goal(PROJECT_ID, ID, "name", ASSOCIATED_IDEA_ID);
        Goal result2 = new Goal(PROJECT_ID, ID, "name", ASSOCIATED_IDEA_ID);

        Assert.assertTrue(result1.equals(result2));
    }

    @Test
    public void toStringTest() throws Exception {
        Goal obj = new Goal(PROJECT_ID, ID, "name", ASSOCIATED_IDEA_ID);

        String result = obj.toString();

        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("projectId=" + PROJECT_ID.toString()));
        Assert.assertTrue(result.contains("id=" + ID.toString()));
        Assert.assertTrue(result.contains("name=name"));
        Assert.assertTrue(result.contains("associatedIdeaId=" + ASSOCIATED_IDEA_ID.toString()));
    }

}
