package search.unit;

import org.alexgaas.search.domain.SearchInput;
import org.alexgaas.search.domain.SearchResult;
import org.alexgaas.search.impl.aho_carasick.AhoCorasickSearch;
import org.alexgaas.search.SearchProvider;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AhoCarasickSearchTest {
    @Test
    void findFirstBasicTest(){
        String needle = "announce";
        String haystack = "cpmxannualxconferencexannounce";

        AhoCorasickSearch searchProvider = new AhoCorasickSearch();
        Pair<Integer, Integer> result = searchProvider.findFirst(needle, haystack);
        assertEquals(haystack.substring(result.getValue0(), result.getValue1()), needle);
    }

    @Test
    void findFirstBasicTest_MultiPattern(){
        String[] needle = {"announce", "cpm"};
        String haystack = "cpmxannualxconferencexannounce";

        AhoCorasickSearch searchProvider = new AhoCorasickSearch();
        SearchResult.SearchResultEntry entry = searchProvider.findFirst(needle, haystack);

        assertEquals(
                haystack.substring(entry.startIndex, entry.endIndex),
                Arrays.stream(needle)
                        .filter(f -> f.equals(entry.pattern))
                        .findFirst()
                        .map(Object::toString)
                        .orElse("")
        );
    }

    @Test
    void findBasicTest_MultiPattern(){
        String[] needle = {"announce", "cpm"};
        String haystack = "cpmxannualxconferencexannounce";

        SearchProvider searchProvider = new AhoCorasickSearch();
        SearchResult result = searchProvider.find(new SearchInput(needle, haystack));

        SearchResult.SearchResultEntry entry = result.searchResultEntryList.get(0);
        String pattern = entry.pattern;

        assertEquals(
                haystack.substring(entry.startIndex, entry.endIndex),
                Arrays.stream(needle)
                        .filter(f -> f.equals(pattern))
                        .findFirst()
                        .map(Object::toString)
                        .orElse("")
        );

        entry = result.searchResultEntryList.get(1);
        String pattern2 = entry.pattern;

        assertEquals(
                haystack.substring(entry.startIndex, entry.endIndex),
                Arrays.stream(needle)
                        .filter(f -> f.equals(pattern2))
                        .findFirst()
                        .map(Object::toString)
                        .orElse("")
        );
    }
}
