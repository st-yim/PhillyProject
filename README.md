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





