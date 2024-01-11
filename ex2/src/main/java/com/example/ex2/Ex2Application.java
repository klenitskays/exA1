package com.example.ex2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class Ex2Application {


    public static void main(String[] args) {
        int n = 20; // максимальный  факториал без переполнения
        double u;
        // u = sumFactorial(n)/ factorial(n);
        u = (1 / factorial(n)) * sumFactorial(n);
        System.out.format("%.6f", u);

    }

    public static double factorial(int n) {
        long b = 1;
        for (int i = 1; i <= n; i++) {
            b *= i;
        }
        return b;
    }

    public static double sumFactorial(int n) {
        long b = 0;
        for (int i = 1; i <= n; i++) {
            b += factorial(i);
        }
        return b;
    }
}