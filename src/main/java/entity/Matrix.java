package entity;

public class Matrix {

    private Integer size;
    private int[][] matrix;

    public Matrix(int size) {
        this.size = size;
        this.matrix = new int [size][size];
    }

    public void init(){
        for (int i = 0; i<matrix.length; i++) {
            for (int j = 0; j<matrix.length; j++){
                matrix[i][j] = (int) (Math.random() * 100 % 2);
            }
        }
    }

    public void print(){
        for (int i = 0; i<matrix.length; i++) {
            for (int j = 0; j<matrix.length; j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public Integer getSize(){
        return this.size;
    }

    public Matrix initFromArray(int array[][]){
        this.matrix = array;
        return this;
    }

    public int[][] getArray(){
        return this.matrix.length>0 ? this.matrix : new int [0][0];
    }

}
