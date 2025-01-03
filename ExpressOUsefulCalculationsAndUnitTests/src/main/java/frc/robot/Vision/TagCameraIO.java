package frc.robot.Vision;

// import org.littletonrobotics.junction.AutoLog;
import edu.wpi.first.math.geometry.Pose2d;

/** Used to visualize vision data on Advantage Scope */
public interface TagCameraIO {

   // @AutoLog
   public static class TagCameraIOInputs {
      public boolean connected = false;
      public Pose2d estimatedPosePhoton = new Pose2d(); // Old 2024 method
      public TagCamData[] detectedTags = new TagCamData[0];
   }

   public static record TagCamData(
      int camId,
      int tagId,
      double timestamp,
      double ambiguity,
      double pitchDeformed, double yawDeformed, // 2d pitch and yaw of tag on image
      double area, double skew // 2d pitch and yaw of tag on image
   ){}

   /** Updates the set of loggable inputs. */
   public default void updateInputs(TagCameraIOInputs inputs) {}
}
