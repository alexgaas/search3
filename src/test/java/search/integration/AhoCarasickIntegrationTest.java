package search.integration;

import org.alexgaas.search.SearchProvider;
import org.alexgaas.search.domain.SearchInput;
import org.alexgaas.search.impl.aho_carasick.AhoCorasickSearch;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AhoCarasickIntegrationTest {
    @ParameterizedTest
    @CsvSource({
        "ENCYCLOPEDIA|BRITANNICA|ENCYCLOPEDIA BRITANNICA," +
                "./src/test/resources/text/144133901.txt",
        "BRITANNICA," +
                "./src/test/resources/text/144133901.txt",
        "ENCYCLOPEDIA BRITANNICA," +
                "./src/test/resources/text/144133901.txt"
    })
    public void ahoCarasickIntegrationTest_OneFile_ThreePatterns(String pattern, String textFile) throws IOException {
        var needle = pattern.split("\\|");
        var haystack = Files.readString(Path.of(textFile));

        SearchProvider search = new AhoCorasickSearch();
        var result = search.find(new SearchInput(needle, haystack));

        for(String p: needle){
            result.searchResultEntryList.stream()
                .filter(e -> Objects.equals(e.pattern, p))
                .forEach(e ->
                    assertEquals(haystack.substring(e.startIndex, e.endIndex), p)
                );
        }

        System.out.println("[" + pattern + "]:\t " + result.stat);
    }

    @ParameterizedTest
    @CsvSource({
            "ENCYCLOPEDIA|BRITANNICA," +
                    "./src/test/resources/text/144133901.txt",
            "and all thofe|and all thofe numerous deductions," +
                    "./src/test/resources/text/144133902.txt",
            "OLESCO|a city and port-town of Brad," +
                    "./src/test/resources/text/144133903.txt",
    })
    public void ahoCarasickIntegrationTest_ThreeFiles_TwoPatterns(String pattern, String textFile) throws IOException {
        var needle = pattern.split("\\|");
        var haystack = Files.readString(Path.of(textFile));

        SearchProvider search = new AhoCorasickSearch();
        var result = search.find(new SearchInput(needle, haystack));

        for(String p: needle){
            result.searchResultEntryList.stream()
                    .filter(e -> Objects.equals(e.pattern, p))
                    .forEach(e ->
                            assertEquals(haystack.substring(e.startIndex, e.endIndex), p)
                    );
        }

        System.out.println("[" + pattern + "]:\t " + result.stat);
    }

    @ParameterizedTest
    @CsvSource({
            "it," +
                    "./src/test/resources/text/144133902.txt|" +
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
                    "./src/test/resources/text/149981189.txt",
    })
    public void ahoCarasickIntegrationTest_AllFiles_OnePattern(String pattern, String textFile) throws IOException {
        var needle = pattern.split("\\|");

        StringBuilder sb = new StringBuilder();
        String[] haystacks = textFile.split("\\|");
        for(String haystack: haystacks){
            sb.append(Files.readString(Path.of(haystack)));
        }
        var haystack = sb.toString();

        SearchProvider search = new AhoCorasickSearch();
        var result = search.find(new SearchInput(needle, haystack));

        for(String p: needle){
            result.searchResultEntryList.stream()
                    .filter(e -> Objects.equals(e.pattern, p))
                    .forEach(e ->
                            assertEquals(haystack.substring(e.startIndex, e.endIndex), p)
                    );
        }

        System.out.println("[" + pattern + "]:\t " + result.stat);
    }

    @ParameterizedTest
    @CsvSource({
            "it|different|determined|may|metallic|compreffed body|fubclavian|fire|aluminous|got," +
                    "./src/test/resources/text/144133902.txt|" +
                    "./src/test/resources/text/144850366.txt|" +
                    "./src/test/resources/text/144850368.txt|" +
                    "./src/test/resources/text/144850370.txt|" +
                    "./src/test/resources/text/144850374.txt|" +
                    "./src/test/resources/text/144850375.txt|" +
                    "./src/test/resources/text/149977338.txt|" +
                    "./src/test/resources/text/149977873.txt|" +
                    "./src/test/resources/text/149978642.txt|" +
                    "./src/test/resources/text/149981189.txt",
    })
    public void ahoCarasickIntegrationTest_TenFiles_TenPattern(String pattern, String textFile) throws IOException {
        var needle = pattern.split("\\|");

        StringBuilder sb = new StringBuilder();
        String[] haystacks = textFile.split("\\|");
        for(String haystack: haystacks){
            sb.append(Files.readString(Path.of(haystack)));
        }
        var haystack = sb.toString();

        SearchProvider search = new AhoCorasickSearch();
        var result = search.find(new SearchInput(needle, haystack));

        for(String p: needle){
            result.searchResultEntryList.stream()
                    .filter(e -> Objects.equals(e.pattern, p))
                    .forEach(e ->
                            assertEquals(haystack.substring(e.startIndex, e.endIndex), p)
                    );
        }

        System.out.println("[" + pattern + "]:\t " + result.stat);
    }
}
