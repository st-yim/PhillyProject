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
