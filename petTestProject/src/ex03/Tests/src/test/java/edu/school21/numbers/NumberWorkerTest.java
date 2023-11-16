package edu.school21.numbers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class NumberWorkerTest {

    @ParameterizedTest
    @ValueSource(ints = { 2, 3, 5 }) // Prime numbers
    void isPrimeForPrimes(int number) throws IllegalNumberException {
        assertTrue(NumberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = { 4, 6, 8 }) // Composite numbers
    void isPrimeForNotPrimes(int number) throws IllegalNumberException {
        assertFalse(NumberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = { -1, 0, 1 }) // Incorrect numbers
    void isPrimeForIncorrectNumbers(int number) {
        assertThrows(IllegalNumberException.class, () -> NumberWorker.isPrime(number));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv") // Read from data.csv file
    void digitsSumCorrectData(int number, int expectedSum) {
        assertEquals(expectedSum, NumberWorker.digitsSum(number));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data_fails.csv") // Read from data_fails.csv file
    void digitsSumIncorrectData(int number, int expectedSum) {
        assertNotEquals(expectedSum, NumberWorker.digitsSum(number));
    }
}
