package service;

import entity.Matrix;
import exception.MatrixException;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class MatrixServiceParallel {
    static volatile int result[][];

    public static Matrix multiply(Matrix A, Matrix B) throws MatrixException {
        if (A.getSize().compareTo(B.getSize()) != 0)
            throw new MatrixException("These matrix have a different sizes", A.getSize(), B.getSize());

        result = new int[A.getSize()][B.getSize()];
        Matrix C = new Matrix(A.getSize());


        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < A.getSize(); i++) {
            int[] line = getLineElements(A.getArray(), i, A.getSize());

            for (int j = 0; j < B.getSize(); j++) {
                int finalJ = j;
                int finalI = i;
                executorService.execute(() -> {
                    callBack(A, B, finalI, finalJ, A.getSize());
                });
            }
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        C.initFromArray(result);

        return C;
    }

    private static void callBack(Matrix A, Matrix B, int lineNumber, int columnNumber, int size) {
        int[] line = getLineElements(A.getArray(), lineNumber, size);
        int[] column = getColumnElements(B.getArray(), columnNumber);
        result[lineNumber][columnNumber] = IntStream.range(0, A.getSize()).map(k -> line[k] * column[k]).sum() % 2;
    }

    /*
     * Extract elements regarding to the number of a line from a matrix
     * input - matrix (2D array)
     * line - number of line (e.g. for 5x5 matrix lines: 0,1,2,3,4)
     * qty - a quantity of elements in a line (e.g. for 5x5 matrix qty=5)
     */
    public static int[] getLineElements(int[][] input, int line, int qty) {
        return Arrays.stream(input).flatMapToInt(Arrays::stream).skip(line * qty).limit(qty).toArray();
    }

    /*
     * Extract and collect elements from a specified column
     * input - matrix (2D array)
     * column - number of column to extract (e.g. e.g. for 5x5 matrix columns: 0,1,2,3,4)
     */
    public static int[] getColumnElements(int[][] input, int column) {
        return Arrays.stream(input).parallel().map(array -> array[column]).mapToInt(i -> i).toArray();
    }
}
