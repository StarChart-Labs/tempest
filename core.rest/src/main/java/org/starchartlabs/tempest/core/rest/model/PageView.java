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

import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

import org.starchartlabs.alloy.core.MoreObjects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a bounded sub-set of available results, including information about the location of the bounded results
 * within the available unbounded set
 *
 * <p>
 * Serializes to JSON for external representation in the form:
 *
 * <pre>
 * {
 *    "items": [ ... ],
 *    "_position": {
 *        "index": "0",
 *        "lastIndex": "0",
 *        "firstPage": "http://...",
 *        "previousPage": "http://...",
 *        "nextPage": "http://...",
 *        "lastPage": "http://...",
 *     },
 *     "_meta": {
 *         "href": "http://...",
 *         "allow": [ ... ],
 *         "links": [
 *             { "rel": "...", "href": "http://..." },
 *             ...
 *         ]
 *     }
 * }
 * </pre>
 *
 * @author romeara
 *
 * @param <T>
 *            Type for the representation of a single available result element
 */
public class PageView<T> {

    @JsonProperty("items")
    private final List<T> items;

    @JsonProperty("_position")
    private final PositionView position;

    @JsonProperty("_meta")
    private final MetaDataView metaData;

    /**
     * @param items
     *            Bounded, ordered list of representations
     * @param position
     *            Description of the contained representation's location within the available unbounded result set
     * @param metaData
     *            Web meta-data describing the location of this resource, the operations allowed on that location, and
     *            related resources
     * @since 0.1.0
     */
    public PageView(List<T> items, PositionView position, MetaDataView metaData) {
        this.items = Objects.requireNonNull(items);
        this.position = Objects.requireNonNull(position);
        this.metaData = Objects.requireNonNull(metaData);
    }

    /**
     * @return Bounded, ordered list of representations
     * @since 0.1.0
     */
    public List<T> getItems() {
        return items;
    }

    /**
     * @return Description of the contained representation's location within the available unbounded result set
     * @since 0.1.0
     */
    public PositionView getPosition() {
        return position;
    }

    /**
     * @return Web meta-data describing the location of this resource, the operations allowed on that location, and
     *         related resources
     * @since 0.1.0
     */
    public MetaDataView getMetaData() {
        return metaData;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItems(),
                getPosition(),
                getMetaData());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean result = false;

        if (obj instanceof PageView) {
            PageView<?> compare = (PageView<?>) obj;

            result = Objects.equals(compare.getItems(), getItems())
                    && Objects.equals(compare.getPosition(), getPosition())
                    && Objects.equals(compare.getMetaData(), getMetaData());
        }

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass()).omitNullValues()
                .add("items", getItems())
                .add("position", getPosition())
                .add("metaData", getMetaData())
                .toString();
    }

}
