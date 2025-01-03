package frc.robot.Vision;

import java.io.Serializable;
import java.util.ArrayList;

/** Data class for recording a sequence of tags captured by photon cameras.
 * Don't change this class anymore to avoid voiding recorded data files.
 */
public class Apparition implements Serializable {
   static final long serialVersionUID = 0;

   public static ShortPose3D[] tagPoses = null;
   public static ShortPose3D[] camPoses = null;
   public ArrayList<Data> detectedTags = null; // the only array that increases with time

   public static class ShortPose3D implements Serializable {
      static final long serialVersionUID = 0;
      public int id;
      public double x, y, z;
      public double roll, pitch, yaw;
   }
   public static class Data implements Serializable {
      static final long serialVersionUID = 0;
      public int camId;
      public int tagId;
      public double timestamp;
      public double ambiguity;
      public double pitchDeformed, yawDeformed; // pitch and yaw read by Photon
      public double area, skew; // other tag stats read by Photon
   }
}
