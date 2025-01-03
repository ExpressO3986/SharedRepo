package frc.robot.Utilities;

/**
 * This class aims to concentrate in one place all calculations related
 * where the pilote is facing, where the robot is facing when started, how robot should be driven
 * when in field or robot centric? So all driving related calculations. Each season, if we simply
 * import this class, we should very quickly have a field-centric drive.
 */
public class UDriving {

   /**
    * Simulate adding a feedForward component to a motor power.
    * If power's magnitude is higher than threshold, add feedForward of the same sign
    * @param power energy level that's usually sent to motors
    * @param threshold must be positive; under this threshold we will not add feedForward
    * @param feedForward must be positive
    * @return
    */
   public static double addFeedForward(double power, double threshold, double feedForward){
      feedForward = Math.abs(feedForward);
      if (Math.abs(power) < threshold) return power;
      if (power < 0) return power - feedForward;
      return power + feedForward;
   }

   /**
    *
    * @param coordX
    * @param coordY
    * @return
    */
   public static double cartesianToCylindricalAngle(double coordX, double coordY){
      return Math.toDegrees(Math.atan2(coordY, coordX));
   }


    /**
     * Useful function for mecanum and swerve drive bases, to take into account the direction
     * the pilote is facing since the start of match around the field.
     * @param gamepadX raw axis value from gamepad assigned to move in strafing, positive right-ward
     * @param gamepadY raw axis value from gamepad assigned to move in strafing, positive down-ward
     * @param fieldToPilotAngleDeg which direction pilot is facing in relation to field's X axis.
     *                            Positive anti-clockwise from field-x to pilot-x, which is the direction
     *                            pilot is facing when looking at the field.
     * @return
     */
   public static Pt2D convertPilotVectorToField(
      double gamepadX, double gamepadY, final double fieldToPilotAngleDeg) {
      Pt2D pilotJoystickVector = new Pt2D(-gamepadY, -gamepadX).rotateVectorCCW(fieldToPilotAngleDeg);
      return pilotJoystickVector;
   }

   /**
    * All angles in degree.
    * Depend on where the pilot is facing, when he wants pushes joystick toward a certain orientation,
    * calculates the correspondaing orientation in field's reference.
    * @param gamepadX raw axis value from gamepad assigned to move in strafing, positive right-ward
    * @param gamepadY raw axis value from gamepad assigned to move in strafing, positive down-ward
    * @param fieldToPilotAngleDeg which direction pilot is facing in relation to field's X axis.
    *                            Positive anti-clockwise from field-x to pilot-x, which is the direction
    *                            pilot is facing when looking at the field.
    * @return if joystick x and y axis are both 0, then 0 is returned.
    */
   public static double convertPilotDesiredOrientationToField(double gamepadX, double gamepadY, double fieldToPilotAngleDeg){
      if (Util.areEqual(Math.abs(gamepadX) + Math.abs(gamepadY), 0, 0.00001))
         return 0;
      double fieldAngleDeg = fieldToPilotAngleDeg + Math.toDegrees(Math.atan2(-gamepadX, -gamepadY));
      return fieldAngleDeg;
   }

   /**
    * Convert from any x-y vector in Field's reference, to a driving vector robot-centric reference.
    *
    *                                FIELD
    *      o----------------------------------------------------------------> +Y
    *      |                                                                |
    *      |                                                                |
    *      |                                                                |
    *      |                                                                |
    *      |                    |----------------|                          |
    *      |                    |[RL]  odoL  [FL]|                          |
    *      |                    |                |\                         |
    *      |                    |odoB            | > +RobotX                |
    *      |                    |                |/                         |
    *      |                    |[RR]  odoR  [FR]|                          |
    *      |                    |----------------|                          |
    *      |                                                                |
    *      |                                                                |
    *      V----------------------------------------------------------------|
    *     +X
    *
    * Note: the 0 degree for fieldToBotDeg (gyroscope) should be aligned with the field's X axis.
    *
    * @param fieldX x component of vector in field's reference
    * @param fieldY y component of vector in field's reference
    * @param fieldToBotDeg positive anti-clockwise
    * @return
    */
   public static Pt2D convertFromFieldRefToRobotRef(double fieldX, double fieldY, double fieldToBotDeg) {
      Pt2D fieldToRobot = new Pt2D(fieldX, fieldY).rotateVectorCCW(-UAng.to180(fieldToBotDeg));
      return fieldToRobot;
   }

   /**
    * ajuste values so values too small become 0
    *
    * @param joystickValue value wanted to be changed
    * @return
    */
   public static double deadband(double joystickValue, double deadband) {
      if (Math.abs(joystickValue) < deadband) {
         return 0;
      }
      return joystickValue;
   }

   /**
    * Improvement upon the usual deadband calculation. We want a smooth increase when getting outof deadband region,
    * instead of a sudden increase. Example:
    * if value is  0.4 and deadband is 0.4, output is  0.0
    * if value is  0.5 and deadband is 0.4, output is  0.1 instead of 0.5 for a smooth increase
    * if value is -0.5 and deadband is 0.4, output is -0.1 instead of -0.5 for a smooth decrease
    * @param value
    * @param deadband always positive
    * @return
    */
   public static double smoothDeadband(double value, double deadband) {
      if (deadband < 0) deadband = -deadband; // ensures always positive
      if (value < -deadband)
         return (value + deadband);
      else if (-deadband <= value && value <= deadband)
         return 0;
      return value - deadband;
   }

   /**
    * If the radius of the point is less than the threshold, the x and y components become 0
    *
    * @param pt        The point to apply the deadband
    * @param threshold The threshold where the deadband is applied
    * @return The point after the deadband verification
    */
   public static Pt2D deadbandRadial(Pt2D pt, double threshold) {
      Pt2D origin = new Pt2D(0, 0);
      double distanceFromOrigin = origin.distanceTo(pt.x, pt.y);
      if (distanceFromOrigin <= threshold) {
         pt.x = 0;
         pt.y = 0;
      }
      return pt;
   }

}
