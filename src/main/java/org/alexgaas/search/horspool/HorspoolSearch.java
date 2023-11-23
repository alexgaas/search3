package org.alexgaas.search.horspool;

import com.google.common.base.Stopwatch;
import org.alexgaas.search.Search;
import org.javatuples.Pair;

public class HorspoolSearch implements Search {
    @Override
    public Integer findFirstStartIndex(String needle, String haystack) {
        Stopwatch timer = Stopwatch.createStarted();

        Horspool search = new Horspool(needle);
        var point = search.searchFirst(haystack);

        System.out.println("Method took: " + timer.stop());

        return point;
    }

    @Override
    public Pair<Integer, Integer> findFirst(String needle, String haystack) {
        Stopwatch timer = Stopwatch.createStarted();

        Horspool search = new Horspool(needle);
        var point = search.searchFirst(haystack);

        System.out.println("Method took: " + timer.stop());

        return new Pair<>(point, point + needle.length());
    }

    @Override
    public Integer findFirstStartIndex(String[] needle, String haystack) {
        return null;
    }

    @Override
    public Pair<Integer, Integer> findFirst(String[] needle, String haystack) {
        return null;
    }
}
