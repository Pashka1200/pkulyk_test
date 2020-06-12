package service;

import entity.Matrix;
import exception.MatrixException;

import java.util.Arrays;
import java.util.stream.IntStream;


/*
 * Sequential time is 29372749 millis (8.15909694 hours) for matrix 10000x10000
 * 53565 millis for 1000x1000
 * 431 millis for 100x100
 * 45 millis for 10x10
 */
public class MatrixServiceSequential {

    /*
     * @param A - multiplicand
     * @param B - multiplier
     * @    return result of multiplication of two 2D matrix
     * @throws MatrixException
     */
    public static Matrix multiply(Matrix A, Matrix B) throws MatrixException {
        if (A.getSize().compareTo(B.getSize()) != 0)
            throw new MatrixException("These matrix have a different sizes", A.getSize(), B.getSize());

        Matrix C = new Matrix(A.getSize());
        int result[][] = new int[A.getSize()][B.getSize()];

        for (int i = 0; i < A.getSize(); i++) {
            int[] line = getLineElements(A.getArray(), i, A.getSize());

            for (int j = 0; j < B.getSize(); j++) {
                int[] column = getColumnElements(B.getArray(), j);
                result[i][j] = IntStream.range(0, A.getSize()).map(k -> line[k] * column[k]).sum() % 2;
            }
        }

        C.initFromArray(result);

        return C;
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
