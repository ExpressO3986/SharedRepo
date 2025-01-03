package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Vision.TagsOnField;

public class Robot extends TimedRobot {

   public Robot() {
      TagsOnField.init();
   }

   /** This function is called periodically during all modes. */
   @Override
   public void robotPeriodic() {
      // Runs the Scheduler. This is responsible for polling buttons, adding
      // newly-scheduled commands, running already-scheduled commands, removing
      // finished or interrupted commands, and running subsystem periodic() methods.
      // This must be called from the robot's periodic block in order for anything in
      // the Command-based framework to work.
      CommandScheduler.getInstance().run();
   }

   /** This function is called once when teleop is enabled. */
   @Override
   public void teleopInit() {
   }

   /** This function is called periodically during operator control. */
   @Override
   public void teleopPeriodic() {
   }

}
