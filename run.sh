#!/bin/bash

# Compile Java files

# Find the main Java file
main_file=$(grep -rl --include \*.java "public static void main(String\[\] args)" src/)

# Extract package and class name
package=$(grep -o 'package [^;]*' "$main_file" | cut -d' ' -f2)
main_class=$(grep -o 'public class [^ ]*' "$main_file" | cut -d' ' -f3)

# Run the main Java class
find src -name "*.java" -type f -exec javac -d bin {} +
java -cp bin $package.$main_class

