/*
 * Copyright 2018 StarChart Labs Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.starchartlabs.tempest.main.app.domain.model;

import java.util.Objects;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.MoreObjects;

/**
 * Represents a vague plan or intention which requires further refinement before action can be taken to acheive it
 *
 * @author romeara
 * @since 0.1.0
 */
public class Idea {

    private final UUID projectId;

    private final UUID id;

    private final String name;

    private final String description;

    /**
     * @param projectId
     *            Unique internal application identifier for the project which owns the idea
     * @param id
     *            Unique internal application identifier for the idea
     * @param name
     *            Human-readable name of the idea
     * @param description
     *            Verbose explanation of what the idea entails
     * @since 0.1.0
     */
    public Idea(UUID projectId, UUID id, String name, String description) {
        this.projectId = Objects.requireNonNull(projectId);
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.description = Objects.requireNonNull(description);
    }

    /**
     * @return Unique internal application identifier for the project which owns the idea
     * @since 0.1.0
     */
    public UUID getProjectId() {
        return projectId;
    }

    /**
     * @return Unique internal application identifier for the idea
     * @since 0.1.0
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return Human-readable name of the idea
     * @since 0.1.0
     */
    public String getName() {
        return name;
    }

    /**
     * @return Verbose explanation of what the idea entails
     * @since 0.1.0
     */
    public String getDescription() {
        return description;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProjectId(),
                getId(),
                getName(),
                getDescription());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean result = false;

        if (obj instanceof Idea) {
            Idea compare = (Idea) obj;

            result = Objects.equals(compare.getProjectId(), getProjectId())
                    && Objects.equals(compare.getId(), getId())
                    && Objects.equals(compare.getName(), getName())
                    && Objects.equals(compare.getDescription(), getDescription());
        }

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass()).omitNullValues()
                .add("projectId", getProjectId())
                .add("id", getId())
                .add("name", getName())
                .add("description", getDescription())
                .toString();
    }

}
