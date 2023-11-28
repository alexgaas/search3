package org.alexgaas.search.impl.horspool;

import org.alexgaas.search.AbstractSearch;
import org.alexgaas.search.domain.SearchInput;
import org.alexgaas.search.domain.SearchResult;
import org.javatuples.Pair;

public class HorspoolSearch extends AbstractSearch {
    public HorspoolSearch() {
        super("Harspool");
    }

    @Override
    public SearchResult find(SearchInput input) {
        return this.find(input, new Horspool(input.needle));
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
