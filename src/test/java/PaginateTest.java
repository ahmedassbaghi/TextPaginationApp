import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

public class PaginateTest {

    private static final String INPUT_FILE = "document.txt";
    private static final String OUTPUT_FILE = "output.txt";

    /**
     * Runs before each test.
     * Deletes input and output files to ensure a clean state
     * for each test execution.
     */
    @BeforeEach
    void setup() throws IOException {
        Files.deleteIfExists(Path.of(INPUT_FILE));
        Files.deleteIfExists(Path.of(OUTPUT_FILE));
    }

    /**
     * Basic test with a short input string.
     * Input: "This is a test."
     * Verifies that the output file contains the input text
     * and includes the page separator "--- Page 1 ---".
     * Precondition: 'document.txt' contains a short string.
     * Postcondition: 'output.txt' contains the text and page marker.
     */
    @Test
    void testShortInput() throws IOException {
        Files.writeString(Path.of(INPUT_FILE), "This is a test.");

        Paginate.main(null);

        String output = Files.readString(Path.of(OUTPUT_FILE));
        assertTrue(output.contains("This is a test."));
        assertTrue(output.contains("--- Page 1 ---"));
    }

    /**
     * Test with empty input file.
     * Input: empty 'document.txt'.
     * Verifies that the output file is empty (or matches expected behavior).
     * Precondition: 'document.txt' is empty.
     * Postcondition: 'output.txt' is empty or contains page marker.
     */
    @Test
    void testEmptyInput() throws IOException {
        Files.writeString(Path.of(INPUT_FILE), "");

        Paginate.main(null);

        String output = Files.readString(Path.of(OUTPUT_FILE));
        assertTrue(output.trim().equals(""));
    }

    /**
     * Test with enough input to produce exactly 25 lines.
     * Input: the word "word " repeated 100 times (approx 25 lines at 80 chars).
     * Verifies that only one page separator appears.
     * Precondition: 'document.txt' contains text for 25 lines.
     * Postcondition: 'output.txt' contains one page separator "--- Page 1 ---".
     */
    @Test
    void testExact25Lines() throws IOException {
        StringBuilder input = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            input.append("word ");
        }
        Files.writeString(Path.of(INPUT_FILE), input.toString());

        Paginate.main(null);

        String output = Files.readString(Path.of(OUTPUT_FILE));
        long pageBreaks = output.lines().filter(line -> line.startsWith("--- Page")).count();
        assertEquals(1, pageBreaks);
    }

    /**
     * Test with enough input to produce multiple pages.
     * Input: the word "word " repeated 2000 times.
     * Verifies that multiple page separators are present.
     * Precondition: 'document.txt' contains large text input.
     * Postcondition: 'output.txt' contains multiple page separators like "--- Page X ---".
     */
    @Test
    void testMultiplePages() throws IOException {
        StringBuilder input = new StringBuilder();
        for (int i = 0; i < 2000; i++) {
            input.append("word ");
        }
        Files.writeString(Path.of(INPUT_FILE), input.toString());

        Paginate.main(null);

        String output = Files.readString(Path.of(OUTPUT_FILE));
        long pageBreaks = output.lines().filter(line -> line.startsWith("--- Page")).count();
        assertTrue(pageBreaks > 1);
    }
}
