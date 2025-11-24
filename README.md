# Taqdeer (تقدير)

**A functional GPA calculation engine and grade management utility built in Clojure.**

**Taqdeer** (*Arabic for "Grade"*) is a tool designed to model academic performance as structured data. It allows for the precise calculation of Semester GPA (SGPA) using a configurable weighted scale and calculating Cumulative GPA (CGPA) from multiple semesters.

## Installation

### Prerequisites

- Java: JDK 8 or higher.
- Leiningen: (Optional, for building from source).

### From Source

Clone the repository and run the project using Leiningen:

``` Bash
git clone [https://github.com/os14you/taqdeer.git](https://github.com/os14you/taqdeer.git)
cd taqdeer
lein run
```

## Usage

Taqdeer operates as a calculation engine that separates grading logic from course data.

When executed, the application:

1. **Loads Configuration**: Reads the `config.edn` file to determine the University's grading scale (e.g., A+ = 4.0).
2. **Normalizes Data**: Converts letter grades from the internal course map into computational quality points.
3. **Calculates Metrics**:
    - Computes the **Semester GPA** weighted against credit hours.
    - Computes the **Cumulative GPA** across recorded semesters.
4. **Outputs Results**: Prints the calculated metrics to standard output.

### Example Run

```bash
$ lein run
Your GPA is: 3.125
Your Cumulative GPA is: 3.408
```

## Configuration

The application relies on a `config.edn` file in the root directory to define the grading scale. This allows the engine to be adapted to different university standards without changing the source code.

Example `config.edn`:

```clojure
{:university "Kafr Elshiekh University"
 :grade-scale {:A+ 4 :A 3.7 :B+ 3.3 :B 3 :C+ 2.7 :C 2.4 :D+ 2 :D 1.7 :F 0}}
```

## Options

*Note: Taqdeer is currently in Alpha (v0.0.1).*

Currently, course data is defined internally in `core.clj`. Future versions will support the following flags:

  - `-f, --file <path>`: Load grades from a JSON or EDN file.
  - `-i, --interactive`: Enter interactive mode to input grades manually.

### Bugs & Limitations

  - **Case Sensitivity:** The engine expects uppercase letter grades (e.g., "A", not "a").
  - **Data Persistence:** Grades are currently transient; they do not persist after the program closes.
  - **Cumulative GPA:** Currently calculated as a simple average of semester GPAs, rather than a weighted average of all total credit hours.
