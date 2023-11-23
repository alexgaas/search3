package org.alexgaas.search.wu_manber;

import com.google.common.base.Stopwatch;
import org.alexgaas.search.Search;
import org.javatuples.Pair;

public class WuManberSearch implements Search {
    @Override
    public Integer findFirstStartIndex(String needle, String haystack) {
        Stopwatch timer = Stopwatch.createStarted();

        WuManber search = new WuManber(new String[]{ needle });

        System.out.println("Method took: " + timer.stop());

        return search.searchFirst(haystack);
    }

    @Override
    public Pair<Integer, Integer> findFirst(String needle, String haystack) {
        Stopwatch timer = Stopwatch.createStarted();

        WuManber search = new WuManber(new String[]{ needle });
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
