package org.alexgaas.search;

import org.alexgaas.search.domain.SearchInput;
import org.alexgaas.search.domain.SearchResult;
import org.javatuples.Pair;

public interface SearchProvider {
    SearchResult find(SearchInput input);
}
