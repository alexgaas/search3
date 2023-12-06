package search;

import org.alexgaas.search.SearchProvider;
import org.alexgaas.search.domain.SearchInput;
import org.alexgaas.search.domain.SearchResult;
import org.alexgaas.search.impl.aho_carasick.AhoCorasickSearch;
import org.alexgaas.search.impl.horspool.HorspoolSearch;
import org.alexgaas.search.impl.wu_manber.WuManberSearch;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchIntegrityTest {

    @Test
    void basicIntegrityTest(){
        String[] needle = {"announce", "cpm"};
        String haystack = "cpmxannualxconferencexannounce";

        // Aho-Corasick
        SearchProvider ahoCorasickSearch = new AhoCorasickSearch();
        SearchResult ahoCorasickResult = ahoCorasickSearch.find(new SearchInput(needle, haystack));
        SearchResult.SearchResultEntry ahoCorasickEntry = ahoCorasickResult.searchResultEntryList.get(0);

        // Horspool
        SearchProvider horspoolSearch = new HorspoolSearch();
        SearchResult horspoolSearchResult = horspoolSearch.find(new SearchInput(needle, haystack));
        SearchResult.SearchResultEntry horspoolSearchEntry = horspoolSearchResult.searchResultEntryList.get(0);

        // Wu-Manber
        SearchProvider wuManberSearch = new WuManberSearch();
        SearchResult wuManberSearchResult = wuManberSearch.find(new SearchInput(needle, haystack));
        SearchResult.SearchResultEntry wuManberSearchEntry = wuManberSearchResult.searchResultEntryList.get(0);

        // Aho-Corasick / Horspool
        assertEquals(haystack.substring(ahoCorasickEntry.startIndex, ahoCorasickEntry.endIndex),
                haystack.substring(horspoolSearchEntry.startIndex, horspoolSearchEntry.endIndex));
        // Aho-Corasick / Wu-Manber
        assertEquals(haystack.substring(ahoCorasickEntry.startIndex, ahoCorasickEntry.endIndex),
                haystack.substring(wuManberSearchEntry.startIndex, wuManberSearchEntry.endIndex));
        // Horspool / Wu-Manber
        assertEquals(haystack.substring(horspoolSearchEntry.startIndex, horspoolSearchEntry.endIndex),
                haystack.substring(wuManberSearchEntry.startIndex, wuManberSearchEntry.endIndex));
    }
}
