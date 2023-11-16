package edu.school21.numbers;

public class NumberWorker {

    public static boolean isPrime(int number) throws IllegalNumberException {
        if (number < 2) {
            throw new IllegalNumberException("IllegalArgument");
        } else {
            boolean isPrime = true;
            for (int i = 2; i * i <= number; i++) {
                if (number % i == 0) {
                    isPrime = false;
                    break;
                }
            }
            return isPrime;
        }
    }

    public static int digitsSum(int number) {
        int sum = 0;
        while (number > 0) {
            sum += number % 10;
            number = number / 10;
        }
        return sum;
    }

//    public static void main(String[] args) throws IllegalNumberException {
//        System.out.println(isPrime(8));
//    }
}
