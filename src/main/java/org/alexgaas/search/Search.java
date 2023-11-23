package org.alexgaas.search;

import org.javatuples.Pair;

import java.util.List;

public interface Search {
    Integer findFirstStartIndex(String needle, String haystack);
    Pair<Integer, Integer> findFirst(String needle, String haystack);
    Pair<String,Integer> findFirstStartIndex(String[] needle, String haystack);
    Pair<String, Pair<Integer, Integer>> findFirst(String[] needle, String haystack);
    List<Pair<String, Pair<Integer, Integer>>> find(String[] needle, String haystack);
}
