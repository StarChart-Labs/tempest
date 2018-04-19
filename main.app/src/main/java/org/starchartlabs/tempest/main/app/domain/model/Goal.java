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
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import org.starchartlabs.alloy.core.MoreObjects;

/**
 * Represents an actionable step towards a larger aim. Often associated/generated from a less specific {@link Idea}
 *
 * @author romeara
 * @since 0.1.0
 */
public class Goal {

    private final UUID projectId;

    private final UUID id;

    private final String name;

    private final Optional<UUID> associatedIdeaId;

    /**
     * @param projectId
     *            Unique internal application identifier for the project which owns the goal
     * @param id
     *            Unique internal application identifier for the goal
     * @param name
     *            Human-readable name of the goal
     * @param associatedIdeaId
     *            Unique internal identifier for the idea the goal is associated with. If not associated with an idea,
     *            may be null
     * @since 0.1.0
     */
    public Goal(UUID projectId, UUID id, String name, @Nullable UUID associatedIdeaId) {
        this.projectId = Objects.requireNonNull(projectId);
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.associatedIdeaId = Optional.ofNullable(associatedIdeaId);
    }

    /**
     * @return Unique internal application identifier for the project which owns the goal
     * @since 0.1.0
     */
    public UUID getProjectId() {
        return projectId;
    }

    /**
     * @return Unique internal application identifier for the goal
     * @since 0.1.0
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return Human-readable name of the goal
     * @since 0.1.0
     */
    public String getName() {
        return name;
    }

    /**
     * @return Unique internal identifier for the idea the goal is associated with. If not associated with an idea, may
     *         be empty
     * @since 0.1.0
     */
    public Optional<UUID> getAssociatedIdeaId() {
        return associatedIdeaId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProjectId(),
                getId(),
                getName(),
                getAssociatedIdeaId());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean result = false;

        if (obj instanceof Goal) {
            Goal compare = (Goal) obj;

            result = Objects.equals(compare.getProjectId(), getProjectId())
                    && Objects.equals(compare.getId(), getId())
                    && Objects.equals(compare.getName(), getName())
                    && Objects.equals(compare.getAssociatedIdeaId(), getAssociatedIdeaId());
        }

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass()).omitNullValues()
                .add("projectId", getProjectId())
                .add("id", getId())
                .add("name", getName())
                .add("associatedIdeaId", getAssociatedIdeaId().orElse(null))
                .toString();
    }

}
