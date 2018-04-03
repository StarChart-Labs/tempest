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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

/**
 * Represents page navigation links and position data based on the current location in a paginated data series
 *
 * <p>
 * Provided as part of paginated responses for ease-of-use by clients presenting current and available data to end-users
 *
 * <p>
 * Intended for serialization to JSON in the form:
 *
 * <pre>
 * {
 *      "index": "0",
 *      "lastIndex": "0",
 *      "firstPage": "http://...",
 *      "previousPage": "http://...",
 *      "nextPage": "http://...",
 *      "lastPage": "http://...",
 * }
 * </pre>
 *
 * @author romeara
 * @since 0.1.0
 */
@JsonInclude(Include.NON_NULL)
public class PositionView {

    @JsonProperty(value = "index", required = true)
    private final Integer index;

    @JsonProperty(value = "lastIndex", required = true)
    private final Integer lastIndex;

    @JsonProperty(value = "totalElements", required = true)
    private final Integer totalElements;

    @JsonProperty(value = "firstPage", required = false)
    private final String firstPage;

    @JsonProperty(value = "previousPage", required = false)
    private final String previousPage;

    @JsonProperty(value = "nextPage", required = false)
    private final String nextPage;

    @JsonProperty(value = "lastPage", required = false)
    private final String lastPage;

    /**
     * Creates a representation of page navigation links and position data based on the current page state
     *
     * @param request
     *            The page request which details the page index and number of elements per page
     * @param elementsInPage
     *            The number of elements provided in the current page
     * @param totalElements
     *            The total number of elements available to be paged
     * @param baseUrl
     *            The URL to the resource being paged, without any of the paging parameters set
     * @since 0.1.0
     */
    public PositionView(PageRequest request, int elementsInPage, int totalElements, String baseUrl) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(baseUrl);

        Preconditions.checkArgument(elementsInPage >= 0,
                "There cannot be less that 0 elements in the page (Input: " + elementsInPage + ")");
        Preconditions.checkArgument(totalElements >= 0,
                "There cannot be less that 0 total elements (Input: " + totalElements + ")");
        Preconditions.checkArgument(elementsInPage <= totalElements,
                "There cannot be more elements in the page than total elements (Input: " + elementsInPage + ", "
                        + totalElements + ")");

        int elementsInPreviousPages = request.getPageNumber() * request.getPerPage();
        int maxPage = Math.max((totalElements / request.getPerPage()) - 1, 0);

        if (totalElements > 0 && (totalElements > request.getPerPage())
                && (totalElements % request.getPerPage()) != 0) {
            maxPage++;
        }

        // If not the first page, provide first and previous links
        if (request.getPageNumber() > 0) {
            firstPage = getPageUrl(request, 0, baseUrl);
            previousPage = getPageUrl(request, request.getPageNumber() - 1, baseUrl);
        } else {
            firstPage = null;
            previousPage = null;
        }

        // If not the last page, provide next and last links
        if (elementsInPreviousPages + elementsInPage < totalElements) {
            lastPage = getPageUrl(request, maxPage, baseUrl);
            nextPage = getPageUrl(request, request.getPageNumber() + 1, baseUrl);
        } else {
            lastPage = null;
            nextPage = null;
        }

        index = request.getPageNumber();
        lastIndex = maxPage;
        this.totalElements = totalElements;
    }

    /**
     * @return Index of the represented page. 0 indexed
     * @since 0.1.0
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * @return Index of the last page in the data series being paginated. 0 indexed
     * @since 0.1.0
     */
    public Integer getLastIndex() {
        return lastIndex;
    }

    /**
     * @return The total number of elements available to be paged
     * @since 0.1.0
     */
    public Integer getTotalElements() {
        return totalElements;
    }

    /**
     * @see Include#NON_NULL
     * @return Link to the first page in the data series being paginated. Null if the current position is the first page
     * @since 0.1.0
     */
    @Nullable
    public String getFirstPage() {
        return firstPage;
    }

    /**
     * @see Include#NON_NULL
     * @return Link to the previous page in the data series being paginated. Null if the current position is the first
     *         page
     * @since 0.1.0
     */
    @Nullable
    public String getPreviousPage() {
        return previousPage;
    }

    /**
     * @see Include#NON_NULL
     * @return Link to the next page in the data series being paginated. Null if the current position is the last page
     * @since 0.1.0
     */
    @Nullable
    public String getNextPage() {
        return nextPage;
    }

    /**
     * @see Include#NON_NULL
     * @return Link to the last page in the data series being paginated. Null if the current position is the last page
     * @since 0.1.0
     */
    @Nullable
    public String getLastPage() {
        return lastPage;
    }

    /**
     * Generates the URL to access an arbitrary page with a given number of maximum elements
     *
     * @param currentPage
     *            Page request detailing the current associated page's parameters
     * @param pageNumber
     *            The number of the page the URL will access (0-indexed)
     * @param baseUrl
     *            The URL to the resource being paged, without any of the paging parameters set
     * @return A URL which stably accesses the provided resource in a paged manner
     */
    private String getPageUrl(PageRequest currentPage, int pageNumber, String baseUrl) {
        Objects.requireNonNull(currentPage);
        Objects.requireNonNull(baseUrl);

        PageRequest targetPageRequst = currentPage.getForPageNumber(pageNumber);

        return targetPageRequst.applyUrlQuery(baseUrl)
                .build()
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIndex(),
                getLastIndex(),
                getTotalElements(),
                getFirstPage(),
                getPreviousPage(),
                getNextPage(),
                getLastPage());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean result = false;

        if (obj instanceof PositionView) {
            PositionView compare = (PositionView) obj;

            result = Objects.equals(compare.getIndex(), getIndex())
                    && Objects.equals(compare.getLastIndex(), getLastIndex())
                    && Objects.equals(compare.getTotalElements(), getTotalElements())
                    && Objects.equals(compare.getFirstPage(), getFirstPage())
                    && Objects.equals(compare.getPreviousPage(), getPreviousPage())
                    && Objects.equals(compare.getNextPage(), getNextPage())
                    && Objects.equals(compare.getLastPage(), getLastPage());
        }

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass()).omitNullValues()
                .add("index", getIndex())
                .add("lastIndex", getLastIndex())
                .add("totalElements", getTotalElements())
                .add("firstPage", getFirstPage())
                .add("previousPage", getPreviousPage())
                .add("nextPage", getNextPage())
                .add("lastPage", getLastPage())
                .toString();
    }

}
