package frc.robot;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.wpilibj.Filesystem;
import frc.robot.Utilities.Pt2D;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

   public static final double LOOP_PERIOD_SECS = 0.02;
   public static final double nbSecondsInAutoSec = 15d;

   public static class Vision {
      // Define the standard deviations for the pose estimator, which determine how fast the pose
      // estimate converges to the vision measurement. This should depend on the vision measurement
      // noise and how many or how frequently vision measurements are applied to the pose
      // estimator.
      public static Matrix<N3, N1> stateStdDevs = VecBuilder.fill(0.01, 0.01, 0.005); // relative to vision's StdDevs
      // The following is not used, replaced by single and multi-tags StdDevs
      public static Matrix<N3, N1> visionStdDevs = VecBuilder.fill(0.9, 0.9, Math.toRadians(5));

      // The standard deviations of our vision estimated poses, which affect correction rate
      // (Fake values. Experiment and determine estimation noise on an actual robot.)
      public static Matrix<N3, N1> kSingleTagStdDevs = VecBuilder.fill(0.9, 0.9, Math.toRadians(5));
      public static Matrix<N3, N1> kMultiTagStdDevs = VecBuilder.fill(0.158, 0.158, 0.407);
      public static Pt2D[] kSingleTagStdDevs_distVsX = new Pt2D[] {
            new Pt2D(1.0, 0.043*1.0),
            new Pt2D(1.5, 0.215*2.0),
            new Pt2D(2.8, 0.900*4.2),
            new Pt2D(3.8, 1.594*5.5),
            new Pt2D(4.9, 1.929*10.5),
            new Pt2D(6.3, 2.334*10.5),
            new Pt2D(7.0, 999.9)
      };
      public static Pt2D[] kSingleTagStdDevs_distVsR = new Pt2D[] {
            new Pt2D(1.0, 0.800*1.5),
            new Pt2D(1.5, 1.258*2.5),
            new Pt2D(2.8, 1.523*4.5),
            new Pt2D(3.8, 4.779*5.5),
            new Pt2D(4.9, 5.000*10.5),
            new Pt2D(6.3, 6.000*10.5),
            new Pt2D(7.0, 999.9)
      };
      public static Pt2D[] kMultiTagsStdDevs_distVsX = new Pt2D[] {
            new Pt2D(1.3, 0.015),
            new Pt2D(1.5, 0.038),
            new Pt2D(2.8, 0.158),
            new Pt2D(3.8, 0.254),
            new Pt2D(4.8, 0.410),
            new Pt2D(6.3, 0.660),
            new Pt2D(7.2, 0.878),
            new Pt2D(8.0, 999.9)
      };
      public static Pt2D[] kMultiTagsStdDevs_distVsR = new Pt2D[] {
            new Pt2D(1.3, 0.041),
            new Pt2D(1.5, 0.089),
            new Pt2D(2.8, 0.407),
            new Pt2D(3.8, 0.596),
            new Pt2D(4.8, 1.500),
            new Pt2D(6.3, 2.500),
            new Pt2D(7.2, 5.000),
            new Pt2D(8.0, 999.9)
      }; // added to not correct beyond this distance
   }
}
