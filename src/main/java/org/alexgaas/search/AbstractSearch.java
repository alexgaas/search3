package org.alexgaas.search;

import com.google.common.base.Stopwatch;
import org.alexgaas.search.domain.SearchInput;
import org.alexgaas.search.domain.SearchResult;
import org.alexgaas.search.impl.Search;
import org.alexgaas.search.impl.wu_manber.WuManber;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public abstract class AbstractSearch implements SearchProvider {
    public final String searchId;

    protected AbstractSearch(String searchId) {
        this.searchId = searchId;
    }

    protected abstract Integer getStartIndex(Pair<String, Integer> pair);

    protected abstract Integer getEndIndex(Pair<String, Integer> pair);

    protected Function<SearchInput, SearchResult> findRef = this::find;

    public Pair<Integer, Integer> findFirst(String needle, String haystack) {
        var result = findFirst(new String[]{needle}, haystack);
        return new Pair<>(result.startIndex, result.endIndex);
    }

    public SearchResult.SearchResultEntry findFirst(String[] needle, String haystack) {
        var result = find(new SearchInput(needle, haystack));
        return !result.searchResultEntryList.isEmpty() ? result.searchResultEntryList.get(0) : null;
    }

    public SearchResult find(SearchInput input, Search search) {
        Stopwatch timer = Stopwatch.createStarted();

        List<SearchResult.SearchResultEntry> list = new ArrayList<>();

        var result = search.searchAllPatterns(input.haystack);
        for(Pair<String, Integer> p: result){
            var foundPattern = Arrays.stream(input.needle).parallel().filter(
                    n -> Objects.equals(n, p.getValue0())).findFirst();
            if (foundPattern.isPresent()){
                var startIndex = getStartIndex(p);
                var endIndex = getEndIndex(p);
                list.add(new SearchResult.SearchResultEntry(
                        p.getValue0(), startIndex, endIndex));
            }
        }

        return new SearchResult(sortResultsByPatternOrder(input, list), timer.stop());
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
