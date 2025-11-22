# Taqdeer (تقدير)

**A functional GPA calculation engine and grade management utility built in Clojure.**

**Taqdeer** (*Arabic for "Grade"*) is a tool designed to model academic performance as structured data. It allows for the precise calculation of Semester GPA (SGPA) using a standard 4.0 weighted scale, moving beyond simple calculators by treating course records as immutable data structures.

## Installation

### Prerequisites

- Java: JDK 8 or higher.
- Leiningen: (Optional, for building from source).

### From Source

Clone the repository and run the project using Leiningen:

``` Bash
git clone https://github.com/os14you/taqdeer.git
cd taqdeer
lein run
```

## Usage

Currently, Taqdeer operates as a calculation engine for pre-defined course data.

When executed, the application:

- Loads the defined course map.
- Normalizes letter grades (e.g., "A+", "C") into computational quality points.
- Calculates the weighted average against credit hours.
- Outputs the calculated GPA to standard output.

Example Run:

```Bash
$ lein run
Your GPA is: 3.125
```

## Options

*Note: Taqdeer is currently in Alpha. Command-line arguments are planned for v01.0.0.*

Currently, data is defined internally in `core.clj`. Future versions will support the following flags:

- `-f, --file <path>`: Load grades from a JSON or EDN file.
- `-i, --interactive`: Enter interactive mode to input grades manually.
- `-v, --verbose`: Print detailed breakdown of points per course.

### Bugs & Limitations

- **Case Sensitivity:** The engine currently expects uppercase letter grades (e.g., "A", not "a").
- **Data Persistence:** Grades are currently transient; they do not persist after the program closes.
- **Grading Scale:** The specific weightings (A=3.7, etc.) are currently hardcoded to a standard 4.0 scale.