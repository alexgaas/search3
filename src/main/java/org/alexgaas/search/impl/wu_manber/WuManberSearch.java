package org.alexgaas.search.impl.wu_manber;

import com.google.common.base.Stopwatch;
import org.alexgaas.search.AbstractSearch;
import org.alexgaas.search.SearchProvider;
import org.alexgaas.search.domain.SearchInput;
import org.alexgaas.search.domain.SearchResult;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class WuManberSearch extends AbstractSearch {
    public WuManberSearch() {
        super("WuManber");
    }

    @Override
    public SearchResult find(SearchInput input) {
        Stopwatch timer = Stopwatch.createStarted();

        List<SearchResult.SearchResultEntry> list = new ArrayList<>();

        WuManber search = new WuManber(input.needle);
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
