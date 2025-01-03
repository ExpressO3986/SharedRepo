package frc.robot.Utilities;

public class Pose2D extends Pt2D {

   public double d = 0;

   public Pose2D(Pose2D pt) {
      super(pt.x, pt.y);
      this.d = pt.d;
   }

   public Pose2D(double x, double y, double _degree) {
      super(x, y);
      this.d = _degree;
   }

   public Pose2D(double _degree, Pt2D pt) {
      super(pt.x, pt.y);
      this.d = _degree;
   }

   public Pose2D(Pt2D pt, double _degree){
      super(pt);
      this.d = _degree;
   }

   /**
   * Allow for copying each value individually to preserve the same object. Useful for modifying the
   * original version after being passed by reference to a function.
   *
   * @param pose2d
   */
   public void copy(Pose2D pose2d) {
      x = pose2d.x;
      y = pose2d.y;
      d = pose2d.d;
   }

   public Pt2D getPt2D() {
      return new Pt2D(x, y);
   }

   /** Permet de verifier si deux points sont egaux */
   public boolean areEqual(Pose2D pt, double tol, double tolDeg) {
      if (null == pt) return false;
      return areEqual(pt, pt.d, tol, tolDeg);
   }

   /**
   * Permet la comparaison de poses. Si la distance entre 2 points est sous la tolerence, et que les
   * angles convertis en ORIENTATION sont sous la tolerence, returne true. Sinon, false.
   */
   public boolean areEqualOrientation(Pose2D pt, double tol, double tolDeg) {
      if (null == pt) return false;
      double deltaDeg = UAng.getShortestSignedAngleDistance(d, pt.d);
      return areEqual(pt, tol) && Util.areEqual(deltaDeg, 0.0, tolDeg);
   }

   /** Permet de verifier si deux points sont egaux */
   public boolean areEqual(Pt2D pt, double targetDeg, double tol, double tolDeg) {
      if (null == pt) return false;
      return Util.areEqual(x, pt.x, tol)
         && Util.areEqual(y, pt.y, tol)
         && Util.areEqual(d, targetDeg, tolDeg);
   }

   /**
   * Uses Pose2D as a 2D transform operation. You can only translate or rotate.
   *
   * <p>Learn from these sites: https://www.cs.iusb.edu/~dvrajito/teach/c481/c481_06_trans2d.html
   * https://math.stackexchange.com/questions/3094716/inverse-of-roto-translation-matrix
   *
   * @param tr Applies rotation first, then translation.
   * @return New pose with the input transform applied.
   */
   public Pose2D transformBy(Pose2D tr) {
      if (null == tr) return null;

      // apply rotation first, then translation
      Pt2D rot = this.rotateVectorCCW(tr.d);
      Pt2D trl = rot.add(tr);
      return new Pose2D(tr.d + this.d, trl);
   }

   /**
   * Applies the reverse of a 2D trnasform represented by a Pose2D.
   *
   * @return New pose with the reverse of the current transform applied.
   */
   public Pose2D inverse() {
      Pt2D rot = this.rotateVectorCCW(-d);
      return new Pose2D(-rot.x, -rot.y, -d);
   }

   /**
   * Permet de soustraire un point a un autre
   *
   * @param pt
   * @return
   */
   public Pose2D substract(Pose2D pt) {
      return new Pose2D(d - pt.d, super.substract(pt));
   }

   // Permet d'ajouter un point a un autre
   public Pose2D add(Pt2D pt) {
      return new Pose2D(d, super.add(pt));
   }
   // Permet d'ajouter un point a un autre
   public Pose2D add(Pose2D pt) {
      return new Pose2D(d+pt.d, super.add(pt));
   }
   // Permet d'ajouter un point a un autre
   public Pose2D add(double _x, double _y, double _d) {
      return new Pose2D(x+_x, y+_y, d+_d);
   }

   /**
   * Permet d'ajouter un point a un autre
   *
   * @return
   */
   public Pose2D getClosestElementToPoint(Pose2D[] array) {
      // protection
      // si juste 1 element

      double closestDistanceSofar = 99999999;
      int indexofClosestElement = 0;
      // boucle for
      if (array == null) return null;

      for (int i = 0; i < array.length; i++) {
      Pose2D targetPose = array[i];
      double distance = targetPose.distanceTo(this);
      if (distance < closestDistanceSofar) {
         closestDistanceSofar = distance;
      }
      }

      Pose2D ptOut = array[indexofClosestElement];
      return ptOut;
   }

   public String printShort() {
      String text = "x" + Util.trimDecimals4(x)
                  + " y" + Util.trimDecimals4(y)
                  + " d" + Util.trimDecimals4(d);
      return text;
   }

   /**
   * Safely print a shortened text illustrating this Pose's internal values. Will deal with null
   * object gracefully by printing empty text.
   */
   public static String print(Pose2D pos) {
      if (null == pos) return "x       y       d       ";
      String text = String.format("x%6.4f  y%6.4f  d%6.4f", pos.x, pos.y, pos.d);
      return text;
   }
}
