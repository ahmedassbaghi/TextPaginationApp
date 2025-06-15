import java.io.*;
import java.util.*;

public class Paginate {
    private static final int MAX_LINE_LENGTH = 80;
    private static final int LINES_PER_PAGE = 25;

    public static void main(String[] args) {
        String inputFileName = "document.txt";
        String outputFileName = "output.txt";

        try {
            String text = readFile(inputFileName);
            List<String> lines = wrapText(text);
            writePaginatedOutput(lines, outputFileName);
            System.out.println("Paginated output written to " + outputFileName);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Read the entire content of the file into a String
    private static String readFile(String filename) throws IOException {
        return new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filename)));
    }

    // Wrap text without breaking words across lines longer than 80 characters
    private static List<String> wrapText(String text) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split("\\s+");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            if (currentLine.length() + word.length() + (currentLine.length() == 0 ? 0 : 1) <= MAX_LINE_LENGTH) {
                if (currentLine.length() > 0) currentLine.append(" ");
                currentLine.append(word);
            } else {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word);
            }
        }
        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        return lines;
    }

    // Write paginated lines to output.txt
    private static void writePaginatedOutput(List<String> lines, String outputFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            int page = 1;
            for (int i = 0; i < lines.size(); i++) {
                writer.write(lines.get(i));
                writer.newLine();

                if ((i + 1) % LINES_PER_PAGE == 0 || i == lines.size() - 1) {
                    writer.write(String.format("--- Page %d ---%n", page++));
                }
            }
        }
    }
}
