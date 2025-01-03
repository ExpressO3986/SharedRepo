package frc.robot.Utilities;

import java.util.Arrays;

/** This class came from a ChatGPT response, it can be used in a variety
 * of advanced calculations, such as sensor fusion with Kalman filter, but
 * not generally necessary.
 */
public class Matrix {
   private final double[][] data;
   private final int rows;
   private final int cols;

   // Constructor
   public Matrix(int rows, int cols) {
      this.rows = rows;
      this.cols = cols;
      this.data = new double[rows][cols];
   }

   public Matrix(double[][] data) {
      this.rows = data.length;
      this.cols = data[0].length;
      this.data = copy(data);
   }

   /** Used to make a deep copy of a generic 1D array. */
   public static double[] copy(double[] data) {
      int rows = data.length;
      double[] copy = new double[rows];
      System.arraycopy(data, 0, copy, 0, rows);
      return copy;
   }
   
   /** Used to make a deep copy of a generic 2D array. */
   public static double[][] copy(double[][] data) {
      int rows = data.length;
      int cols = data[0].length;
      double[][] copy = new double[rows][cols];
      for (int i = 0; i < rows; i++) {
         System.arraycopy(data[i], 0, copy[i], 0, cols);
      }
      return copy;
   }

   public Matrix(double[] data, boolean isSrcASingleRow) {
      if (isSrcASingleRow) {
         this.rows = 1;
         this.cols = data.length;
         this.data = new double[1][cols];
         System.arraycopy(data, 0, this.data[0], 0, cols);
      } else {
         this.rows = data.length;
         this.cols = 1;
         this.data = new double[rows][1];
         for (int i = 0; i < rows; i++) {
            this.data[i][0] = data[i];
         }
      }
   }   
   
   /**
    * Utility function to check if two matrices are approximately equal.
    *
    * @param m1 First matrix
    * @param m2 Second matrix
    * @param epsilon Tolerance for floating point comparison
    * @return true if the matrices are equal within the given tolerance
    */
   public static boolean areEqual(Matrix m1, Matrix m2, double epsilon) {
      if (m1.getRows() != m2.getRows() || m1.getCols() != m2.getCols()) {
         return false;
      }
      for (int i = 0; i < m1.getRows(); i++) {
         for (int j = 0; j < m1.getCols(); j++) {
               if (Math.abs(m1.get(i, j) - m2.get(i, j)) > epsilon) {
                  return false;
               }
         }
      }
      return true;
   }

   // Return the identity matrix of given size
   public static Matrix identity(int size) {
      Matrix identity = new Matrix(size, size);
      for (int i = 0; i < size; i++) {
         identity.data[i][i] = 1.0;
      }
      return identity;
   }

   // Add two matrices
   public Matrix add(Matrix other) {
      if (this.rows != other.rows || this.cols != other.cols) {
         throw new IllegalArgumentException("Matrix dimensions must match for addition.");
      }
      Matrix result = new Matrix(this.rows, this.cols);
      for (int i = 0; i < rows; i++) {
         for (int j = 0; j < cols; j++) {
               result.data[i][j] = this.data[i][j] + other.data[i][j];
         }
      }
      return result;
   }

   // Subtract two matrices
   public Matrix subtract(Matrix other) {
      if (this.rows != other.rows || this.cols != other.cols) {
         throw new IllegalArgumentException("Matrix dimensions must match for subtraction.");
      }
      Matrix result = new Matrix(this.rows, this.cols);
      for (int i = 0; i < rows; i++) {
         for (int j = 0; j < cols; j++) {
               result.data[i][j] = this.data[i][j] - other.data[i][j];
         }
      }
      return result;
   }

   /** Multiply two matrices */
   public Matrix multiply(Matrix other) {
      if (this.cols != other.rows) {
         throw new IllegalArgumentException("Matrix dimensions must match for multiplication.");
      }
      Matrix result = new Matrix(this.rows, other.cols);
      for (int i = 0; i < result.rows; i++) {
         for (int j = 0; j < result.cols; j++) {
               for (int k = 0; k < this.cols; k++) {
                  result.data[i][j] += this.data[i][k] * other.data[k][j];
               }
         }
      }
      return result;
   }

   /** Multiply two matrices */
   public Matrix multiply_3x3_3x1(Matrix other) {
      if (this.cols != other.rows) {
         throw new IllegalArgumentException("Matrix dimensions must match for multiplication.");
      }
      Matrix result = new Matrix(this.rows, other.cols);
      result.data[0][0] += this.data[0][0] * other.data[0][0];
      result.data[0][0] += this.data[0][1] * other.data[1][0];
      result.data[0][0] += this.data[0][2] * other.data[2][0];

      result.data[1][0] += this.data[1][0] * other.data[0][0];
      result.data[1][0] += this.data[1][1] * other.data[1][0];
      result.data[1][0] += this.data[1][2] * other.data[2][0];

      result.data[2][0] += this.data[2][0] * other.data[0][0];
      result.data[2][0] += this.data[2][1] * other.data[1][0];
      result.data[2][0] += this.data[2][2] * other.data[2][0];
      return result;
   }

   // Multiply matrix by scalar
   public Matrix scale(double scalar) {
      Matrix result = new Matrix(this.rows, this.cols);
      for (int i = 0; i < rows; i++) {
         for (int j = 0; j < cols; j++) {
               result.data[i][j] = this.data[i][j] * scalar;
         }
      }
      return result;
   }

   // Transpose the matrix
   public Matrix transpose() {
      Matrix result = new Matrix(this.cols, this.rows);
      for (int i = 0; i < this.rows; i++) {
         for (int j = 0; j < this.cols; j++) {
               result.data[j][i] = this.data[i][j];
         }
      }
      return result;
   }

   // Inverse of a 2x2 or 3x3 matrix
   public Matrix inverse() {
      if (this.rows != this.cols) {
         throw new IllegalArgumentException("Matrix must be square to compute the inverse.");
      }
      if (this.rows == 2) {
         return inverse2x2();
      } else if (this.rows == 3) {
         return inverse3x3();
      } else {
         throw new UnsupportedOperationException("Only 2x2 and 3x3 matrix inverses are supported.");
      }
   }

   private Matrix inverse2x2() {
      double determinant = data[0][0] * data[1][1] - data[0][1] * data[1][0];
      if (determinant == 0) {
         throw new ArithmeticException("Matrix is singular and cannot be inverted.");
      }
      return new Matrix(new double[][]{
         {data[1][1], -data[0][1]},
         {-data[1][0], data[0][0]}
      }).scale(1.0 / determinant);
   }

   private Matrix inverse3x3() {
      double determinant = 
         data[0][0] * (data[1][1] * data[2][2] - data[1][2] * data[2][1]) - 
         data[0][1] * (data[1][0] * data[2][2] - data[1][2] * data[2][0]) + 
         data[0][2] * (data[1][0] * data[2][1] - data[1][1] * data[2][0]);
      
      if (determinant == 0) {
         throw new ArithmeticException("Matrix is singular and cannot be inverted.");
      }

      Matrix adjugate = new Matrix(new double[][]{
         {
               data[1][1] * data[2][2] - data[1][2] * data[2][1],
               data[0][2] * data[2][1] - data[0][1] * data[2][2],
               data[0][1] * data[1][2] - data[0][2] * data[1][1]
         },
         {
               data[1][2] * data[2][0] - data[1][0] * data[2][2],
               data[0][0] * data[2][2] - data[0][2] * data[2][0],
               data[0][2] * data[1][0] - data[0][0] * data[1][2]
         },
         {
               data[1][0] * data[2][1] - data[1][1] * data[2][0],
               data[0][1] * data[2][0] - data[0][0] * data[2][1],
               data[0][0] * data[1][1] - data[0][1] * data[1][0]
         }
      });

      return adjugate.scale(1.0 / determinant);
   }

   // Print the matrix
   public void print() {
      for (double[] row : data) {
         System.out.println(Arrays.toString(row));
      }
      System.out.println();
   }

   public double get(int row, int col) {
      return data[row][col];
   }

   public void set(int row, int col, double value) {
      data[row][col] = value;
   }

   public int getRows() {
      return rows;
   }

   public int getCols() {
      return cols;
   }
}
