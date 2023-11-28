package org.alexgaas.search;

import org.alexgaas.search.domain.SearchInput;
import org.alexgaas.search.domain.SearchResult;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public abstract class AbstractSearch implements SearchProvider {
    public final String searchId;

    protected AbstractSearch(String searchId) {
        this.searchId = searchId;
    }

    protected Function<SearchInput, SearchResult> findRef = this::find;

    public Pair<Integer, Integer> findFirst(String needle, String haystack) {
        var result = findFirst(new String[]{needle}, haystack);
        return new Pair<>(result.startIndex, result.endIndex);
    }

    public SearchResult.SearchResultEntry findFirst(String[] needle, String haystack) {
        var result = find(new SearchInput(needle, haystack));
        return !result.searchResultEntryList.isEmpty() ? result.searchResultEntryList.get(0) : null;
    }

    public List<SearchResult.SearchResultEntry> sortResultsByPatternOrder(SearchInput input, List<SearchResult.SearchResultEntry> list){
        List<SearchResult.SearchResultEntry> sortedList = new ArrayList<>();
        int idx = 0;
        for(String pattern: input.needle){
            if (list.stream().anyMatch(f -> f.pattern.equals(pattern))){
                //noinspection OptionalGetWithoutIsPresent
                sortedList.add(list.stream().filter(f -> Objects.equals(f.pattern, pattern)).findAny().get());
            }
            idx++;
        }
        return sortedList;
    }
}
