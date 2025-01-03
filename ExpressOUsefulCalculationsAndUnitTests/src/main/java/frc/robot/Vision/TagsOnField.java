package frc.robot.Vision;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import frc.robot.Utilities.Pose3D;
import frc.robot.Utilities.UMath;
import frc.robot.Utilities.Util;

/** The 3D pose of all april tags on the field, in meters. The index value is the tag ID. */
public class TagsOnField implements Serializable {
   static final long serialVersionUID = 0;

   private static Map<Integer, Pose3D> tagPoses = new HashMap<>();
   public static Pose3d[] tagPoses3 = null;
   public static AprilTagFieldLayout kTagLayout = null;

   public static void init(){
      tagPoses.put(1, new Pose3D(0.776,     0, 1.167, 0,0,  0)); // ID 1
      tagPoses.put(2, new Pose3D(3.080,     0, 1.167, 0,0,  0)); // ID 2
      tagPoses.put(3, new Pose3D(    0, 0.847, 1.167, 0,0,    -90)); // ID 3
      tagPoses.put(4, new Pose3D(    0, 3.055, 1.167, 0,0,    -90)); // ID 4
      tagPoses.put(5, new Pose3D(3.281, 3.074, 1.167, 0,0, 90)); // ID 5
      tagPoses.put(6, new Pose3D(2.905, 6.061, 1.260, 0,0,180)); // ID 6
      tagPoses.put(7, new Pose3D(0.776,     0, 0.811, 0,0,  0)); // ID 7
      tagPoses.put(8, new Pose3D(    0, 1.161, 0.808, 0,0,    -90)); // ID 8

      // Tag30 - 53
      int idStart = 30;
      double vDownY = UMath.moy(1.663-1.657, 2.050-2.045)/7d;
      double vDownZ = UMath.moy(0.668-2.240, 0.670-2.244)/7d;
      double vRightY = UMath.moy(2.045-1.657, 2.050-1.663)/2d;
      double vRightZ = UMath.moy(2.244-2.240, 0.670-0.668)/2d;
      for (int r = 0; r < 3; r++) {
         for (int d = 0; d < 8; d++) {
            double y = 1.657+d*vDownY+r*vRightY;
            double z = 2.240+d*vDownZ+r*vRightZ;
            tagPoses.put(idStart, new Pose3D( 0, y, z, 0, 0, -90));
            idStart++;
         }
      }

      tagPoses3 = new Pose3d[tagPoses.size()];
      int i = 0;
      List<AprilTag> poses3 = new ArrayList<>();
      // now iterate through all key,value pairs of tagPoses
      for (Map.Entry<Integer, Pose3D> entry : tagPoses.entrySet()) {
         int id = entry.getKey();
         Pose3D pose = entry.getValue();
         tagPoses3[i] = new Pose3d(pose.x, pose.y, pose.z,
            // +90, because FRC's x axis comes out of the tag, but in FTC our y axis is coming out of tag
            new Rotation3d(Math.toRadians(pose.roll), Math.toRadians(pose.pitch), Math.toRadians(pose.d+90)));
         poses3.add(new AprilTag(id, tagPoses3[i]));
         i++;
      }

      kTagLayout = new AprilTagFieldLayout(poses3, 6.096, 3.048);
   }

   public static boolean isTagValid(int tagId){
      if (null == tagPoses) init();
      if (tagPoses.size() <= 0) return false;
      if (tagId <= 0 || !tagPoses.containsKey(tagId)) return false;
      return true;
   }
   public static Pose3D get(int tagId){
      if (null == tagPoses) init();
      if (tagPoses.size() <= 0) return null;
      if (tagId <= 0 || !tagPoses.containsKey(tagId)) return null;
      return tagPoses.get(tagId);
   }
   public static Map<Integer, Pose3D> getPoses(){
      if (null == tagPoses) init();
      return tagPoses;
   }

}



// // The old layout code layout of the AprilTags on the field
// public static final AprilTagFieldLayout kTagLayout;
// static {
//    try {
//       // kTagLayout = AprilTagFieldLayout.loadField(AprilTagFields.k2024Crescendo);
//       // kTagLayout = new AprilTagFieldLayout("./kTagLayoutHome.json");
//       kTagLayout = new AprilTagFieldLayout(Filesystem.getDeployDirectory()+"/kTagLayoutHome.json");
//    } catch (Exception e) {
//       e.printStackTrace();
//       throw new RuntimeException(e);
//    }
// }
