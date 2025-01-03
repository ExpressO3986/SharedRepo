package frc.robot.Utilities;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class Test_Pol2D {
   @Test
   void testPolarToCartesian() {

      Pol2D pol = null;
      Pt2D  pt = null;

      pol = new Pol2D(1, 90);
      assertEquals(  1, pol.m, 0.0001);
      assertEquals( 90, pol.d, 0.0001);

      pt = new Pol2D(1, 0).toCartesian();
      assertEquals(  1, pt.x, 0.0001);
      assertEquals(  0, pt.y, 0.0001);

      pt = new Pol2D(1, 45).toCartesian();
      assertEquals(0.707, pt.x, 0.001);
      assertEquals(0.707, pt.y, 0.001);

      pt = new Pol2D(1, 90).toCartesian();
      assertEquals(0, pt.x, 0.001);
      assertEquals(1, pt.y, 0.001);

      pt = new Pol2D(1, 135).toCartesian();
      assertEquals(-0.707, pt.x, 0.001);
      assertEquals( 0.707, pt.y, 0.001);



   }
}
