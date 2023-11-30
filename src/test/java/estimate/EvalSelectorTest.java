package estimate;

import org.alexgaas.estimate.EvalSelector;
import org.alexgaas.search.SearchProvider;
import org.alexgaas.search.domain.SearchInput;
import org.alexgaas.search.domain.SearchResult;
import org.alexgaas.search.impl.aho_carasic.AhoCorasickSearch;
import org.alexgaas.search.impl.horspool.HorspoolSearch;
import org.alexgaas.search.impl.wu_manber.WuManberSearch;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EvalSelectorTest {

    private static EvalSelector selector;

    @BeforeAll
    static void setup(){
        selector = new EvalSelector();

        SearchProvider ahoCorasickSearch = new AhoCorasickSearch();
        SearchProvider horspoolSearch = new HorspoolSearch();
        SearchProvider wuManberSearch = new WuManberSearch();

        // register implementation functions
        selector.registerImplementation(ahoCorasickSearch::find);
        selector.registerImplementation(horspoolSearch::find);
        selector.registerImplementation(wuManberSearch::find);
    }

    @RepeatedTest(100)
    void basicEvaluationTest(){
        String[] needle = {"announce", "cpm"};
        String haystack = "cpmxannualxconferencexannounce";
        SearchInput input = new SearchInput(needle, haystack);

        SearchResult result = selector.SelectAndExecute(input);

        System.out.println("[" + result.searchId + "]: " + result.stat);

        assertNotNull(result);
    }
}
