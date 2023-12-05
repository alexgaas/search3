package estimate;

import org.alexgaas.estimate.EvalSelector;
import org.alexgaas.search.SearchProvider;
import org.alexgaas.search.domain.SearchInput;
import org.alexgaas.search.domain.SearchResult;
import org.alexgaas.search.impl.aho_carasic.AhoCorasickSearch;
import org.alexgaas.search.impl.horspool.HorspoolSearch;
import org.alexgaas.search.impl.wu_manber.WuManberSearch;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

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

    @AfterAll
    static void done(){
        assertNotNull(selector.GetSamplingList());

        System.out.println();

        int i = 0;
        for(Map<Integer, Double> map: selector.GetSamplingList()){
            // to get sampling (as normal distribution) per search type
            // comment if you do not need this output
            System.out.println(i++ + "\t" + map.get(0));
        }
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

    @RepeatedTest(100)
    public void evaluationTest_OneFile_ThreePatterns() throws IOException {
        var pattern = "ENCYCLOPEDIA|BRITANNICA|ENCYCLOPEDIA BRITANNICA";
        var needle = pattern.split("\\|");

        var textFile = "./src/test/resources/text/144133901.txt";
        var haystack = Files.readString(Path.of(textFile));

        SearchInput input = new SearchInput(needle, haystack);

        SearchResult result = selector.SelectAndExecute(input);

        System.out.println("[" + result.searchId + "]: " + result.stat);

        assertNotNull(result);
    }

    @RepeatedTest(100)
    public void evaluationTest_AllFiles_OnePattern() throws IOException {
        var pattern = "it";
        var needle = pattern.split("\\|");

        var textFile = "./src/test/resources/text/144133902.txt|" +
                "./src/test/resources/text/144133903.txt|" +
                "./src/test/resources/text/144850366.txt|" +
                "./src/test/resources/text/144850367.txt|" +
                "./src/test/resources/text/144850368.txt|" +
                "./src/test/resources/text/144850370.txt|" +
                "./src/test/resources/text/144850373.txt|" +
                "./src/test/resources/text/144850374.txt|" +
                "./src/test/resources/text/144850375.txt|" +
                "./src/test/resources/text/144850376.txt|" +
                "./src/test/resources/text/144850377.txt|" +
                "./src/test/resources/text/144850378.txt|" +
                "./src/test/resources/text/144850379.txt|" +
                "./src/test/resources/text/149977338.txt|" +
                "./src/test/resources/text/149977873.txt|" +
                "./src/test/resources/text/149978642.txt|" +
                "./src/test/resources/text/149979156.txt|" +
                "./src/test/resources/text/149979622.txt|" +
                "./src/test/resources/text/149981189.txt";
        StringBuilder sb = new StringBuilder();
        String[] haystacks = textFile.split("\\|");
        for(String haystack: haystacks){
            sb.append(Files.readString(Path.of(haystack)));
        }
        var haystack = sb.toString();

        SearchInput input = new SearchInput(needle, haystack);

        SearchResult result = selector.SelectAndExecute(input);

        System.out.println("[" + result.searchId + "]: " + result.stat);

        assertNotNull(result);
    }

    @RepeatedTest(100)
    public void evaluationTest_AllFiles_TenPatterns() throws IOException {
        var pattern = "it|different|determined|may|metallic|compreffed body|fubclavian|fire|aluminous|got";
        var needle = pattern.split("\\|");

        var textFile = "./src/test/resources/text/144133902.txt|" +
                "./src/test/resources/text/144133903.txt|" +
                "./src/test/resources/text/144850366.txt|" +
                "./src/test/resources/text/144850367.txt|" +
                "./src/test/resources/text/144850368.txt|" +
                "./src/test/resources/text/144850370.txt|" +
                "./src/test/resources/text/144850373.txt|" +
                "./src/test/resources/text/144850374.txt|" +
                "./src/test/resources/text/144850375.txt|" +
                "./src/test/resources/text/144850376.txt|" +
                "./src/test/resources/text/144850377.txt|" +
                "./src/test/resources/text/144850378.txt|" +
                "./src/test/resources/text/144850379.txt|" +
                "./src/test/resources/text/149977338.txt|" +
                "./src/test/resources/text/149977873.txt|" +
                "./src/test/resources/text/149978642.txt|" +
                "./src/test/resources/text/149979156.txt|" +
                "./src/test/resources/text/149979622.txt|" +
                "./src/test/resources/text/149981189.txt";
        StringBuilder sb = new StringBuilder();
        String[] haystacks = textFile.split("\\|");
        for(String haystack: haystacks){
            sb.append(Files.readString(Path.of(haystack)));
        }
        var haystack = sb.toString();

        SearchInput input = new SearchInput(needle, haystack);

        SearchResult result = selector.SelectAndExecute(input);

        System.out.println("[" + result.searchId + "]: " + result.stat);

        assertNotNull(result);
    }
}
