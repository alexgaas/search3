package org.alexgaas.search.impl.wu_manber;

import org.alexgaas.search.AbstractSearch;
import org.alexgaas.search.domain.SearchInput;
import org.alexgaas.search.domain.SearchResult;
import org.javatuples.Pair;

public class WuManberSearch extends AbstractSearch {
    public WuManberSearch() {
        super("WuManber");
    }

    @Override
    public SearchResult find(SearchInput input) {
        return this.find(input, new WuManber(input.needle));
    }

    @Override
    protected Integer getStartIndex(Pair<String, Integer> pair) {
        return pair.getValue1();
    }

    @Override
    protected Integer getEndIndex(Pair<String, Integer> pair) {
        return pair.getValue1() + pair.getValue0().length();
    }
}
