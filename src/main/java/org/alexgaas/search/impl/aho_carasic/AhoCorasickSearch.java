package org.alexgaas.search.impl.aho_carasic;

import com.google.common.base.Stopwatch;
import org.alexgaas.search.AbstractSearch;
import org.alexgaas.search.domain.SearchInput;
import org.alexgaas.search.domain.SearchResult;
import org.javatuples.Pair;

import java.util.*;

public class AhoCorasickSearch extends AbstractSearch {

    public AhoCorasickSearch(){
        super("AhoCarasic");
    }

    @Override
    public SearchResult find(SearchInput input) {
        Stopwatch timer = Stopwatch.createStarted();

        List<SearchResult.SearchResultEntry> list = new ArrayList<>();

        AhoCorasick search = new AhoCorasick(input.needle);
        var result = search.searchAllPatterns(input.haystack);
        for(Pair<String, Integer> p: result){
            var foundPattern = Arrays.stream(input.needle).parallel().filter(
                    n -> Objects.equals(n, p.getValue0())).findFirst();
            if (foundPattern.isPresent()){
                var startIndex = p.getValue1() - p.getValue0().length() + 1;
                var endIndex = p.getValue1() + 1;
                list.add(new SearchResult.SearchResultEntry(
                        p.getValue0(), startIndex, endIndex));
            }
        }
        return new SearchResult(sortResultsByPatternOrder(input, list), timer.stop());
    }
}

