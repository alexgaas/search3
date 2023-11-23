package org.alexgaas.search.aho_carasic;

import com.google.common.base.Stopwatch;
import org.alexgaas.search.Search;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AhoCarasicSearch implements Search {
    @Override
    public Integer findFirstStartIndex(String needle, String haystack) {
        Stopwatch timer = Stopwatch.createStarted();

        AhoCorasick search = new AhoCorasick(new String[]{ needle });
        var result = search.searchFirst(haystack);

        System.out.println("Method took: " + timer.stop());

        return result;
    }

    @Override
    public Pair<Integer, Integer> findFirst(String needle, String haystack) {
        Stopwatch timer = Stopwatch.createStarted();

        AhoCorasick search = new AhoCorasick(new String[]{ needle });
        var result = search.searchFirst(haystack);
        var resultAsPair = new Pair<>(result - needle.length() + 1, result + 1);

        System.out.println("Method took: " + timer.stop());

        return resultAsPair;
    }

    @Override
    public Pair<String,Integer> findFirstStartIndex(String[] needle, String haystack) {
        Stopwatch timer = Stopwatch.createStarted();

        AhoCorasick search = new AhoCorasick(needle);
        var result = search.searchAllPatternsFirst(haystack);

        System.out.println("Method took: " + timer.stop());

        return result;
    }

    @Override
    public Pair<String, Pair<Integer, Integer>> findFirst(String[] needle, String haystack) {
        Stopwatch timer = Stopwatch.createStarted();

        AhoCorasick search = new AhoCorasick(needle);
        var result = search.searchAllPatternsFirst(haystack);

        var startEndPatternIndexAsPair = new Pair<>(
                result.getValue1() - result.getValue0().length() + 1, result.getValue1() + 1);
        var resultAsPair = new Pair<>(result.getValue0(), startEndPatternIndexAsPair);

        System.out.println("Method took: " + timer.stop());

        return resultAsPair;
    }

    @Override
    public List<Pair<String, Pair<Integer, Integer>>> find(String[] needle, String haystack) {
        Stopwatch timer = Stopwatch.createStarted();

        List<Pair<String, Pair<Integer, Integer>>> returnList = new ArrayList<>();

        AhoCorasick search = new AhoCorasick(needle);
        var result = search.searchAllPatterns(haystack);
        for(Pair<String, Integer> p: result){
            var foundPattern = Arrays.stream(needle).parallel().filter(n -> n == p.getValue0()).findFirst();
            if (foundPattern.isPresent()){


            }
        }

        //var resultAsPair = new Pair<>(result - needle.length() + 1, result + 1);

        System.out.println("Method took: " + timer.stop());

        return returnList;

    }
}

