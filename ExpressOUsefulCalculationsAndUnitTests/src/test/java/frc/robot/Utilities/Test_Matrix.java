package frc.robot.Utilities;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class Test_Matrix {

   @Test
   public void testConstructorAndGetters() {
      Matrix matrix = new Matrix(2, 3);
      assertEquals(2, matrix.getRows());
      assertEquals(3, matrix.getCols());
   }

   @Test
   public void testCopy1DArray() {
      double[] data = {1.0, 2.0, 3.0};
      double[] copy = Matrix.copy(data);
      assertArrayEquals(data, copy);
   }

   @Test
   public void testCopy2DArray() {
      double[][] data = {{1.0, 2.0}, {3.0, 4.0}};
      double[][] copy = Matrix.copy(data);
      assertArrayEquals(data, copy);
   }

   @Test
   public void testMatrixFrom2DArray() {
      double[][] data = {{1.0, 2.0}, {3.0, 4.0}};
      Matrix matrix = new Matrix(data);
      assertEquals(2, matrix.getRows());
      assertEquals(2, matrix.getCols());
      assertEquals(1.0, matrix.get(0, 0));
      assertEquals(2.0, matrix.get(0, 1));
      assertEquals(3.0, matrix.get(1, 0));
      assertEquals(4.0, matrix.get(1, 1));
   }

   @Test
   public void testMatrixFrom1DArray() {
      double[] data = {1.0, 2.0, 3.0};
      Matrix matrix = new Matrix(data, true);
      assertEquals(1, matrix.getRows());
      assertEquals(3, matrix.getCols());
      assertEquals(1.0, matrix.get(0, 0));
      assertEquals(2.0, matrix.get(0, 1));
      assertEquals(3.0, matrix.get(0, 2));
   }

   @Test
   public void testAreEqual() {
      double[][] data1 = {{1.0, 2.0}, {3.0, 4.0}};
      double[][] data2 = {{1.0, 2.0}, {3.0, 4.0}};
      Matrix m1 = new Matrix(data1);
      Matrix m2 = new Matrix(data2);
      assertTrue(Matrix.areEqual(m1, m2, 0.001));
   }

   @Test
   public void testIdentity() {
      Matrix identity = Matrix.identity(3);
      assertEquals(1.0, identity.get(0, 0));
      assertEquals(1.0, identity.get(1, 1));
      assertEquals(1.0, identity.get(2, 2));
      assertEquals(0.0, identity.get(0, 1));
      assertEquals(0.0, identity.get(1, 0));
   }

   @Test
   public void testAdd() {
      double[][] data1 = {{1.0, 2.0}, {3.0, 4.0}};
      double[][] data2 = {{5.0, 6.0}, {7.0, 8.0}};
      Matrix m1 = new Matrix(data1);
      Matrix m2 = new Matrix(data2);
      Matrix result = m1.add(m2);
      assertEquals(6.0, result.get(0, 0));
      assertEquals(8.0, result.get(0, 1));
      assertEquals(10.0, result.get(1, 0));
      assertEquals(12.0, result.get(1, 1));
   }

   @Test
   public void testSubtract() {
      double[][] data1 = {{5.0, 6.0}, {7.0, 8.0}};
      double[][] data2 = {{1.0, 2.0}, {3.0, 4.0}};
      Matrix m1 = new Matrix(data1);
      Matrix m2 = new Matrix(data2);
      Matrix result = m1.subtract(m2);
      assertEquals(4.0, result.get(0, 0));
      assertEquals(4.0, result.get(0, 1));
      assertEquals(4.0, result.get(1, 0));
      assertEquals(4.0, result.get(1, 1));
   }

   @Test
   public void testMultiply() {
      double[][] data1 = {{1.0, 2.0}, {3.0, 4.0}};
      double[][] data2 = {{2.0, 0.0}, {1.0, 2.0}};
      Matrix m1 = new Matrix(data1);
      Matrix m2 = new Matrix(data2);
      Matrix result = m1.multiply(m2);
      assertEquals(4.0, result.get(0, 0));
      assertEquals(4.0, result.get(0, 1));
      assertEquals(10.0, result.get(1, 0));
      assertEquals(8.0, result.get(1, 1));
   }

   @Test
   public void testScale() {
      double[][] data = {{1.0, 2.0}, {3.0, 4.0}};
      Matrix matrix = new Matrix(data);
      Matrix result = matrix.scale(2.0);
      assertEquals(2.0, result.get(0, 0));
      assertEquals(4.0, result.get(0, 1));
      assertEquals(6.0, result.get(1, 0));
      assertEquals(8.0, result.get(1, 1));
   }

   @Test
   public void testTranspose() {
      double[][] data = {{1.0, 2.0}, {3.0, 4.0}};
      Matrix matrix = new Matrix(data);
      Matrix result = matrix.transpose();
      assertEquals(1.0, result.get(0, 0));
      assertEquals(3.0, result.get(0, 1));
      assertEquals(2.0, result.get(1, 0));
      assertEquals(4.0, result.get(1, 1));
   }

   @Test
   public void testInverse2x2() {
      double[][] data = {{4.0, 7.0}, {2.0, 6.0}};
      Matrix matrix = new Matrix(data);
      Matrix result = matrix.inverse();
      assertEquals(0.6, result.get(0, 0), 0.001);
      assertEquals(-0.7, result.get(0, 1), 0.001);
      assertEquals(-0.2, result.get(1, 0), 0.001);
      assertEquals(0.4, result.get(1, 1), 0.001);
   }

   @Test
   public void testInverse3x3() {
      double[][] data = {
         {1.0, 2.0, 3.0},
         {0.0, 1.0, 4.0},
         {5.0, 6.0, 0.0}
      };
      Matrix matrix = new Matrix(data);
      Matrix result = matrix.inverse();
      assertEquals(-24.0, result.get(0, 0), 0.001);
      assertEquals(18.0, result.get(0, 1), 0.001);
      assertEquals(5.0, result.get(0, 2), 0.001);
      assertEquals(20.0, result.get(1, 0), 0.001);
      assertEquals(-15.0, result.get(1, 1), 0.001);
      assertEquals(-4.0, result.get(1, 2), 0.001);
      assertEquals(-5.0, result.get(2, 0), 0.001);
      assertEquals(4.0, result.get(2, 1), 0.001);
      assertEquals(1.0, result.get(2, 2), 0.001);
   }
}