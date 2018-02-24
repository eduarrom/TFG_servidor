package com.rodrigo.TFG_server.Negocio.TestParametrizados;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestParamTest {

    final static Logger log = LoggerFactory.getLogger(TestParamTest.class);


    @ParameterizedTest
    @ValueSource(strings = { "Hello", "JUnit" })
    void parameterizedTest(String word) {
        log.info("Word: " + word);
        assertNotNull(word);
    }



    @ParameterizedTest(name = "run #{index} with [{arguments}]")
    @ValueSource(strings = { "Hello", "JUnit" })
    void withValueSource(String word) {
        log.info("Word: " + word);
    }



    @DisplayName("Roman numeral")
    @ParameterizedTest(name = "\"{0}\" should be {1}")
    @CsvSource({ "I, 1", "II, 2", "V, 5"})
    void withNiceName(String word, int number) {

        log.info("Word: " + word + "  --  Number: " + number);
    }


    @ParameterizedTest
    @ValueSource(strings = { "Hello", "JUnit" })
    void withOtherParams(String word, TestInfo info, TestReporter reporter) {
        reporter.publishEntry(info.getDisplayName(), "Word: " + word);
    }


    @Params
    void testMetaAnnotation(String s) {
        log.info("s: " + s);
    }

    @Retention(RetentionPolicy.RUNTIME)
    @ParameterizedTest(name = "Elaborate name listing all {arguments}")
    @ValueSource(strings = { "Hello", "JUnit" })
    @interface Params { }


    @ParameterizedTest
    @ValueSource(longs = { 42, 63 })
    void withValueSource(long number) {
        log.info("number = [" + number + "]");
    }



    @ParameterizedTest
    @EnumSource(TimeUnit.class)
    void withAllEnumValues(TimeUnit unit) {
        // executed once for each time unit
        log.info("unit = [" + unit + "]");
    }

    @ParameterizedTest
    @EnumSource(
            value = TimeUnit.class,
            names = {"NANOSECONDS", "MICROSECONDS"})
    void withSomeEnumValues(TimeUnit unit) {
        log.info("unit = [" + unit + "]");
        // executed once for TimeUnit.NANOSECONDS
        // and once for TimeUnit.MICROSECONDS
    }




    @ParameterizedTest
    @CsvSource({ "Hello, 5", "JUnit 5, 7", "'Hello, JUnit 5!', 15" })
    void withCsvSource(String word, int length) {
        log.info("word = [" + word + "], length = [" + length + "]");
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/word-lengths.csv")
    void withCsvSourceFile(String word, int length) {
        log.info("word = [" + word + "], length = [" + length + "]");
    }


}
