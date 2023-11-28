package org.alexgaas.search.impl.aho_carasic;

import com.google.common.base.Stopwatch;
import org.alexgaas.search.AbstractSearch;
import org.alexgaas.search.domain.SearchInput;
import org.alexgaas.search.domain.SearchResult;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AhoCorasickSearch extends AbstractSearch {

    public AhoCorasickSearch(){
        super("AhoCarasic");
    }

    // For testing purpose of AhoCarasic
    @Override
    public Pair<Integer, Integer> findFirst(String needle, String haystack) {
        Stopwatch timer = Stopwatch.createStarted();

        AhoCorasick search = new AhoCorasick(new String[]{ needle });
        var result = search.searchFirst(haystack);
        var resultAsPair = new Pair<>(result - needle.length() + 1, result + 1);

        System.out.println("Method took: " + timer.stop());

        return resultAsPair;
    }

    // For testing purpose of AhoCarasic
    @Override
    public SearchResult.SearchResultEntry findFirst(String[] needle, String haystack) {
        Stopwatch timer = Stopwatch.createStarted();

        AhoCorasick search = new AhoCorasick(needle);
        var result = search.searchAllPatternsFirst(haystack);

        var startIndex = result.getValue1() - result.getValue0().length() + 1;
        var endIndex = result.getValue1() + 1;
        var searchResult = new SearchResult.SearchResultEntry(result.getValue0(), startIndex, endIndex);

        System.out.println("Method took: " + timer.stop());

        return searchResult;
    }

    // For prod usage and evaluation
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

        return new SearchResult(list, timer.stop());
    }
}

