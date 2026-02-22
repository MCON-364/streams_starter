package edu.touro.las.mcon364.func_prog.demo;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Demo {
    public void demoConsumer() {
        /**
         * Consumer is a functional interface that represents an operation that takes a single input argument and returns no result.
         * It is often used for operations that have side effects, such as printing to the console or modifying an object.
         * In this example, we create a Consumer that takes a String and prints a greeting message to the console. We then call the accept method to execute the Consumer with the input "World".
         * Output:
         * Hello, World
         */
        Consumer<String> print = s -> System.out.println("Hello, " + s);
        print.accept("World");
    }

    public void demoConsumerWithAddThenPrint() {
        /**
         * In this example, we create a Consumer that adds a string to a list and then prints the list. We use the andThen method to chain two Consumers together: one that adds the string to the list and another that prints the list.
         * Output:
         * [Hello]
         * [Hello, World]
         */
        List<String> list = new java.util.ArrayList<>();
        Consumer<String> add = s -> list.add(s);
        Consumer<String> addThenPrint = add.andThen(s -> System.out.println(list));
        addThenPrint.accept("Hello");
        addThenPrint.accept("World");
    }

    public void demoPredicate() {
        /**
         * Predicate is a functional interface that represents a boolean-valued function of one argument. It is often used for filtering or testing conditions.
         * In this example, we create a Predicate that checks if a given string has a length greater than 5. We then call the test method to evaluate the Predicate with the input "Hello" and "Hello, World".
         * Output:
         * false
         * true
         */
        Predicate<String> isLongerThan5 = s -> s.length() > 5;
        System.out.println(isLongerThan5.test("Hello"));
        System.out.println(isLongerThan5.test("Hello, World"));
    }

    public void demoNotPredicate() {
        /**
         * The not method is a static method in the Predicate interface that returns a predicate that represents the logical negation of the original predicate.
         * In this example, we create a Predicate that checks if a given string has a length greater than 5 and
         * then use the not method to create a new Predicate that checks if the string does not have a length greater than 5.
         * Output:
         * true
         * false
         */
        Predicate<String> isLongerThan5 = s -> s.length() > 5;
        Predicate<String> isNotLongerThan5 = Predicate.not(isLongerThan5);
        System.out.println(isNotLongerThan5.test("Hello"));
        System.out.println(isNotLongerThan5.test("Hello, World"));
    }

    public void demoNegatePredicate() {
        /**
         * The negate method is a default method in the Predicate interface that returns a predicate that represents the logical negation of the original predicate.
         * In this example, we create a Predicate that checks if a given string has a length greater than 5 and then use the negate method to create a new Predicate that checks if the string does not have a length greater than 5.
         * Output:
         * true
         * false
         */
        Predicate<String> isLongerThan5 = s -> s.length() > 5;
        Predicate<String> isNotLongerThan5 = isLongerThan5.negate();
        System.out.println(isNotLongerThan5.test("Hello"));
        System.out.println(isNotLongerThan5.test("Hello, World"));
    }

    public void demoAndPredicate() {
        /**
         * The and method is a default method in the Predicate interface that returns a composed predicate that represents a short-circuiting logical AND of this predicate and another.
         * In this example, we create two Predicates: one that checks if a string has a length greater than 5 and another that checks if the string contains the letter 'o'. We then use the and method to create a new Predicate that checks if both conditions are true.
         * Output:
         * false
         * true
         */
        Predicate<String> isLongerThan5 = s -> s.length() > 5;
        Predicate<String> containsO = s -> s.contains("o");
        Predicate<String> isLongerThan5AndContainsO = isLongerThan5.and(containsO);
        System.out.println(isLongerThan5AndContainsO.test("Hello"));
        System.out.println(isLongerThan5AndContainsO.test("Hello, World"));
    }

    public void demoOrPredicate() {
        /**
         * The or method is a default method in the Predicate interface that returns a composed predicate that represents a short-circuiting logical OR of this predicate and another.
         * In this example, we create two Predicates: one that checks if a string has a length greater than 5 and another that checks if the string contains the letter 'o'. We then use the or method to create a new Predicate that checks if either condition is true.
         * Output:
         * true
         * true
         */
        Predicate<String> isLongerThan5 = s -> s.length() > 5;
        Predicate<String> containsO = s -> s.contains("o");
        Predicate<String> isLongerThan5OrContainsO = isLongerThan5.or(containsO);
        System.out.println(isLongerThan5OrContainsO.test("Hello"));
        System.out.println(isLongerThan5OrContainsO.test("Hi"));
    }

    public void demoFunction() {
        /**
         * Function is a functional interface that represents a function that takes one argument and produces a result. It is often used for transformations or mappings.
         * In this example, we create a Function that takes a string and returns its length. We then call the apply method to execute the Function with the input "Hello".
         * Output:
         * 5
         */
        Function<String, Integer> lengthFunction = s -> s.length();
        System.out.println(lengthFunction.apply("Hello"));
    }

    public void demoFunctionWithAndThen() {
        /**
         * The andThen method is a default method in the Function interface that returns a composed function that first applies this function to its input, and then applies the after function to the result.
         * In this example, we create a Function that takes a string and returns its length, and another Function that takes an integer and returns its square. We then use the andThen method to create a new Function that first calculates the length of the string and then squares it.
         * Output:
         * 25
         */
        Function<String, Integer> lengthFunction = s -> s.length();
        Function<Integer, Integer> squareFunction = x -> x * x;
        Function<String, Integer> lengthThenSquare = lengthFunction.andThen(squareFunction);
        System.out.println(lengthThenSquare.apply("Hello"));
    }

    public void demoFunctionWithCompose() {
        /**
         * The compose method is a default method in the Function interface that returns a composed function
         * that first applies the before function to its input, and then applies this function to the result.
         * In this example, we create a Function that takes a string and returns its length,
         * and another Function that takes a string and trims it.
         * We then use the compose method to create a new Function that first trims the string and then
         * calculates its length.
         * Output:
         * 5
         */
        Function<String, String> trim = String::trim;
        Function<String, String> upper = String::toUpperCase;
        Function<String, Integer> length = String::length;

        // andThen: left first, then right
        Function<String, Integer> pipeline1 = trim.andThen(upper).andThen(length);
        System.out.println(pipeline1.apply("  java  ")); // 4

        // compose: right first, then left
        Function<String, Integer> pipeline2 = length.compose(trim);
        System.out.println(pipeline2.apply("  hello ")); // 5

    }

    public Supplier<String> demoSupplier() {
        /**
         * Supplier is a functional interface that represents a supplier of results. It does not take any arguments and returns a result.
         * It is often used for lazy generation of values or for providing default values.
         * In this example, we create a Supplier that returns a greeting message.
         * We then call the get method to retrieve the value from the Supplier.
         * Output:
         * Hello, World!
         */
        Supplier<String> greetingSupplier = () -> "Hello, World!";
        return greetingSupplier;
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        demo.demoConsumer();
        demo.demoConsumerWithAddThenPrint();
        demo.demoPredicate();
        demo.demoNotPredicate();
        demo.demoNegatePredicate();
        demo.demoAndPredicate();
        demo.demoOrPredicate();
        demo.demoFunction();
        demo.demoFunctionWithAndThen();
        demo.demoFunctionWithCompose();
        System.out.println("=== Supplier Demo ===");
        Supplier<String> supplier = demo.demoSupplier();
        System.out.println(supplier.get());
        System.out.println("=== End of Supplier Demo ===");
    }
}

