package estimate;

import org.alexgaas.estimate.EvalSelector;
import org.alexgaas.search.SearchProvider;
import org.alexgaas.search.domain.SearchInput;
import org.alexgaas.search.domain.SearchResult;
import org.alexgaas.search.impl.aho_carasick.AhoCorasickSearch;
import org.alexgaas.search.impl.horspool.HorspoolSearch;
import org.alexgaas.search.impl.wu_manber.WuManberSearch;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EvalSelectorTest {

    private static EvalSelector selector;

    private static final Boolean SHOW_DISTRIBUTION_DATA = false;
    private static final Boolean SHOW_STAT_DATA = false;
    private static final Boolean SHOW_PLOT_DATA = true;


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
            if (SHOW_DISTRIBUTION_DATA) {
                System.out.println(i++ + "\t" + map.get(0));
            }
        }
    }

    void tableForPlot(int id, SearchResult result, TimeUnit unit){
        TimeUnit u = TimeUnit.NANOSECONDS;
        if (unit != null){
            u = unit;
        }

        int elapsedTime = 0;
        switch (u){
            case NANOSECONDS:
                elapsedTime = result.stat.elapsed().getNano();
                break;
            case MICROSECONDS:
                elapsedTime = result.stat.elapsed().getNano() / 1000;
                break;
            case MILLISECONDS:
                elapsedTime = Math.toIntExact(result.stat.elapsed().toMillis());
                break;
            case SECONDS:
                elapsedTime = Math.toIntExact(result.stat.elapsed().getSeconds());
                break;
            default:
                throw new NotImplementedException("There is no table / plot have been built for this unit [" + unit + "]");
        }

        switch (result.searchId){
            case "AhoCarasick":
                System.out.println(id + "\t" + elapsedTime + "\t\t");
                break;
            case "Harspool":
                System.out.println(id + "\t\t" + elapsedTime + "\t");
                break;
            case "WuManber":
                System.out.println(id + "\t\t\t" + elapsedTime);
                break;
        }

        assertNotNull(result);
    }

    @RepeatedTest(100)
    void basicEvaluationTest(RepetitionInfo info){
        String[] needle = {"announce", "cpm"};
        String haystack = "cpmxannualxconferencexannounce";
        SearchInput input = new SearchInput(needle, haystack);

        SearchResult result = selector.SelectAndExecute(input);

        if (SHOW_STAT_DATA) {
            System.out.println("[" + result.searchId + "]: " + result.stat);
        }

        if (SHOW_PLOT_DATA) {
            tableForPlot(info.getCurrentRepetition(), result, TimeUnit.NANOSECONDS);
        }

        assertNotNull(result);
    }

    @RepeatedTest(100)
    public void evaluationTest_OneFile_ThreePatterns(RepetitionInfo info) throws IOException {
        var pattern = "ENCYCLOPEDIA|BRITANNICA|ENCYCLOPEDIA BRITANNICA";
        var needle = pattern.split("\\|");

        var textFile = "./src/test/resources/text/144133901.txt";
        var haystack = Files.readString(Path.of(textFile));

        SearchInput input = new SearchInput(needle, haystack);

        SearchResult result = selector.SelectAndExecute(input);

        if (SHOW_STAT_DATA) {
            System.out.println("[" + result.searchId + "]: " + result.stat);
        }

        if (SHOW_PLOT_DATA) {
            tableForPlot(info.getCurrentRepetition(), result, TimeUnit.MICROSECONDS);
        }

        assertNotNull(result);
    }

    @RepeatedTest(100)
    public void evaluationTest_AllFiles_OnePattern(RepetitionInfo info) throws IOException {
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

        if (SHOW_STAT_DATA) {
            System.out.println("[" + result.searchId + "]: " + result.stat);
        }

        if (SHOW_PLOT_DATA) {
            tableForPlot(info.getCurrentRepetition(), result, TimeUnit.MILLISECONDS);
        }

        assertNotNull(result);
    }

    @RepeatedTest(100)
    public void evaluationTest_AllFiles_TenPatterns(RepetitionInfo info) throws IOException {
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

        if (SHOW_STAT_DATA) {
            System.out.println("[" + result.searchId + "]: " + result.stat);
        }

        if (SHOW_PLOT_DATA) {
            tableForPlot(info.getCurrentRepetition(), result, TimeUnit.MILLISECONDS);
        }

        assertNotNull(result);
    }
}
