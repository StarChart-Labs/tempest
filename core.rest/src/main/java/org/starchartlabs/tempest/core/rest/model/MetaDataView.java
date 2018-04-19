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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.springframework.http.HttpMethod;
import org.starchartlabs.alloy.core.MoreObjects;
import org.starchartlabs.alloy.core.Preconditions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Represents meta-data associated with a representation which presents information on the location, allowed operations,
 * and associated resources of the containing representation
 *
 * @author romeara
 * @since 0.1.0
 */
@JsonInclude(Include.NON_NULL)
public class MetaDataView {

    @Nullable
    private final String href;

    private final Set<HttpMethod> allow;

    private final List<LinkView> links;

    /**
     * @param builder
     *            Builder which contains values to build into an immutable meta-data representation
     */
    private MetaDataView(Builder builder) {
        this.href = builder.href;
        this.allow = new HashSet<>(builder.allow);
        this.links = new ArrayList<>(builder.links);
    }

    /**
     * @return The path providing the contained resource
     * @since 0.1.0
     */
    public String getHref() {
        return href;
    }

    /**
     * @return The HTTP methods allowed on the path providing the contained resource
     * @since 0.1.0
     */
    public Set<HttpMethod> getAllow() {
        return allow;
    }

    /**
     * @return Links to resources associated with the containing representation
     * @since 0.1.0
     */
    public List<LinkView> getLinks() {
        return links;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHref(),
                getAllow(),
                getLinks());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean result = false;

        if (obj instanceof MetaDataView) {
            MetaDataView compare = (MetaDataView) obj;

            result = Objects.equals(compare.getHref(), getHref())
                    && Objects.equals(compare.getAllow(), getAllow())
                    && Objects.equals(compare.getLinks(), getLinks());
        }

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass()).omitNullValues()
                .add("href", getHref())
                .add("allow", getAllow())
                .add("links", getLinks())
                .toString();
    }

    /**
     * @return A new incremental builder which allows construction of an immutable meta-data representation
     * @since 0.1.0
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Incremental builder which allows construction of an immutable meta-data representation
     *
     * @author romeara
     * @since 0.1.0
     */
    public static final class Builder {

        private String href;

        private Set<HttpMethod> allow;

        private List<LinkView> links;

        private Builder() {
            allow = new HashSet<>();
            links = new ArrayList<>();
        }

        /**
         * @param href
         *            Location where the representation containing the meta-data may be read from
         * @return This builder instance
         * @since 0.1.0
         */
        public Builder setHref(String href) {
            this.href = Objects.requireNonNull(href);

            return this;
        }

        /**
         * @param methods
         *            HTTP method(s) to add to the allowed methods on the path representing a resource
         * @return This builder instance
         * @since 0.1.0
         */
        public Builder addAllow(HttpMethod... methods) {
            Objects.requireNonNull(methods);

            Stream.of(methods)
            .forEach(method -> Preconditions.checkArgument(method != null));

            Stream.of(methods)
            .forEach(allow::add);

            return this;
        }

        /**
         * @param rel
         *            Key indicating what resource the associated {@code href} leads to
         * @param href
         *            URL to the resource indicated by the {@code rel} key
         * @return This builder instance
         * @since 0.1.0
         */
        public Builder addLink(String rel, String href) {
            Objects.requireNonNull(rel);
            Objects.requireNonNull(href);

            links.add(new LinkView(rel, href));

            return this;
        }

        /**
         * @param link
         *            An associated link to add to the meta-data
         * @return This builder instance
         * @since 0.1.0
         */
        public Builder addLink(LinkView link) {
            Objects.requireNonNull(link);

            links.add(link);

            return this;
        }

        /**
         * @return A representation of meta-data created from the values provided this builder
         * @since 0.1.0
         */
        public MetaDataView build() {
            return new MetaDataView(this);
        }

    }
}
