import org.alexgaas.search.Search;
import org.alexgaas.search.horspool.HorspoolSearch;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HorspoolSearchTest {
    @Test
    void findFirstBasicTest(){
        String needle = "announce";
        String haystack = "cpmxannualxconferencexannounce";

        Search search = new HorspoolSearch();
        Pair<Integer, Integer> result = search.findFirst(needle, haystack);
        assertEquals(haystack.substring(result.getValue0(), result.getValue1()), needle);
    }

    @Test
    void findFirst1MBTest() throws URISyntaxException, IOException {
        String needle = "accordingly";
        String haystack = Files.readString(
                Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("book1")).toURI())
        );

        Search search = new HorspoolSearch();
        Pair<Integer, Integer> result = search.findFirst(needle, haystack);
        assertEquals(haystack.substring(result.getValue0(), result.getValue1()), needle);
    }
}
