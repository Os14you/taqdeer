# Introduction to Taqdeer (تقدير)

**Taqdeer** is a Clojure-based utility designed to model and calculate academic performance. While many tools exist to perform one-off GPA calculations, Taqdeer is built to treat academic records as structured data, allowing for precise calculation, historical tracking, and future projections.

This document explains the core concepts, data structures, and mathematical models that drive the utility.

## The Design Philosophy

The core problem with standard GPA calculators is that they often obscure the relationship between a **Letter Grade** (human-readable representation) and **Quality Points** (computational representation).

Taqdeer bridges this gap by separating the data (Student Courses) from the evaluation logic (Grade Map). This separation allows the system to be adaptable to different grading scales without altering the core calculation engine.

## Mathematical Model

Taqdeer calculates the **Semester GPA (SGPA)** using the standard weighted mean formula. The weight is determined by the credit hours of the course.

$$\text{GPA} = \frac{ \sum (\text{Quality Points} \times \text{Credit Hours}) }{ \sum \text{Total Credit Hours} }$$

Where:

- **Quality Points**: The numerical value assigned to a letter grade (e.g., A+ = 4.0).
- **Credit Hours**: The weight or "size" of the course.

## Data Structures

To use Taqdeer effectively, it is helpful to understand how it views data.

### 1\. The Grade Map (`grade-map`)

This is the immutable "truth" source for the system. It acts as a lookup table that converts keywords into floating-point numbers.

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

**Example Data:**

```clojure
{:AI01 {:grade "A"  :hours 2}
 :CS02 {:grade "C+" :hours 3}}
```

## Logic Flow

When the `sem-gpa` function processes a semester:

1. **Normalization**: It extracts the values from the course map, ignoring the keys (course codes).
2. **Transformation**: It converts the string input (`"A"`) into a keyword (`:A`) to query the Grade Map.
3. **Aggregation**: It performs two simultaneous reductions:
   - Accumulating total weighted points.
   - Accumulating total credit hours.
4. **Calculation**: It divides the points by hours to return a double-precision GPA.

### Implementation Reference

The core logic is encapsulated functionally to ensure no global side effects occur during calculation.

```clojure
(defn sem-gpa 
  [sem-courses]
  (let [total-points (->> sem-courses
                          (vals)
                          (map (fn [{:keys [grade hours]}]
                                 (* hours (get grade-map (keyword grade)))))
                          (reduce +))
        total-hours (->> sem-courses
                         (vals)
                         (map (fn [{:keys [hours]}] hours))
                         (reduce +))]
    (/ total-points total-hours)))
```
