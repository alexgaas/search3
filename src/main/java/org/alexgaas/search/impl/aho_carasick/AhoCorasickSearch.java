package org.alexgaas.search.impl.aho_carasick;

import org.alexgaas.search.AbstractSearch;
import org.alexgaas.search.domain.SearchInput;
import org.alexgaas.search.domain.SearchResult;
import org.javatuples.Pair;

public class AhoCorasickSearch extends AbstractSearch {

    public AhoCorasickSearch(){
        super("AhoCarasick");
    }

    @Override
    public SearchResult find(SearchInput input) {
        return this.find(input, new AhoCorasick(input.needle));
    }

    @Override
    protected Integer getStartIndex(Pair<String, Integer> pair) {
        return pair.getValue1() - pair.getValue0().length() + 1;
    }

    @Override
    protected Integer getEndIndex(Pair<String, Integer> pair) {
        return pair.getValue1() + 1;
    }
}

