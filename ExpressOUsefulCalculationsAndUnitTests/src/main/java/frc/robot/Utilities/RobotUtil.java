package frc.robot.Utilities;

import edu.wpi.first.hal.AllianceStationID;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Solenoid;

/** This class contains utility functions that depends on WPILib classes. */
public class RobotUtil {

   /**
   * In simulation, this function RobotController.getBatteryVoltage() has a bug, it can occasionally
   * return impossibly large values, sending simulated robot out to space. We haven't get tested how
   * reliable it is in a real robot, so just in case we should always call this protection function
   * to clamp the battery output to between +- 13.5V (highest voltage read when battery fully
   * charged in practice).
   *
   * <p>So don't call RobotController.getBatteryVoltage() directly!
   *
   * @return
   */
   public static double getBatteryVoltage() {
      double battery = MathUtil.clamp(RobotController.getBatteryVoltage(), -13.5, 13.5);
      return battery;
   }

   /**
   * Export WPILib's 3D transform components into a fixed array of 6 doubles. Order: x, y, z, roll,
   * pitch, yaw
   *
   * @param tr
   * @return
   */
   public static double[] toArray(Transform3d tr) {
      double[] arr = new double[] {0, 0, 0, 0, 0, 0};
      if (null == tr) return arr;
      arr[0] = tr.getX();
      arr[1] = tr.getY();
      arr[2] = tr.getZ();
      Rotation3d rot = tr.getRotation();
      if (null == rot) return arr;
      arr[3] = rot.getX();
      arr[4] = rot.getY();
      arr[5] = rot.getZ();
      return arr;
   }
   /**
   * Export WPILib's 3D transform components into a fixed array of 6 doubles. Order: x, y, z, roll,
   * pitch, yaw
   *
   * @param tr
   * @return
   */
   public static double[] toArray(Pose3d tr) {
      double[] arr = new double[] {0, 0, 0, 0, 0, 0};
      if (null == tr) return arr;
      arr[0] = tr.getX();
      arr[1] = tr.getY();
      arr[2] = tr.getZ();
      Rotation3d rot = tr.getRotation();
      if (null == rot) return arr;
      arr[3] = rot.getX();
      arr[4] = rot.getY();
      arr[5] = rot.getZ();
      return arr;
   }

   public static Pose2D toPose2D(Pose2d pos) {
      return new Pose2D(pos.getX(), pos.getY(), pos.getRotation().getDegrees());
   }
   public static Pose2D toPose2D(Pose3d pos) {
      return new Pose2D(
      pos.getX(),
      pos.getY(),
      Math.toDegrees(pos.getRotation().getAngle()));
   }
   public static Pt2D toPt2D(Translation2d tr) {
      return new Pt2D(tr.getX(), tr.getY());
   }
   public static Pol2D toPol2D(Translation2d tr) {
      return new Pol2D(new Pt2D(tr.getX(), tr.getY()));
   }

   /**
   * Makes Pt2d to a Pose2d where the rotation is "new Rotation2d()"
   * @return Pose2d
   */
   public static Pose2d toPose2d(Pt2D pt){
      return new Pose2d(pt.x, pt.y, new Rotation2d());
   }
   public static Pose2d toPose2d(Pose2D pt){
      return new Pose2d(pt.x, pt.y, Rotation2d.fromDegrees(pt.d));
   }

   public static Translation2d toTransl2d(Pt2D pt){
      return new Translation2d(pt.x, pt.y);
   }
   public static Translation2d toTransl2d(Pol2D pol){
      Pt2D pt = pol.toCartesian();
      return new Translation2d(pt.x, pt.y);
   }
   public static Pose2d add(Pose2d p1, Pose2d p2){
      Pose2d p12 = new Pose2d(
         p1.getX() + p2.getX(),
         p1.getY() + p2.getY(),
         Rotation2d.fromRadians(p1.getRotation().getRadians() + p2.getRotation().getRadians())
      );
      return p12;
   }


   public static Pose3d toPose3d(Pose3D pt){
      return new Pose3d(pt.x, pt.y, pt.z,
         new Rotation3d(0, 0,Math.toRadians(pt.d)));
   }
   public static Pose3d[] toPose3d(Pose3D pts[]){
      if (null == pts) return null;
      Pose3d[] pts3 = new Pose3d[pts.length];
      for (int i = 0; i < pts.length; i++) {
         if (null == pts[i]) {pts3[i] = null; continue;}
         pts3[i] = new Pose3d(pts[i].x, pts[i].y, pts[i].z,
            new Rotation3d(Math.toRadians(pts[i].roll), Math.toRadians(pts[i].pitch), Math.toRadians(pts[i].d)));
      }
      return pts3;
   }

   /**
   * Permet d'activer un piston continuellement. La fonction verifie avec le solenoide et evite
   * d'envoyer le meme etat consecutivement.
   *
   * @param trueForw_falseRev
   * @param piston
   */
   public static void setPiston(boolean trueForw_falseRev, DoubleSolenoid piston) {
      if (piston == null) return;
      Value newVal = trueForw_falseRev ? Value.kForward : Value.kReverse;
      Value lastVal = piston.get();
      if (newVal == lastVal) return;
      piston.set(newVal);
   }

   /**
   * Permet d'activer un piston continuellement. La fonction verifie avec le solenoide et evite
   * d'envoyer le meme etat consecutivement.
   *
   * @param trueForw_falseRev
   * @param piston
   */
   public static void setPiston(boolean trueForw_falseRev, Solenoid piston) {
      if (piston == null) return;
      boolean lastVal = piston.get();
      if (trueForw_falseRev == lastVal) return;
      piston.set(trueForw_falseRev);
   }



   //============================================================
   //Motor encoder calculations: raw sensor unit vs user unit, and offsets
   public static double convertSensorRawPosToUserPos(final double sensorToUserRatio, double sensorPositionRaw, double sensorPosOffset){
      double res = calculateOffsettedNewSensorPos(sensorPositionRaw, sensorPosOffset) / sensorToUserRatio;
      return res;
   }
   public static double convertUserPosToSensorRawPos(final double sensorToUserRatio, double userPosition, double sensorPosOffset){
      double desiredSensorPosIfOffsetIs0 = userPosition * sensorToUserRatio;
      double desiredSensorPosRaw = desiredSensorPosIfOffsetIs0 + sensorPosOffset;
      return desiredSensorPosRaw;
   }
   public static double convertUserToSensorVelocity(final double sensorToUserRatio, double userPosition){
      double desiredSensorPosIfOffsetIs0 = userPosition * sensorToUserRatio;
      return desiredSensorPosIfOffsetIs0;
   }
   public static double convertSensorToUserVelocity(final double sensorToUserRatio, double sensorPosition){
      double desiredSensorPosIfOffsetIs0 = sensorPosition / sensorToUserRatio;
      return desiredSensorPosIfOffsetIs0;
   }
   public static double calculateOffsettedNewSensorPos(double currentSensorRawPos, double sensorPosOffset){
      double res =  currentSensorRawPos - sensorPosOffset;
      return res;
   }
   public static double calculateNewSensorOffset(double currentSensorRawPos, double desiredSensorPos){
      // Formula: new = raw - off
      // ex:      5   = 10  - ?
      // Formula: off = raw - new
      // ex:      3?  = 10 - 7
      // ex:      8?   = (10+1) - 3
      double res =  currentSensorRawPos - desiredSensorPos;
      return res;
   }

   /** Especially useful when connected to a real FMS, since this will always give the correct
    * answer even if robot just rebooted from a disconnect. If nothing is found, will return RED by
    */
   public static boolean getIsTeamRed_FromDriverStation(){
      switch (DriverStation.getRawAllianceStation()){
         case Blue1: return false;
         case Blue2: return false;
         case Blue3: return false;
         default   : return true;
      }
  }
}
