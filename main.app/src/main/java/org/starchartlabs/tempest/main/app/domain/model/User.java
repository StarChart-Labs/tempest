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
 * Represents an individual who interacts with the application
 *
 * @author romeara
 * @since 0.1.0
 */
public class User {

    private final UUID id;

    private final String googleId;

    /**
     * @param id
     *            Unique internal application identifier for the user
     * @param googleId
     *            ID of the user within google - used to link user account to application account upon login
     * @since 0.1.0
     */
    public User(UUID id, String googleId) {
        this.id = Objects.requireNonNull(id);
        this.googleId = Objects.requireNonNull(googleId);
    }

    /**
     * @return Unique internal application identifier for the user
     * @since 0.1.0
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return ID of the user within google - used to link user account to application account upon login
     * @since 0.1.0
     */
    public String getGoogleId() {
        return googleId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getGoogleId());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean result = false;

        if (obj instanceof User) {
            User compare = (User) obj;

            result = Objects.equals(compare.getId(), getId())
                    && Objects.equals(compare.getGoogleId(), getGoogleId());
        }

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass()).omitNullValues()
                .add("id", getId())
                .add("googleId", getGoogleId())
                .toString();
    }
}
