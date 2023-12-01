package org.alexgaas.estimate;

import com.google.common.base.Stopwatch;
import org.alexgaas.search.domain.SearchInput;
import org.alexgaas.search.domain.SearchResult;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class EvalSelector {
    private final ImplEstimator stat;
    private final List<Function<SearchInput, SearchResult>> implementations;

    public EvalSelector(){
        this.stat = new ImplEstimator();
        this.implementations = new ArrayList<>();
    }

    public SearchResult SelectAndExecute(SearchInput input){
        int id = stat.Select();
        Stopwatch watch = Stopwatch.createStarted();

        // execute function
        SearchResult result = implementations.get(id).apply(input);
        watch.stop();

        stat.Complete(id, watch.elapsed().getNano());

        return result;
    }

    public void registerImplementation(Function<SearchInput, SearchResult> func){
        // register implementation
        implementations.add(func);
        stat.emplaceStatData();
    }
}
