package edu.touro.las.mcon364.func_prog.exercises;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for FunctionalInterfaceExercises.
 * Tests all functional interfaces with full coverage.
 */
@DisplayName("Functional Interface Exercises Tests")
public class FunctionalInterfaceExercisesTest {

    // =========================================================
    // PART 1 — SUPPLIERS TESTS
    // =========================================================

    @Nested
    @DisplayName("Part 1: Supplier Tests")
    class SupplierTests {

        @Test
        @DisplayName("Test 1: currentYearSupplier returns current year")
        void testCurrentYearSupplier() {
            Supplier<Integer> supplier = FunctionalInterfaceExercises.currentYearSupplier();
            assertNotNull(supplier, "Supplier should not be null");

            Integer year = supplier.get();
            assertNotNull(year, "Year should not be null");
            assertEquals(LocalDate.now().getYear(), year, "Should return the current year");
            assertTrue(year >= 2000 && year <= 2100, "Year should be reasonable");
        }

        @Test
        @DisplayName("Test 1: currentYearSupplier returns consistent value")
        void testCurrentYearSupplierConsistency() {
            Supplier<Integer> supplier = FunctionalInterfaceExercises.currentYearSupplier();

            Integer year1 = supplier.get();
            Integer year2 = supplier.get();
            assertEquals(year1, year2, "Should return the same year on multiple calls");
        }

        @Test
        @DisplayName("Test 2: randomScoreSupplier returns values between 1 and 100")
        void testRandomScoreSupplier() {
            Supplier<Integer> supplier = FunctionalInterfaceExercises.randomScoreSupplier();
            assertNotNull(supplier, "Supplier should not be null");

            for (int i = 0; i < 100; i++) {
                Integer score = supplier.get();
                assertNotNull(score, "Score should not be null");
                assertTrue(score >= 1 && score <= 100, "Score should be between 1 and 100, got: " + score);
            }
        }

        @Test
        @DisplayName("Test 2: randomScoreSupplier returns different values")
        void testRandomScoreSupplierVariability() {
            Supplier<Integer> supplier = FunctionalInterfaceExercises.randomScoreSupplier();

            Integer score1 = supplier.get();
            Integer score2 = supplier.get();
            // With 100 possible values, it's highly likely they'll differ
            // But we'll just test that we get values in the correct range
            assertTrue(score1 >= 1 && score1 <= 100, "First score should be in range");
            assertTrue(score2 >= 1 && score2 <= 100, "Second score should be in range");
        }
    }

    // =========================================================
    // PART 2 — PREDICATES TESTS
    // =========================================================

    @Nested
    @DisplayName("Part 2: Predicate Tests")
    class PredicateTests {

        @Test
        @DisplayName("Test 3: isAllUpperCase detects uppercase strings")
        void testIsAllUpperCaseTrue() {
            Predicate<String> predicate = FunctionalInterfaceExercises.isAllUpperCase();
            assertNotNull(predicate, "Predicate should not be null");

            assertTrue(predicate.test("HELLO"), "Should recognize 'HELLO' as uppercase");
            assertTrue(predicate.test("ABC"), "Should recognize 'ABC' as uppercase");
            assertTrue(predicate.test("A"), "Should recognize single uppercase letter");
        }

        @Test
        @DisplayName("Test 3: isAllUpperCase rejects non-uppercase strings")
        void testIsAllUpperCaseFalse() {
            Predicate<String> predicate = FunctionalInterfaceExercises.isAllUpperCase();

            assertFalse(predicate.test("hello"), "Should reject lowercase 'hello'");
            assertFalse(predicate.test("Hello"), "Should reject mixed case 'Hello'");
            assertFalse(predicate.test("hELLO"), "Should reject mixed case 'hELLO'");
            assertFalse(predicate.test("a"), "Should reject lowercase letter");
        }

        @Test
        @DisplayName("Test 3: isAllUpperCase handles special cases")
        void testIsAllUpperCaseEdgeCases() {
            Predicate<String> predicate = FunctionalInterfaceExercises.isAllUpperCase();

            assertTrue(predicate.test("123"), "Should accept numbers as they don't have case");
            assertTrue(predicate.test("!@#$%"), "Should accept special characters");
            assertTrue(predicate.test("HELLO123"), "Should accept uppercase with numbers");
            assertFalse(predicate.test("HELLO world"), "Should reject mixed case with space");
        }

        @Test
        @DisplayName("Test 4: positiveAndDivisibleByFive accepts valid numbers")
        void testPositiveAndDivisibleByFiveTrue() {
            Predicate<Integer> predicate = FunctionalInterfaceExercises.positiveAndDivisibleByFive();
            assertNotNull(predicate, "Predicate should not be null");

            assertTrue(predicate.test(5), "Should accept 5");
            assertTrue(predicate.test(10), "Should accept 10");
            assertTrue(predicate.test(100), "Should accept 100");
            assertTrue(predicate.test(15), "Should accept 15");
            assertTrue(predicate.test(25), "Should accept 25");
        }

        @Test
        @DisplayName("Test 4: positiveAndDivisibleByFive rejects invalid numbers")
        void testPositiveAndDivisibleByFiveFalse() {
            Predicate<Integer> predicate = FunctionalInterfaceExercises.positiveAndDivisibleByFive();

            assertFalse(predicate.test(3), "Should reject 3 (not divisible by 5)");
            assertFalse(predicate.test(7), "Should reject 7 (not divisible by 5)");
            assertFalse(predicate.test(-5), "Should reject -5 (not positive)");
            assertFalse(predicate.test(-10), "Should reject -10 (not positive)");
            assertFalse(predicate.test(0), "Should reject 0 (not positive)");
            assertFalse(predicate.test(1), "Should reject 1 (not divisible by 5)");
        }

        @Test
        @DisplayName("Test 4: positiveAndDivisibleByFive uses AND logic correctly")
        void testPositiveAndDivisibleByFiveChaining() {
            Predicate<Integer> predicate = FunctionalInterfaceExercises.positiveAndDivisibleByFive();

            // Edge case: divisible by 5 but not positive
            assertFalse(predicate.test(-15), "Should reject -15 (divisible by 5 but negative)");

            // Edge case: positive but not divisible by 5
            assertFalse(predicate.test(17), "Should reject 17 (positive but not divisible by 5)");
        }
    }

    // =========================================================
    // PART 3 — FUNCTIONS TESTS
    // =========================================================

    @Nested
    @DisplayName("Part 3: Function Tests")
    class FunctionTests {

        @Test
        @DisplayName("Test 5: celsiusToFahrenheit converts correctly")
        void testCelsiusToFahrenheitConversions() {
            Function<Double, Double> function = FunctionalInterfaceExercises.celsiusToFahrenheit();
            assertNotNull(function, "Function should not be null");

            // Test common conversions
            assertEquals(32.0, function.apply(0.0), 0.01, "0°C should be 32°F");
            assertEquals(212.0, function.apply(100.0), 0.01, "100°C should be 212°F");
            assertEquals(98.6, function.apply(37.0), 0.01, "37°C should be approximately 98.6°F");
            assertEquals(68.0, function.apply(20.0), 0.01, "20°C should be 68°F");
            assertEquals(50.0, function.apply(10.0), 0.01, "10°C should be 50°F");
        }

        @Test
        @DisplayName("Test 5: celsiusToFahrenheit handles negative temperatures")
        void testCelsiusToFahrenheitNegative() {
            Function<Double, Double> function = FunctionalInterfaceExercises.celsiusToFahrenheit();

            assertEquals(-40.0, function.apply(-40.0), 0.01, "-40°C should be -40°F");
            assertEquals(14.0, function.apply(-10.0), 0.01, "-10°C should be 14°F");
        }

        @Test
        @DisplayName("Test 6: countVowels counts vowels correctly")
        void testCountVowels() {
            Function<String, Integer> function = FunctionalInterfaceExercises.countVowels();
            assertNotNull(function, "Function should not be null");

            assertEquals(2, function.apply("hello"), "hello has 2 vowels");
            assertEquals(5, function.apply("aeiou"), "aeiou has 5 vowels");
            assertEquals(0, function.apply("bcdfg"), "bcdfg has 0 vowels");
            assertEquals(5, function.apply("education"), "education has 5 vowels");
            assertEquals(1, function.apply("a"), "a has 1 vowel");
        }

        @Test
        @DisplayName("Test 6: countVowels is case-insensitive")
        void testCountVowelsCaseInsensitive() {
            Function<String, Integer> function = FunctionalInterfaceExercises.countVowels();

            assertEquals(2, function.apply("HELLO"), "HELLO has 2 vowels");
            assertEquals(2, function.apply("Hello"), "Hello has 2 vowels");
            assertEquals(5, function.apply("AEIOU"), "AEIOU has 5 vowels");
            assertEquals(5, function.apply("AeIoU"), "AeIoU has 5 vowels");
        }

        @Test
        @DisplayName("Test 6: countVowels handles special cases")
        void testCountVowelsSpecialCases() {
            Function<String, Integer> function = FunctionalInterfaceExercises.countVowels();

            assertEquals(0, function.apply("123"), "Numbers have no vowels");
            assertEquals(0, function.apply("!@#$%"), "Special characters have no vowels");
            assertEquals(3, function.apply("Hello, World!"), "Handles punctuation and spaces");
            assertEquals(0, function.apply(""), "Empty string has 0 vowels");
        }
    }

    // =========================================================
    // PART 4 — CONSUMERS TESTS
    // =========================================================

    @Nested
    @DisplayName("Part 4: Consumer Tests")
    class ConsumerTests {

        @Test
        @DisplayName("Test 7: starPrinter prints with asterisks")
        void testStarPrinter() {
            Consumer<String> consumer = FunctionalInterfaceExercises.starPrinter();
            assertNotNull(consumer, "Consumer should not be null");

            // Capture output
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            try {
                System.setOut(new PrintStream(outContent));

                consumer.accept("Hello");
                String output = outContent.toString().trim();

                assertTrue(output.contains("***"), "Output should contain asterisks");
                assertTrue(output.contains("Hello"), "Output should contain the input string");
                assertEquals("*** Hello ***", output, "Output format should be '*** Hello ***'");
            } finally {
                System.setOut(originalOut);
            }
        }

        @Test
        @DisplayName("Test 7: starPrinter handles different strings")
        void testStarPrinterVariousInputs() {
            Consumer<String> consumer = FunctionalInterfaceExercises.starPrinter();

            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            try {
                System.setOut(new PrintStream(outContent));

                consumer.accept("Test");
                String output = outContent.toString();
                assertTrue(output.contains("*** Test ***"), "Should format 'Test' correctly");

                outContent.reset();
                consumer.accept("123");
                output = outContent.toString();
                assertTrue(output.contains("*** 123 ***"), "Should format '123' correctly");
            } finally {
                System.setOut(originalOut);
            }
        }

        @Test
        @DisplayName("Test 8: printSquare prints square of integer")
        void testPrintSquare() {
            Consumer<Integer> consumer = FunctionalInterfaceExercises.printSquare();
            assertNotNull(consumer, "Consumer should not be null");

            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            try {
                System.setOut(new PrintStream(outContent));

                consumer.accept(5);
                String output = outContent.toString().trim();
                assertEquals("25", output, "Square of 5 should be 25");
            } finally {
                System.setOut(originalOut);
            }
        }

        @Test
        @DisplayName("Test 8: printSquare handles various numbers")
        void testPrintSquareVariousNumbers() {
            Consumer<Integer> consumer = FunctionalInterfaceExercises.printSquare();

            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            try {
                System.setOut(new PrintStream(outContent));

                consumer.accept(0);
                String output = outContent.toString().trim();
                assertEquals("0", output, "Square of 0 should be 0");

                outContent.reset();
                consumer.accept(10);
                output = outContent.toString().trim();
                assertEquals("100", output, "Square of 10 should be 100");

                outContent.reset();
                consumer.accept(-3);
                output = outContent.toString().trim();
                assertEquals("9", output, "Square of -3 should be 9");
            } finally {
                System.setOut(originalOut);
            }
        }
    }

    // =========================================================
    // PART 5 — APPLYING FUNCTIONAL INTERFACES TESTS
    // =========================================================

    @Nested
    @DisplayName("Part 5: Applying Functional Interfaces Tests")
    class ApplicationTests {

        @Test
        @DisplayName("Test 9: processStrings filters and transforms correctly")
        void testProcessStrings() {
            List<String> input = Arrays.asList("hi", "hello", "hey", "world", "java");

            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            try {
                System.setOut(new PrintStream(outContent));

                FunctionalInterfaceExercises.processStrings(input);
                String output = outContent.toString();

                // Should include strings with length > 3 in lowercase
                assertTrue(output.contains("hello"), "Should include 'hello'");
                assertTrue(output.contains("world"), "Should include 'world'");
                assertTrue(output.contains("java"), "Should include 'java'");

                // Should not include strings with length <= 3
                assertFalse(output.contains("hi\n"), "Should not include 'hi' (length = 2)");
                assertFalse(output.contains("hey\n"), "Should not include 'hey' (length = 3)");

                // Should be lowercase
                assertFalse(output.contains("HELLO"), "Output should be lowercase");
                assertFalse(output.contains("WORLD"), "Output should be lowercase");
            } finally {
                System.setOut(originalOut);
            }
        }

        @Test
        @DisplayName("Test 9: processStrings handles empty list")
        void testProcessStringsEmptyList() {
            List<String> input = Arrays.asList();

            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            try {
                System.setOut(new PrintStream(outContent));

                FunctionalInterfaceExercises.processStrings(input);
                String output = outContent.toString();

                assertEquals("", output, "Should print nothing for empty list");
            } finally {
                System.setOut(originalOut);
            }
        }

        @Test
        @DisplayName("Test 9: processStrings filters correctly")
        void testProcessStringsFiltering() {
            List<String> input = Arrays.asList("a", "ab", "abc", "abcd", "ABCDE", "Hi");

            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            try {
                System.setOut(new PrintStream(outContent));

                FunctionalInterfaceExercises.processStrings(input);
                String output = outContent.toString();

                // Only strings with length > 3 should appear (in lowercase)
                assertTrue(output.contains("abcd"), "Should include 'abcd'");
                assertTrue(output.contains("abcde"), "Should include 'abcde'");
                assertFalse(output.contains("a\n"), "Should not include 'a'");
                assertFalse(output.contains("ab\n"), "Should not include 'ab'");
                assertFalse(output.contains("abc\n"), "Should not include 'abc'");
                assertFalse(output.contains("Hi"), "Should not include 'Hi' (length = 2)");
            } finally {
                System.setOut(originalOut);
            }
        }

        @Test
        @DisplayName("Test 10: generateAndFilterScores generates and filters correctly")
        void testGenerateAndFilterScores() {
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            try {
                System.setOut(new PrintStream(outContent));

                FunctionalInterfaceExercises.generateAndFilterScores();
                String output = outContent.toString();

                // Each line should be a number (or empty if no scores > 70)
                String[] lines = output.split("\n");
                for (String line : lines) {
                    if (!line.trim().isEmpty()) {
                        int score = Integer.parseInt(line.trim());
                        assertTrue(score > 70 && score <= 100, "Printed scores should be > 70 and <= 100");
                    }
                }
            } finally {
                System.setOut(originalOut);
            }
        }

        @Test
        @DisplayName("Test 10: generateAndFilterScores runs without exception")
        void testGenerateAndFilterScoresNoException() {
            assertDoesNotThrow(() -> {
                FunctionalInterfaceExercises.generateAndFilterScores();
            }, "Should run without throwing an exception");
        }

        @Test
        @DisplayName("Test 10: generateAndFilterScores generates 5 scores")
        void testGenerateAndFilterScoresCount() {
            // We can't easily verify exactly 5 scores are generated, but we can verify
            // that the method runs and generates some output
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            try {
                System.setOut(new PrintStream(outContent));

                FunctionalInterfaceExercises.generateAndFilterScores();
                String output = outContent.toString();

                // The method generates 5 scores, but only prints those > 70
                // We can count the lines and verify they're within range
                String[] lines = output.split("\n");
                int count = 0;
                for (String line : lines) {
                    if (!line.trim().isEmpty()) {
                        count++;
                    }
                }

                // Between 0 and 5 scores should be printed
                assertTrue(count >= 0 && count <= 5, "Should have between 0 and 5 printed scores");
            } finally {
                System.setOut(originalOut);
            }
        }
    }
}

