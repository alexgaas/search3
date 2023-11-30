package org.alexgaas.search.domain;

import com.google.common.base.Stopwatch;

import java.util.List;

public class SearchResult {

    public static class SearchResultEntry {
        public String pattern;
        public Integer startIndex;
        public Integer endIndex;

        public SearchResultEntry(String pattern, Integer startIndex, Integer endIndex){
            this.pattern = pattern;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }
    }

    public String searchId;

    public Stopwatch stat;

    public List<SearchResultEntry> searchResultEntryList;

    public SearchResult(String searchId, List<SearchResultEntry> searchResultEntryList, Stopwatch stat){
        this.searchId = searchId;
        this.searchResultEntryList = searchResultEntryList;
        this.stat = stat;
    }
}


