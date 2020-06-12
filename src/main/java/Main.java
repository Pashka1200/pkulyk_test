import entity.Matrix;
import exception.MatrixException;
import service.MatrixServiceParallel;
import service.MatrixServiceSequential;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Please input a range for square matrices");
        int num = 0;
        Scanner in = new Scanner(System.in);
        try {
            num = in.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("'" + in.next() + "' is not a number! (-_-)");
            return;
        }

        int size = num;

        System.out.println("Generating 2 matrices " + size + "x" + size + "...");

        Matrix m1 = new Matrix(size);
        m1.init();
        if (size <= 10) {
            System.out.println("Matrix A: ");
            m1.print();
        }

        System.out.println();

        Matrix m2 = new Matrix(size);
        m2.init();
        if (size <= 10) {
            System.out.println("Matrix B: ");
            m2.print();
        }

        System.out.println();

        try {
            System.out.println("Multiplying (sequential)...");
            long start = System.currentTimeMillis();
            Matrix m3 = MatrixServiceSequential.multiply(m1, m2);
            long end = System.currentTimeMillis();

            if (size <= 10) {
                m3.print();
            }
            System.out.println("time is : " + (end - start) + " millis \n");
        } catch (MatrixException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Multiplying (parallel)...");
            long start = System.currentTimeMillis();
            Matrix m4 = MatrixServiceParallel.multiply(m1, m2);
            long end = System.currentTimeMillis();

            if (size <= 10) {
                m4.print();
            }
            System.out.println("time is : " + (end - start) + " millis \n");
        } catch (MatrixException e) {
            e.printStackTrace();
        }
    }
}
