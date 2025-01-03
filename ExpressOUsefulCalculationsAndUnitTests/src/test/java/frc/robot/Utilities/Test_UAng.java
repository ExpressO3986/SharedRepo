package frc.robot.Utilities;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/** Add your docs here. */
public class Test_UAng {

   @Test
   public static void test_getJoystickAngle() {
      try{
         assertEquals(UDriving.cartesianToCylindricalAngle(1, 1), 45, 0.01);
         assertEquals(UDriving.cartesianToCylindricalAngle(100, 17.633), 10, 0.01);

         System.out.println("test_getJoystickAngle succeeded, now get back to work");

      } catch (Exception e) {
         System.out.println("test_getJoystickAngle is a failure");
         e.printStackTrace();
      }
   }

   @Test
   public static void test_getMinimalAngleChange() {
      try{
         assertEquals(UAng.getShortestSignedAngleDistance(45, 65), 20, 0.01, "test1");
         assertEquals(UAng.getShortestSignedAngleDistance(-35, -70), -35, 0.01, "test2");
         assertEquals(UAng.getShortestSignedAngleDistance(170, -170), 20, 0.01, "test3");
         assertEquals(UAng.getShortestSignedAngleDistance(170, -163.9), 26.1, 0.01, "test4");
         assertEquals(UAng.getShortestSignedAngleDistance(-163.9, 170), -26.1, 0.01, "test5");
         assertEquals(UAng.getShortestSignedAngleDistance(-36163.9, 36170), -26.1, 0.01, "test6");

         System.out.println("test_getMinimalAngleChange succeeded, now get back to work");

      } catch (Exception e) {
         System.out.println("test_getMinimalAngleChange is a failure");
         e.printStackTrace();
      }
   }
}
