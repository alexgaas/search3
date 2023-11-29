package org.alexgaas.estimate;

import com.google.common.base.Stopwatch;
import org.alexgaas.search.domain.SearchInput;
import org.alexgaas.search.domain.SearchResult;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Selector {
    private final Estimator stat;
    private final List<Function<SearchInput, SearchResult>> implementations;

    public Selector(){
        this.stat = new Estimator(new ArrayList<>());
        this.implementations = new ArrayList<>();
    }
    public Selector(Estimator stat, List<Function<SearchInput, SearchResult>> implementations) {
        this.stat = stat;
        this.implementations = implementations;
    }

    public SearchResult SelectAndExecute(SearchInput input){
        // might be added any expression here
        Boolean considerable = true;

        int id = stat.Select(considerable);
        Stopwatch watch = Stopwatch.createStarted();

        // execute function
        SearchResult result = implementations.get(id).apply(input);

        watch.stop();
        if (considerable)
        {
            stat.Complete(id, watch.elapsed().getSeconds(), 1000);
        }

        return result;
    }

    void registerImplementation(Function<SearchInput, SearchResult> func){
        // register implementation
        implementations.add(func);
    }
}
