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
package org.starchartlabs.tempest.core.rest.model;

import java.util.Objects;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

/**
 * Represents a link to a resource associated with another representation
 *
 * <p>
 * Intended for serialization to JSON in the form:
 *
 * <pre>
 * {
 *      "rel": "thing",
 *      "href": "http://..."
 * }
 * </pre>
 *
 * @author romeara
 * @since 0.1.0
 */
public class LinkView {

    @JsonProperty("rel")
    private final String rel;

    @JsonProperty("href")
    private final String href;

    /**
     * @param rel
     *            Key indicating what resource the associated {@code href} leads to
     * @param href
     *            URL to the resource indicated by the {@code rel} key
     * @since 0.1.0
     */
    public LinkView(String rel, String href) {
        this.rel = Objects.requireNonNull(rel);
        this.href = Objects.requireNonNull(href);
    }

    /**
     * @return Key indicating what resource the associated {@code href} leads to
     * @since 0.1.0
     */
    public String getRel() {
        return rel;
    }

    /**
     * @return URL to the resource indicated by the {@code rel} key
     * @since 0.1.0
     */
    public String getHref() {
        return href;
    }

    /**
     * @return A representation of the relational link appropriate for use within a standard "Link" header value
     * @see <a href="https://tools.ietf.org/html/rfc5988#section-5">RFC5988 Link header specification</a>
     * @since 0.1.0
     */
    @JsonIgnore
    public String toLinkHeader() {
        return "<" + getHref() + ">; rel=\"" + getRel() + "\"";
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRel(),
                getHref());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean result = false;

        if (obj instanceof LinkView) {
            LinkView compare = (LinkView) obj;

            result = Objects.equals(compare.getRel(), getRel())
                    && Objects.equals(compare.getHref(), getHref());
        }

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass()).omitNullValues()
                .add("rel", getRel())
                .add("href", getHref())
                .toString();
    }

}
