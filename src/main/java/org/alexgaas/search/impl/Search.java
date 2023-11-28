package org.alexgaas.search.impl;

import org.javatuples.Pair;

import java.util.List;

public interface Search {
    Integer searchFirst(String text);
    List<Integer> search(String text);
    Pair<String,Integer> searchAllPatternsFirst(String text);
    List<Pair<String,Integer>> searchAllPatterns(String text);
}
