# Configuration Guide

Taqdeer uses Extensible Data Notation (EDN) for configuration, allowing users to define their university's specific grading standards without modifying the source code.

The configuration is loaded from a file named `config.edn` located in the project root.

## Structure

The configuration file is a hash map containing the following keys:

### `:university`
*Type: String*
A human-readable name for the institution.

### `:grade-scale`
*Type: Map {Keyword Number}*
A mapping of letter grades to their corresponding quality points.

* **Keys**: Must be valid Clojure keywords (e.g., `:A+`, `:C`).
* **Values**: Must be numbers (integers or floats).

## Example

```clojure
{:university "Kafr Elshiekh University"
 :grade-scale {:A+ 4 
               :A 3.7 
               :B+ 3.3 
               :B 3 
               :C+ 2.7 
               :C 2.4 
               :D+ 2 
               :D 1.7 
               :F 0}}
```