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

import org.springframework.web.util.UriComponentsBuilder;
import org.starchartlabs.alloy.core.MoreObjects;
import org.starchartlabs.alloy.core.Preconditions;

/**
 * Represents query parameters provided on a web resource that supports bounding of a variable number of available
 * representations
 *
 * <p>
 * Bounds results as "pages" - pre-defined sub-lists of ordered results. Clients control these pages by setting the
 * number of results to include per page, the index of the page to retrieve, and the order in which results are provided
 *
 * <p>
 * Supports:
 *
 * <ul>
 * <li>perPage - The number of results to include in a single result. Minimum 1</li>
 * <li>pageNumber - The index of the page to read. 0-indexed</li>
 * <li>sort - Specification of the sort parameter(s) to use. Of form "(field) [asc/desc]". May be a CSV of multiple
 * field and direction parameters</li>
 * </ul>
 *
 * @author romeara
 * @since 0.1.0
 */
public class PageRequest {

    private final Integer pageNumber;

    private final Integer perPage;

    private final String sort;

    /**
     * @param pageNumber
     *            The number of results to include in a single result. Minimum 1
     * @param perPage
     *            The index of the page to read. 0-indexed
     * @param sort
     *            Specification of the sort parameter(s) to use. Of form "(field) [asc/desc]". May be a CSV of multiple
     *            field and direction parameters
     * @since 0.1.0
     */
    public PageRequest(Integer pageNumber, Integer perPage, String sort) {
        this.pageNumber = Objects.requireNonNull(pageNumber);
        this.perPage = Objects.requireNonNull(perPage);
        this.sort = Objects.requireNonNull(sort);

        InvalidPagingArgumentException.checkArgument(this.pageNumber >= 0, "Cannot read a negative page");
        InvalidPagingArgumentException.checkArgument(this.perPage >= 1, "Cannot read 0 or fewer values");
        InvalidPagingArgumentException.checkArgument(!this.sort.trim().isEmpty(), "Cannot specify an empty sort");
    }

    /**
     * @return The number of results to include in a single result. Minimum 1
     * @since 0.1.0
     */
    public Integer getPageNumber() {
        return pageNumber;
    }

    /**
     * @return The index of the page to read. 0-indexed
     * @since 0.1.0
     */
    public Integer getPerPage() {
        return perPage;
    }

    /**
     * @return Specification of the sort parameter(s) to use. Of form "(field) [asc/desc]". May be a CSV of multiple
     *         field and direction parameters
     * @since 0.1.0
     */
    public String getSort() {
        return sort;
    }

    /**
     * Generates a page request using the same sorting and per-page parameters as this request for a specified page
     * number
     *
     * @param pageNumber
     *            The index of the page to generate a request representation for (0 - indexed)
     * @return A page request using the same sorting and per-page parameters as this request, for the specified page
     *         number
     * @since 0.1.0
     */
    public PageRequest getForPageNumber(int pageNumber) {
        Preconditions.checkArgument(pageNumber >= 0, "Cannot request a negative page");

        return new PageRequest(pageNumber, getPerPage(), getSort());
    }

    /**
     * Adds the page request parameters to a string URI representation as query parameters
     *
     * @param baseUrl
     *            Base URI to construct a web address from
     * @return The URI builder initialized with provided URI string and paging parameters
     * @since 0.1.0
     */
    public UriComponentsBuilder applyUrlQuery(String baseUrl) {
        Objects.requireNonNull(baseUrl);

        return applyUrlQuery(UriComponentsBuilder.fromUriString(baseUrl));
    }

    /**
     * Adds the page request parameters to a URI builder as query parameters
     *
     * @param builder
     *            URI builder being used to construct a web address
     * @return The provided URI builder with paging parameters
     * @since 0.1.0
     */
    public UriComponentsBuilder applyUrlQuery(UriComponentsBuilder builder) {
        Objects.requireNonNull(builder);

        return builder
                .queryParam("page", getPageNumber())
                .queryParam("per_page", getPerPage())
                .queryParam("sort", getSort());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPageNumber(),
                getPerPage(),
                getSort());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean result = false;

        if (obj instanceof PageRequest) {
            PageRequest compare = (PageRequest) obj;

            result = Objects.equals(compare.getPageNumber(), getPageNumber())
                    && Objects.equals(compare.getPerPage(), getPerPage())
                    && Objects.equals(compare.getSort(), getSort());
        }

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass()).omitNullValues()
                .add("pageNumber", getPageNumber())
                .add("perPage", getPerPage())
                .add("sort", getSort())
                .toString();
    }

}
