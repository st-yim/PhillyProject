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
