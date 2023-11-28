package org.alexgaas.search;

import com.google.common.annotations.VisibleForTesting;
import org.alexgaas.search.domain.SearchInput;
import org.alexgaas.search.domain.SearchResult;
import org.javatuples.Pair;

import java.util.function.Function;

public abstract class AbstractSearch implements SearchProvider {
    public final String searchId;

    protected AbstractSearch(String searchId) {
        this.searchId = searchId;
    }

    protected Function<SearchInput, SearchResult> findRef = this::find;

    @VisibleForTesting
    protected abstract Pair<Integer, Integer> findFirst(String needle, String haystack);

    @VisibleForTesting
    protected abstract SearchResult.SearchResultEntry findFirst(String[] needle, String haystack);
}
