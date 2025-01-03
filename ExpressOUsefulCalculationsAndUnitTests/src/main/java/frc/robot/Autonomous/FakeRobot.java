package frc.robot.Autonomous;

import frc.robot.Utilities.Enums.*;
import frc.robot.Utilities.Pose2D;
import frc.robot.Utilities.Pt2D;
import frc.robot.Utilities.UAng;


public class FakeRobot {
   boolean useRealRobotTiming = true;

   // Constants and time durations
   final double maxMoveSpeedDegPerS        = (useRealRobotTiming)?140: 99999; // degrees per second; positive counter-clockwise
   final double maxDistanceSpeedInchesPerS = (useRealRobotTiming)? 50: 99999; // inches per second; positive pointing out of robot intake
   final double minElevatorMoveTimeS       = 2.0;
   final double minExtenderMoveTimeS       = 1.5;
   final double minRollsMoveTimeS          = 0.8;
   final double minClawMoveTimeS           = 0.4;

   // Internal variables
   long startElevatorChangeTimeMs   = 0;
   long startExtenderChangeTimeMs   = 0;
   long startClawChangeTimeMs       = 0;
   long startRollsChangeTimeMs      = 0;
   long lastRobotMoveTimeMs         = 0;

   elevator lastTargetElevator     = elevator.none;
   claw     lastTargetClawPose     = claw.open;
   intake   lastTargetIntakePose   = intake.stopped;
   extender lastTargetExtenderPose = extender.none;

   // Outputs
   public Pose2D   currentRobotPose      = null;
   public elevator currentElevator       = elevator.ground;
   public extender currentExtenderPose   = extender.none;
   public claw     currentClawPose       = claw.close;
   public intake   currentRollPose       = intake.stopped;
   public boolean  sampleInClaw          = false;

   public FakeRobot(Pose2D _currentRobotPose, elevator _currentElevatorPose, extender _currentExtenderPose,
                  intake _currentRollPose, claw _currentClawPose, boolean _sampleInClaw){
      lastRobotMoveTimeMs       = System.currentTimeMillis();
      startElevatorChangeTimeMs = lastRobotMoveTimeMs;
      startClawChangeTimeMs     = lastRobotMoveTimeMs;

      lastTargetElevator     = _currentElevatorPose;
      lastTargetExtenderPose = _currentExtenderPose;
      lastTargetIntakePose = _currentRollPose;
      lastTargetClawPose     = _currentClawPose;

      currentRobotPose         = new Pose2D(_currentRobotPose);
      currentElevator          = _currentElevatorPose;
      currentExtenderPose      = _currentExtenderPose;
      currentRollPose          = _currentRollPose;
      currentClawPose          = _currentClawPose;
      sampleInClaw             = _sampleInClaw;
   }
   public Pose2D getCurrentRobotPose(){
      return new Pose2D(currentRobotPose);
   }
   public elevator getCurrentElevator(){
      return currentElevator;
   }
   public extender getCurrentExtender(){
      return currentExtenderPose;
   }
   public intake getCurrentRollPose(){
      return currentRollPose;
   }
   public claw getCurrentClawPose(){
      return currentClawPose;
   }

   /**
    * Send robot to target pose on field, simulate gradual transition of a real robot.
    * Activate
    */
   public void moveRobot(Pose2D targetPose, elevator targetElevator, extender targetExtender,
                        intake targetIntakePose, claw targetClawPose){
      long currentMs = System.currentTimeMillis();

      // Reset timer to zero only if target changes
      if (lastTargetElevator     != targetElevator)  startElevatorChangeTimeMs = currentMs;
      if (lastTargetExtenderPose != targetExtender)  startRollsChangeTimeMs    = currentMs;
      if (lastTargetIntakePose   != targetIntakePose)startRollsChangeTimeMs    = currentMs;
      if (lastTargetClawPose     != targetClawPose)  startClawChangeTimeMs     = currentMs;

      // Calculate robot rotation during elapsed time
      double moveDegreeDuringElapsedS = (currentMs - lastRobotMoveTimeMs)/1000.0 * maxMoveSpeedDegPerS;
      double deltaDegrees = UAng.getShortestSignedAngleDistance(currentRobotPose.d, targetPose.d);//targetPose.Deg() - currentRobotPose.Deg();
      double actualRotateDeg = deltaDegrees;
      if (Math.abs(moveDegreeDuringElapsedS) < Math.abs(deltaDegrees))
         actualRotateDeg = Math.signum(actualRotateDeg) * moveDegreeDuringElapsedS;
      double finalRotatedDeg = currentRobotPose.d + actualRotateDeg;

      // Calculate robot translation during elapsed time
      double moveDistanceDuringElapsedS = (currentMs - lastRobotMoveTimeMs)/1000.0 * maxDistanceSpeedInchesPerS;
      Pt2D unitMove = targetPose.substract(currentRobotPose).getUnitVector().multiply(moveDistanceDuringElapsedS);
      double distance = targetPose.distanceTo(currentRobotPose);
      if (distance < moveDistanceDuringElapsedS){
         currentRobotPose = new Pose2D(finalRotatedDeg, targetPose);
      } else {
         currentRobotPose = currentRobotPose.add(unitMove);
            currentRobotPose.d = finalRotatedDeg;
      }

      // set Elevator after a delay
      double elapsedElevatorMoveS = (currentMs - startElevatorChangeTimeMs) / 1000.0;
      if (minElevatorMoveTimeS  < elapsedElevatorMoveS) currentElevator = targetElevator;
      // set Extender after a delay
      double elapsedExtenderMoveS = (currentMs - startExtenderChangeTimeMs) / 1000.0;
      if (minExtenderMoveTimeS < elapsedExtenderMoveS) currentExtenderPose = targetExtender;
      // set rolls after a delay
      double elapsedRollsMoveS   = (currentMs - startRollsChangeTimeMs) / 1000.0;
      if (minRollsMoveTimeS < elapsedRollsMoveS) currentRollPose = targetIntakePose;
      // set claw after a delay
      double elapsedClawMs = (currentMs - startClawChangeTimeMs) / 1000.0;
      if (minClawMoveTimeS < elapsedClawMs) currentClawPose = targetClawPose;
      // set Intake after a delay

      // set variables for next iteration
      lastTargetElevator      = targetElevator;
      lastTargetExtenderPose  = targetExtender;
      lastTargetIntakePose = targetIntakePose;
      lastTargetClawPose      = targetClawPose;
      lastRobotMoveTimeMs     = currentMs;
   }
}
