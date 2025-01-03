package frc.robot.Utilities;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class Test_Pt2D {

   @Test
   public void testDistanceTo() {
      assertEquals(5, new Pt2D(0,0).distanceTo(5, 0), 0.001);
      assertEquals(5, new Pt2D(0,0).distanceTo(0, -5), 0.001);
      assertEquals(5, new Pt2D(0,0).distanceTo(0, 5), 0.001);
      assertEquals(54, new Pt2D(0,0).distanceTo(0, -54), 0.001);
      assertEquals(4564560458.4564, new Pt2D(-2,0).distanceTo(4564560456.4564, 0), 0.001);
   }
}