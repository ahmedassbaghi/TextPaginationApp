# Text Pagination App (Java + Maven)

This application reads a single-line text from `document.txt`, wraps it without breaking words (max 80 characters per line), paginates the text into pages of 25 lines, and adds a footer like:

--- Page X ---

The result is saved to `output.txt`.

## How it works

- Reads the full text from `document.txt`.
- Splits it into words and wraps lines to 80 characters max, without cutting any word.
- Groups lines into pages of 25 lines.
- Appends a footer to each page: "--- Page 1 ---", "--- Page 2 ---", etc.
- The final paginated result is written to `output.txt`.

## Running the app

### Prerequisites

- Java 8 or higher
- Maven

### Steps

1. Clone the repository:

   git clone https://github.com/ahmedassbaghi/TextPaginationApp.git  
   cd TextPaginationApp

2. Edit or replace `document.txt` with your input text.

3. Compile the application:
   ```bash
   mvn compile

4. Run the application:
   ```bash
   mvn exec:java -Dexec.mainClass=Paginate

5. Check the generated file `output.txt`.

## Running tests

Tests are written with JUnit 5 and located in `src/test/java/PaginateTest.java`. They cover:

- Short input → Fits in one page and includes footer.
- Exact 25 lines → Confirms pagination with no overflow.
- Multiple pages → Checks page breaks and numbering.
- Empty input → Verifies that no content produces empty output.

To run the tests:
   ```bash
   mvn test

## Clean up

To remove compiled files:

   mvn clean

## Notes

- Input must be a single line.
- Words are separated by spaces.
- Words longer than 80 characters are placed on their own line.
