# Introduction to Taqdeer (تقدير)

**Taqdeer** is a Clojure-based utility designed to model and calculate academic performance. While many tools exist to perform one-off GPA calculations, Taqdeer is built to treat academic records as structured data, allowing for precise calculation, historical tracking, and future projections.

This document explains the core concepts, data structures, and mathematical models that drive the utility.

## The Design Philosophy

The core problem with standard GPA calculators is that they often obscure the relationship between a **Letter Grade** (human-readable representation) and **Quality Points** (computational representation).

Taqdeer bridges this gap by separating the data (Student Courses) from the evaluation logic (Grade Map). This separation allows the system to be adaptable to different grading scales (via `config.edn`) without altering the core calculation engine.

## Mathematical Models

### Semester GPA (SGPA)
Taqdeer calculates the SGPA using the standard weighted mean formula. The weight is determined by the credit hours of the course.

$$\text{GPA} = \frac{ \sum (\text{Quality Points} \times \text{Credit Hours}) }{ \sum \text{Total Credit Hours} }$$

Where:

- **Quality Points**: The numerical value assigned to a letter grade (e.g., A+ = 4.0) defined in the configuration.
- **Credit Hours**: The weight or "size" of the course.

### Cumulative GPA (CGPA)
Currently, the Cumulative GPA is modeled as the arithmetic mean of the provided semester GPAs.

$$\text{CGPA} = \frac{ \sum \text{Semester GPAs} }{ \text{Count of Semesters} }$$

## Data Structures

To use Taqdeer effectively, it is helpful to understand how it views data.

### 1\. The Grade Map (`grade-scale`)

This is the "truth" source for the system, loaded dynamically from `config.edn`. It acts as a lookup table that converts keywords into floating-point numbers.

| Key | Grade | Points |
| :--- | :--- | :--- |
| `:A+` | A+ | 4.0 |
| `:A` | A | 3.7 |
| `:B` | B | 3.0 |
| `:F` | F | 0.0 |

### 2\. The Course Record

Courses are stored as a map of maps. The outer key acts as the unique identifier (Course Code), while the inner map contains the variable data.

**Structure:**

```clojure
{:COURSE_CODE {:grade "String" :hours Integer}}
```

## Logic Flow

When the `sem-gpa` function processes a semester:

- **Configuration Load:** The system injects the grade-scale from the configuration file.
- **Normalization:** It extracts the values from the course map.
- **Transformation:** It converts the string input (`"A"`) into a keyword (`:A`) to query the Grade Scale.
- **Aggregation:** It accumulates total weighted points and total credit hours.
- **Calculation:** It divides the points by hours to return a double-precision GPA.

### Implementation Reference

The core logic is encapsulated functionally. Note that sem-gpa now requires the grade-scale as an argument to maintain purity.

```clojure
(defn sem-gpa 
  "Calculate one semester GPA"
  [sem-courses grade-scale]
  (let [total-points (->> sem-courses
                          (vals)
                          (map (fn [{:keys [grade hours]}]
                                 (* hours (grade->points grade grade-scale))))
                          (reduce +))
        total-hours (->> sem-courses
                         (vals)
                         (map (fn [{:keys [hours]}] hours))
                         (reduce +))]
    (double (/ total-points total-hours))))
```
