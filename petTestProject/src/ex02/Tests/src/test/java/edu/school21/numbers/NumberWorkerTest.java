package edu.school21.numbers;

import org.testng.annotations.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NumberWorkerTest {

    private final NumberWorker numberWorker = new NumberWorker();

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 11})
    void isPrimeForPrimes(int number) {
        assertTrue(numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 8, 9, 10})
    void isPrimeForNotPrimes(int number) {
        assertFalse(numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, -2})
    void isPrimeForIncorrectNumbers(int number) {
        try {
            numberWorker.isPrime(number);
        } catch (NumberWorker.IllegalNumberException e) {
            assertEquals("Number must be greater than 1", e.getMessage());
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    void digitsSum(int number, int expectedSum) {
        assertEquals(expectedSum, numberWorker.digitsSum(number));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data_fails.csv")
    void digitsSumForIncorrectNumbers(int number) {
        try {
            numberWorker.digitsSum(number);
        } catch (NumberWorker.IllegalNumberException e) {
            assertEquals("Number must be non-negative", e.getMessage());
        }
    }
}