package frc.robot.Utilities;

import java.io.Serializable;
/**
 * On regroupe les sigaux de control ici pour analyses futurs.
 */
public class PilotInputs implements Serializable{
   static final long serialVersionUID = 0;

   // Entrees des pilotes: Axes de la manette et les boutons,
   // mettre les valeurs brutes sans transformations
   public double  joyLeftAxisX         = 0;
   public double  joyLeftAxisY         = 0;
   public double  joyRightAxisX        = 0;
   public double  joyRightAxisY        = 0;
   public boolean buttonA              = false;
   public boolean buttonB              = false;
   public boolean buttonX              = false;
   public boolean buttonY              = false;
   public boolean buttonRB             = false;
   public boolean buttonLB             = false;
   public double  joyRTriggerAnalog    = 0.0; // [0.0 , 1.0]
   public double  joyLTriggerAnalog    = 0.0; // [0.0 , 1.0]
   public boolean buttonBack           = false;
   public boolean buttonStart          = false;
   public boolean dPadNone             = false;
   public boolean dPadUp               = false;
   public boolean dPadRight            = false;
   public boolean dPadDown             = false;
   public boolean dPadLeft             = false;
   public boolean rightBumper          = false;
   public boolean leftBumper           = false;

   // Arcade board buttons
   public boolean button1 = false;
   public boolean button2 = false;
   public boolean button3 = false;
   public boolean button4 = false;
   public boolean button5 = false;
   public boolean button6 = false;
   public boolean button7 = false;
   public boolean button8 = false;
   public boolean button9 = false;
   public boolean button10 = false;
   public boolean button11 = false;
   public boolean button12 = false;

   public double axisButton0 = 0;
   public double axisButton1 = 0;


   // Gyroscope + Odometry
   public double  gyroAngleDeg         = 0;
   public double  copyPoseRobotX       = 0;
   public double  copyPoseRobotY       = 0;
   public double  copyPoseRobotFrg     = 0;

   // Don't put any functions here, we don't touch this class anymore.
   // Put new functions in Utility.java
}
