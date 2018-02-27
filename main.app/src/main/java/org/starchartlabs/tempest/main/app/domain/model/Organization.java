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
 * Represents a group of users who manage and contribute to a common goal. Serves as a name-space for projects and other
 * application elements
 *
 * @author romeara
 * @since 0.1.0
 */
public class Organization {

    private final UUID id;

    private final String name;

    /**
     * @param id
     *            Unique internal application identifier for the organization
     * @param name
     *            Human-readable name of the organization
     * @since 0.1.0
     */
    public Organization(UUID id, String name) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
    }

    /**
     * @return Unique internal application identifier for the organization
     * @since 0.1.0
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return Human-readable name of the organization
     * @since 0.1.0
     */
    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getName());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean result = false;

        if (obj instanceof Organization) {
            Organization compare = (Organization) obj;

            result = Objects.equals(compare.getId(), getId())
                    && Objects.equals(compare.getName(), getName());
        }

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass()).omitNullValues()
                .add("id", getId())
                .add("name", getName())
                .toString();
    }

}
