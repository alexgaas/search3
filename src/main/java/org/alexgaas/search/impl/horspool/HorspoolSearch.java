package org.alexgaas.search.impl.horspool;

import com.google.common.base.Stopwatch;
import org.alexgaas.search.AbstractSearch;
import org.alexgaas.search.domain.SearchInput;
import org.alexgaas.search.domain.SearchResult;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HorspoolSearch extends AbstractSearch {
    public HorspoolSearch() {
        super("Harspool");
    }

    @Override
    public SearchResult find(SearchInput input) {
        Stopwatch timer = Stopwatch.createStarted();

        List<SearchResult.SearchResultEntry> list = new ArrayList<>();

        Horspool search = new Horspool(input.needle);
        var result = search.searchAllPatterns(input.haystack);
        for(Pair<String, Integer> p: result){
            var foundPattern = Arrays.stream(input.needle).parallel().filter(
                    n -> Objects.equals(n, p.getValue0())).findFirst();
            if (foundPattern.isPresent()){
                var startIndex = p.getValue1();
                var endIndex = p.getValue1() + p.getValue0().length();
                list.add(new SearchResult.SearchResultEntry(
                        p.getValue0(), startIndex, endIndex));
            }
        }

        return new SearchResult(sortResultsByPatternOrder(input, list), timer.stop());
    }
}
