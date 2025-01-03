package frc.robot.Utilities;

import java.io.Serializable;

/** Add your docs here. */
public class Pol2D implements Serializable {
   static final long serialVersionUID = 0;

   public double m; // vector magnitude
   public double d; // vector angle with x axis in degrees

   public Pol2D(double _distanceM, double _angleDeg) {
      d = _angleDeg;
      m = _distanceM;
   }

   public Pol2D(Pt2D cartesian) {
      m = Math.hypot(cartesian.x, cartesian.y);
      double rad = Math.atan2(cartesian.y, cartesian.x);
      d = Math.toDegrees(rad);
   }

   public Pt2D toCartesian() {
      double _x = Math.cos(Math.toRadians(d)) * m;
      double _y = Math.sin(Math.toRadians(d)) * m;
      return new Pt2D(_x, _y);
   }
}
