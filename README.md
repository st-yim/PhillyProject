# PhillyProject

A Java-based data analysis application that ingests and processes multiple real-world datasets (COVID‑19 stats, property values, population data) from the Philadelphia Open Data portal.

## 📋 Overview

PhillyProject is a Java-based application built as part of the **CIT 5940** course. It reads and analyzes open data from Philadelphia, enabling:
- Calculation of statistics like average property value, livable area, vaccination rates.
- Robust handling of CSV and JSON with inconsistent data entries.

---

## 🚀 Features

- Modular, N-tier architecture
- Multi-format data parsing (CSV/JSON)
- Statistical computation via Strategy pattern
- Singleton-based logging system
- Query result caching with memoization
- Simple CLI for querying by ZIP code

---

## 🛠️ Technologies

- **Language:** Java 11+
- **Build Tool:** Maven or Gradle
- **Libraries:** Jackson (JSON), Apache Commons CSV
- **Patterns:** Strategy, Singleton, Memoization

---

## 📁 Input Test Files

This project uses multiple structured input data files in **CSV** and **JSON** formats. Each file type corresponds to a specific dataset used in the analysis.

### 1. COVID-19 Data
- **Formats:** `.csv`, `.json`
- **Typical Fields:**
  - `zip_code` – 5-digit ZIP code of reporting location
  - `timestamp` – `YYYY-MM-DD hh:mm:ss`
  - `partially_vaccinated` – individuals with only one vaccine dose
  - `fully_vaccinated` – individuals with two vaccine doses
  - `total_tests` – total COVID tests conducted (positive and negative)
  - `booster_doses`, `hospitalizations`, `deaths` – cumulative values
- **Format Notes:**
  - JSON version uses an array of objects (similar to the flu tweets format from the Solo Project).
  - CSV includes a header row and supports flexible column order.
  - Records are ignored if `zip_code` is not 5 digits or `timestamp` format is invalid.
  - Empty fields are treated as 0 (except for `zip_code`/`timestamp`).
 
---

## 📁 Data Management Module

The `edu.upenn.cit594.datamanagement` package handles **file parsing and tweet ingestion** across multiple formats, supporting `.json`, `.csv`, and `.txt`.

### 🔍 Core Classes

#### 1. `TweetReader` (Base Class)
- Abstract base class for file readers.
- Supports:
  - Getting file extension with format validation.
  - Reading `.csv` files with a basic structure (state, lat, long).
  - Resource management via `AutoCloseable`.

#### 2. `JsonReader` (Extends `TweetReader`)
- Parses `.json` files containing tweet data.
- Uses `JSONParser` to extract tweet objects with `location`, `time`, and `text`.
- Each tweet is wrapped in a `Tweet` object with a dummy `identifier = 0`.

#### 3. `TxtReader` (Extends `TweetReader`)
- Parses `.txt` files with tweet entries tab-delimited into four fields: `location`, `identifier`, `time`, and `text`.
- Each tweet is parsed and instantiated as a `Tweet` object.

### 🧪 Unit Tests
Each reader class has a corresponding test file:
- `JsonReaderTest`
- `TxtReaderTest`
- `TweetReaderTest`

Tests include:
- File parsing validation
- Extension checks
- Output verification by printing parsed `Tweet` or string data

### 🔄 Design Pattern Used
- Inheritance (`TweetReader` as base class)
- Separation of concerns: each file type has its own parser
- Graceful exception handling and cleanup in `finally` blocks

---

## 🧾 Logging Module

The `edu.upenn.cit594.logging` package implements a centralized, reusable logging mechanism for the application using the **Singleton design pattern**.

### 🔧 Core Class

#### `Logger`
- Implements `AutoCloseable` for safe resource management.
- Designed as a **Singleton** — only one instance of `Logger` exists at runtime.
- Handles both file creation and appending:
  - If the file does **not** exist, it creates a new file.
  - If the file **exists**, it opens in append mode to retain previous logs.
- Provides a `log(String msg)` method to write messages and flush immediately.

### 🔄 Design Pattern Used
- **Singleton**: Ensures consistent, global logging behavior across the application.
- **AutoCloseable**: Supports proper closure via try-with-resources.

### 🛠 Example Usage
```java
Logger logger = Logger.getInstance();
logger.setOutput("log.txt");
logger.log("Query ZIP: 19104");
logger.close();

```
--- 

## ⚙️ Processor Module

The `edu.upenn.cit594.processor` package serves as the **core logic engine** of the application. It orchestrates data from multiple sources (CSV, TXT, JSON) and applies pattern-matching, coordinate mapping, and state-level aggregation to produce structured insights.

### 🔧 Core Class: `TweetProcessor`

#### ✅ Responsibilities
- Ingest and store input data from various file readers.
- Identify flu-related tweets using regex-based pattern matching.
- Calculate the **nearest U.S. state** for each tweet based on geolocation.
- Count and aggregate flu tweets by state.

---

### 📊 Key Functionalities

| Method                  | Description                                                                 |
|-------------------------|-----------------------------------------------------------------------------|
| `getStateMap()`         | Maps U.S. state names to geographic coordinates from a CSV file.            |
| `getRelevantTweet()`    | Filters tweets containing `"flu"` or `"#flu"` using extensive regex logic. |
| `getLocationOfTweet()`  | Calculates the closest state to each tweet using Euclidean distance.        |
| `getFluState()`         | Builds a frequency map of states with flu-related tweet counts.             |

---

### 🧪 Unit Testing

The `TweetProcessorTest` suite verifies:
- State-coordinate mapping from CSV
- Regex-based flu tweet filtering
- Geolocation-based tweet origin assignment
- Final aggregation and logger output

---

### 🔄 Design Insights

- **Overloaded Constructors:** Supports multiple file input types (CSV, TXT, JSON).
- **Distance Calculation:** Uses basic Euclidean geometry between tweet and state coordinates.
- **Regex Filtering:** Covers various formats of flu-related terms (e.g., `#flu.`, `flu!`, `flu `).
- **Logging Integration:** Each tweet’s assigned state is logged via the `Logger` Singleton.

---

## 🧪 Student Tests

The `edu.upenn.cit594.studenttests` package includes **integration-level JUnit tests** to validate the full execution flow of the application — from file input to final console output and logging.

### 📋 Overview

These tests simulate real execution of the `Main` class by:
- Creating test data (both trivial and real-world)
- Invoking the application using different file combinations
- Capturing and parsing the output stream
- Validating output format and values
- Checking the contents of the generated log file

---

### 🛠️ Core Class: `BasicTests`

#### 🔑 Key Methods

| Method | Purpose |
|--------|---------|
| `runMain()` | Programmatically runs `Main.main()` and captures its printed output. |
| `extractResults()` | Parses output lines into a `Map<String, Integer>` of state → tweet count. |
| `extractLog()` | Reads and parses log file contents into a list of `[state, tweet]` arrays. |
| `makeTrivialTest()` | Generates dummy input files for quick sanity tests. |
| `print2DStrings()` | Utility to format log lines for visibility in test output. |

---

### 📌 Notable Tests

- **`testStuff()`**  
  Runs a single trivial scenario with one tweet and one state. Verifies correct mapping and logging.

- **`testThreeThings()`**  
  A comprehensive test that runs:
  - Actual data files (`flu_tweets.json`, `flu_tweets.txt`)
  - A synthetic file (`trivial_tweets.json`)
  - Verifies result accuracy and log output for each

---

### 🧪 Design Highlights

- Uses `ByteArrayOutputStream` to capture `System.out` output.
- Includes regex-based parsing to ensure strict output format validation.
- Enables full-stack testing across input parsing, processing, and logging.

---

## 🖥️ Command-Line User Interface (UI)

The `edu.upenn.cit594.ui` package contains the `CommandLineUserInterface` class — the entry point for triggering the program's core tweet analysis and output functionality.

### 📋 Overview

The UI handles:
- Program startup and control flow
- Integrating data from different readers (CSV, JSON, TXT)
- Displaying results in a readable format (state → flu tweet count)

---

### 🔧 Key Class: `CommandLineUserInterface`

#### 🔑 Constructor

```java
public CommandLineUserInterface(TweetProcessor processor1, TweetProcessor processor2, String logFile)
```

---

## 🧰 Utility Module

The `edu.upenn.cit594.util` package provides foundational data structures used across the application. It contains the core domain model used to represent and pass tweet data between different layers of the application.

### 🔧 Core Class: `Tweet`

Represents a single tweet entry with location, timestamp, text, and identifier information. This class is **immutable** and serves as the main data model throughout the system.

#### ✅ Fields

| Field        | Type     | Description                                      |
|--------------|----------|--------------------------------------------------|
| `location`   | `String` | Geographical source or context of the tweet      |
| `identifier` | `int`    | Unique (or placeholder) identifier for the tweet |
| `date`       | `String` | Timestamp or date string of the tweet            |
| `text`       | `String` | Content of the tweet                             |

#### 🧱 Constructor

```java
public Tweet(String location, int identifier, String date, String text)
```

### 📦 Methods

| Method            | Description                                   |
|-------------------|-----------------------------------------------|
| `getLocation()`   | Returns the tweet's location                  |
| `getIdentifier()` | Returns the tweet's numeric ID                |
| `getDate()`       | Returns the date or timestamp of the tweet    |
| `getText()`       | Returns the tweet's full text content         |
| `toString()`      | Stringified representation for debugging      |

---

### 🎯 Design Considerations

- **Immutability:** All fields are declared `final` to ensure thread safety and data consistency.
- **Reusability:** The `Tweet` class is shared across multiple modules including data readers, processors, and loggers.
- **Readability:** A clean `toString()` method enhances unit test debugging and improves console output clarity.

---

## 🧮 Main Program Entrypoint

The `edu.upenn.cit594.Main` class serves as the **central entry point** of the application. It orchestrates the program flow, validates inputs, and initiates the processing and logging pipeline.

---

### 📦 Class: `Main`

This class manages:

- File validation (e.g., input types: `.csv`, `.json`, `.txt`)
- Initialization of the appropriate `TweetReader` subtype (`JsonReader`, `TxtReader`)
- Initialization of the logging system (`Logger`)
- Launching the CLI interface (`CommandLineUserInterface`)

---

### 🚦 Input Requirements

The program expects **exactly 3 command-line arguments**:

1. **Tweet file** — `.json` or `.txt`
2. **State coordinate file** — `.csv`
3. **Log file** — output file to write the processed log

If the number of arguments is incorrect, the program prints an appropriate error and exits.

---

### 🔧 Runtime Flow

- Parses file extensions and validates supported formats.
- Initializes `TweetProcessor` instances based on detected input types.
- Sets up `Logger` with append support:

  ```java
  Logger logger = Logger.getInstance();
  logger.setOutput(logFile);
  ```

---

### ✅ Example Usage

```java
java Main flu_tweets.json states.csv log.txt
```

### 💥 Exception Handling

- Catches and prints all `IOException` issues from file operations.
- Gracefully fails with informative messages if unsupported file types are provided.

---

### 💡 Design Highlights

| Principle               | Implementation                                                                 |
|-------------------------|----------------------------------------------------------------------------------|
| **Factory Pattern**     | Chooses the appropriate reader class based on file extension                    |
| **Fail Fast**           | Validates all file types up front and exits with a descriptive error if invalid |
| **Separation of Concerns** | Delegates parsing, processing, and logging to dedicated modules              |


---

## ✅ Final Notes

This project was designed to demonstrate:

- **Modular Java development**
- **Robust multi-format data ingestion** (`.json`, `.txt`, `.csv`)
- **Geolocation-based analysis of textual data**

It showcases key software engineering principles such as:

- **Separation of concerns**
- **Immutability and clean design**
- **Reusable architecture**

Through the integration of pattern-based filtering and state mapping, this project simulates real-world tweet analysis using structured design patterns and a layered processing model.




