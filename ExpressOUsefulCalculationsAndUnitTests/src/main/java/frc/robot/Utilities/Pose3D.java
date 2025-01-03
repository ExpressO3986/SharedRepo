package frc.robot.Utilities;

public class Pose3D extends Pose2D {

   public double z = 0; // height
   public double roll  = 0;
   public double pitch = 0;
   // yaw is pose2d's d parameter

   public Pose3D(Pose3D pt) {
      super(pt.x, pt.y, pt.d);
      this.z     = pt.z;
      this.roll  = pt.roll;
      this.pitch = pt.pitch;
   }

   public Pose3D(double x, double y, double z, double roll, double pitch, double yaw) {
      super(x, y, yaw);
      this.z = z;
      this.roll  = roll;
      this.pitch = pitch;
   }

   public Pose3D(Pose2D pt, double z, double roll, double pitch, double yaw){
      super(pt);
      this.z = z;
      this.roll  = roll;
      this.pitch = pitch;
   }
}
