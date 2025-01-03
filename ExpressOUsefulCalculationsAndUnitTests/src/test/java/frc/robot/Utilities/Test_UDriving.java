package frc.robot.Utilities;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class Test_UDriving {

   @Test
   public void testconvertFromFieldRefToRobotRef() {
      Pt2D driveVectorInRobotRef = null;
      double x,y,h;

      // robot facing angle 0 degres (x axis)
      driveVectorInRobotRef = UDriving.convertFromFieldRefToRobotRef(0, 0, 0);
      assertEquals(0, driveVectorInRobotRef.x, 0.001);
      assertEquals(0, driveVectorInRobotRef.y, 0.001);

      driveVectorInRobotRef = UDriving.convertFromFieldRefToRobotRef(1, 0, 0);
      assertEquals(1, driveVectorInRobotRef.x, 0.001);
      assertEquals(0, driveVectorInRobotRef.y, 0.001);

      driveVectorInRobotRef = UDriving.convertFromFieldRefToRobotRef(1, 1, 0);
      assertEquals(1, driveVectorInRobotRef.x, 0.001);
      assertEquals(1, driveVectorInRobotRef.y, 0.001);

      driveVectorInRobotRef = UDriving.convertFromFieldRefToRobotRef(-.5, -.5, 0);
      assertEquals(-.5, driveVectorInRobotRef.x, 0.001);
      assertEquals(-.5, driveVectorInRobotRef.y, 0.001);

      // robot facing angle 90 degres (y axis)
      driveVectorInRobotRef = UDriving.convertFromFieldRefToRobotRef(1, 0, 90);
      assertEquals(0, driveVectorInRobotRef.x, 0.001);
      assertEquals(-1, driveVectorInRobotRef.y, 0.001);

      driveVectorInRobotRef = UDriving.convertFromFieldRefToRobotRef(1, 1, 90);
      assertEquals(1, driveVectorInRobotRef.x, 0.001);
      assertEquals(-1, driveVectorInRobotRef.y, 0.001);

      driveVectorInRobotRef = UDriving.convertFromFieldRefToRobotRef(-.5, -.5, 90);
      assertEquals(-.5, driveVectorInRobotRef.x, 0.001);
      assertEquals(.5, driveVectorInRobotRef.y, 0.001);

      // robot facing angle 135 degres
      driveVectorInRobotRef = UDriving.convertFromFieldRefToRobotRef(1, 0, 135);
      x = Math.cos(Math.toRadians(-135)); y = Math.cos(Math.toRadians(-135));
      assertEquals(x, driveVectorInRobotRef.x, 0.001);
      assertEquals(y, driveVectorInRobotRef.y, 0.001);

      driveVectorInRobotRef = UDriving.convertFromFieldRefToRobotRef(1, 1, 135);
      h = Math.hypot(1, 1);
      assertEquals(0, driveVectorInRobotRef.x, 0.001);
      assertEquals(-h, driveVectorInRobotRef.y, 0.001);

      driveVectorInRobotRef = UDriving.convertFromFieldRefToRobotRef(-.5, -.5, 135);
      h = Math.hypot(.5, .5);
      assertEquals(0, driveVectorInRobotRef.x, 0.001);
      assertEquals(h, driveVectorInRobotRef.y, 0.001);
   }

   @Test
   public void testConvertPilotVectorToField() {
      Pt2D fieldToPilot = null;
      double x,y;

      fieldToPilot = UDriving.convertPilotVectorToField(0, 0, 135);
      assertEquals(0, fieldToPilot.x, 0.001);
      assertEquals(0, fieldToPilot.y, 0.001);

      fieldToPilot = UDriving.convertPilotVectorToField(-.5, .5, 135);
      assertEquals(0, fieldToPilot.x, 0.001);
      assertEquals(-Math.hypot(.5,.5), fieldToPilot.y, 0.001);

      fieldToPilot = UDriving.convertPilotVectorToField(.5, .5, 135);
      assertEquals(Math.hypot(.5,.5), fieldToPilot.x, 0.001);
      assertEquals(0, fieldToPilot.y, 0.001);

      fieldToPilot = UDriving.convertPilotVectorToField(0, -1, 180);
      assertEquals(-1, fieldToPilot.x, 0.001);
      assertEquals(0, fieldToPilot.y, 0.001);

      fieldToPilot = UDriving.convertPilotVectorToField(-1, -1, -180);
      x = -1; y = -1;
      assertEquals(x, fieldToPilot.x, 0.001);
      assertEquals(y, fieldToPilot.y, 0.001);

      fieldToPilot = UDriving.convertPilotVectorToField(0, -1, -170);
      x = Math.cos(Math.toRadians(-170)); y = Math.sin(Math.toRadians(-170));
      assertEquals(x, fieldToPilot.x, 0.001);
      assertEquals(y, fieldToPilot.y, 0.001);
   }

   @Test
   public void testConvertPilotDesiredOrientationToField() {
      assertEquals(0, UDriving.convertPilotDesiredOrientationToField(0, 0, 0), 0.001);
      assertEquals(0, UDriving.convertPilotDesiredOrientationToField(0, -0.5, 0), 0.001);
      assertEquals(-179, UDriving.convertPilotDesiredOrientationToField(0,  0.5, 1), 0.001);
      assertEquals(90, UDriving.convertPilotDesiredOrientationToField(0,  -0.5, 90), 0.001);
      assertEquals(-135, UDriving.convertPilotDesiredOrientationToField(-0.5, -0.5, -180), 0.001);
   }
}