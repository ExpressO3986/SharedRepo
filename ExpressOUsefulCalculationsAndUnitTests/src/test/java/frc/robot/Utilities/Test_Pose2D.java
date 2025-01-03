package frc.robot.Utilities;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class Test_Pose2D {
   
   @Test
   public void testTransform1(){
      Pose2D pt1 = null;
      Pose2D pt2 = null;
      Pose2D pt3 = null;

      pt1 = new Pose2D(4, 4, -135);
      pt2 = new Pose2D(2, -1, 180);
      pt3 = pt2.transformBy(pt1);
      assertEquals(2.0, pt3.x, 0.3);
      assertEquals(3.0, pt3.y, 0.3);
      assertEquals(45, pt3.d, 0.1);

      pt1 = new Pose2D(2.85,1.6, -30);
      pt2 = new Pose2D(-4.1, 2.2, -29);
      pt3 = pt2.transformBy(pt1);
      assertEquals(1.1, pt3.x, 0.8);
   }

   @Test
   public void testTransform2() {
      Pose2D po1 = null, tr = null, res = null;

      // Translations
      po1 = new Pose2D(10, 0, 0);
      tr = new Pose2D(-10, 5, 0);
      res = po1.transformBy(tr);
      assertTrue(res.areEqual(new Pose2D(0, 5, 0), 0.0001, 0.0001));

      po1 = new Pose2D(-10, 0, 0);
      tr = new Pose2D(-10, 5, 0);
      res = po1.transformBy(tr);
      assertTrue(res.areEqual(new Pose2D(-20, 5, 0), 0.0001, 0.0001));

      po1 = new Pose2D(-10, -20, 0);
      tr = new Pose2D(-1, 3, 0);
      res = po1.transformBy(tr);
      assertTrue(res.areEqual(new Pose2D(-11, -17, 0), 0.0001, 0.0001));
   }

   @Test
   public void testCascadingTransforms(){
      Pose2D tr1 = new Pose2D(2, 2, -10);
      Pose2D tr2 = tr1.transformBy(tr1);

      assertEquals(tr2.x, 4.33, 0.05);
      assertEquals(tr2.y, 3.65, 0.05);
      assertEquals(tr2.d, -20.0, 0.01);
   }

   @Test
   public void testLocateCameraFromAprilTag(){
      // Prepare
      Pose2D fieldToCam = new Pose2D(29 +1/16.0, 114 + 3/8.0, -10.0);
      Pose2D fieldToTag1 = new Pose2D( 28.938,132.009,180); // Tag 1 in FTC 2024
      Pose2D fieldToTag2 = new Pose2D( 34.938,132.009,180); // Tag 2 in FTC 2024
      Pose2D fieldToTag3 = new Pose2D( 40.938,132.009,180); // Tag 3 in FTC 2024
      Pose2D camToTag1 = new Pose2D(-3.22, 17.77, 9.82+180); //read from Driver Station: X, Y, Yaw
      Pose2D camToTag2 = new Pose2D(2.68, 18.80, 11.63+180); //read from Driver Station: X, Y, Yaw
      Pose2D camToTag3 = new Pose2D(8.59, 19.76, 11.18+180); //read from Driver Station: X, Y, Yaw

      // Calculate fieldToCam using 3 tags
      Pose2D camToTag1_inv = camToTag1.inverse();
      Pose2D camToTag2_inv = camToTag2.inverse();
      Pose2D camToTag3_inv = camToTag3.inverse();
      Pose2D fieldToCam_calculatedFromTag1 = camToTag1_inv.transformBy(fieldToTag1);
      Pose2D fieldToCam_calculatedFromTag2 = camToTag2_inv.transformBy(fieldToTag2);
      Pose2D fieldToCam_calculatedFromTag3 = camToTag3_inv.transformBy(fieldToTag3);

      // Compare results from 3 tags to fieldToCam measured by measuring tape on the physical field
      assertEquals(fieldToCam.x, fieldToCam_calculatedFromTag1.x, 0.7);
      assertEquals(fieldToCam.x, fieldToCam_calculatedFromTag2.x, 0.7);
      assertEquals(fieldToCam.x, fieldToCam_calculatedFromTag3.x, 0.7);
      assertEquals(fieldToCam.y, fieldToCam_calculatedFromTag1.y, 0.7);
      assertEquals(fieldToCam.y, fieldToCam_calculatedFromTag2.y, 0.7);
      assertEquals(fieldToCam.y, fieldToCam_calculatedFromTag3.y, 0.7);
      assertEquals(fieldToCam.d, fieldToCam_calculatedFromTag1.d, 1.8);
      assertEquals(fieldToCam.d, fieldToCam_calculatedFromTag2.d, 1.8);
      assertEquals(fieldToCam.d, fieldToCam_calculatedFromTag3.d, 1.8);
   }

}