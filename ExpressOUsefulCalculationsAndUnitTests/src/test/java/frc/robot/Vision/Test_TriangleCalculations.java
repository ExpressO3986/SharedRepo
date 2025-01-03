package frc.robot.Vision;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import frc.robot.Utilities.TriAngleFrom3Sides;

/** Add your docs here. */
public class Test_TriangleCalculations {

    @Test
    public void test_TriAngleFrom3Sides(){
        TriAngleFrom3Sides triangle = null;

        // Test an invalid 0 length triangle
        triangle = new TriAngleFrom3Sides(3, 4, 8);
        assertEquals(0, triangle.angleDegA, 0.00000001);
        assertEquals(0, triangle.angleDegB, 0.00000001);
        assertEquals(0, triangle.angleDegC, 0.00000001);

        // Test an invalid flat triangle
        triangle = new TriAngleFrom3Sides(10, 4, 3);
        assertEquals(0, triangle.angleDegA, 0.00000001);
        assertEquals(0, triangle.angleDegB, 0.00000001);
        assertEquals(0, triangle.angleDegC, 0.00000001);


        // Test a valid triangle
        triangle = new TriAngleFrom3Sides(3, 4, 5);
        assertEquals(90.0             , triangle.angleDegA, 0.00000001);
        assertEquals(53.13010235415598, triangle.angleDegB, 0.00000001);
        assertEquals(36.86989764584402, triangle.angleDegC, 0.00000001);

        // Test a valid triangle
        triangle = new TriAngleFrom3Sides(6, 6.39, 6);
        assertEquals(57.83, triangle.angleDegA, 0.01);
        assertEquals(64.35, triangle.angleDegB, 0.01);
        assertEquals(57.83, triangle.angleDegC, 0.01);


        // run calculations for real measurements
        triangle = new TriAngleFrom3Sides(0.640, 0.685, 0.248);
        triangle = new TriAngleFrom3Sides(0.661, 0.710, 0.239);
        triangle = new TriAngleFrom3Sides(1.610, 1.797, 0.796);
        triangle = new TriAngleFrom3Sides(1.659, 1.867, 0.914);


        assertFalse(triangle == null);
    }

}
