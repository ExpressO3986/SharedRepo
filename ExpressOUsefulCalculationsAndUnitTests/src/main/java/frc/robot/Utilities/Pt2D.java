package frc.robot.Utilities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe de base pour contenir une position, ou un vecteur de deplacement, en 2D.
 *
 * <p>Afin d'assurer la possibilite d'utiliser cette class sans un robot, il est interdit ici
 * d'ajouter des objets de moteurs, de senseurs, de quoi que ce soit de materiel ou du WpiLib.
 *
 * <p>On ne mettera ici que les variables publiques, donc pas de fonctions, pour diminuer la
 * possibilite qu'on brise la compatibilite avec les donnees enregistrees il y a longtemps parce
 * qu'on a ameliorer les fonctions de cette class.
 */
public class Pt2D implements Serializable {
   static final long serialVersionUID = 0;

   public double x = 0;
   public double y = 0;

   public Pt2D(double _x, double _y) {
      x = _x;
      y = _y;
   }

   public Pt2D(Pt2D pt) {
      x = 0; y = 0;
      if (null == pt) return;
      x = pt.x;
      y = pt.y;
   }

   public double getLength() {
      return Math.hypot(x, y);
   }

   // public double X(){return x;} // d'ici la fin, il faudrait revenir avec des fonctions d'acces
   // pour un code plus propre
   // public double Y(){return y;} // d'ici la fin, il faudrait revenir avec des fonctions d'acces
   // pour un code plus propre

   // Permet de caluler la distance, Junction, Stack et Pose2D ont accès à cette fonction.
   public double distanceTo(double _x, double _y) {
      double deltaX = (_x - x) * (_x - x);
      double deltaY = (_y - y) * (_y - y);

      double distance = Math.sqrt(deltaX + deltaY);

      return distance;
   }

   // Permet de calculer une distance d'un point a un autre
   public double distanceTo(Pt2D pt) {
      double distance = distanceTo(pt.x, pt.y);
      return distance;
   }

   /**
   * Permet de soustraire un point a un autre
   *
   * @param pt
   * @return
   */
   public Pt2D substract(Pt2D pt) {
      double newX = x - pt.x;
      double newY = y - pt.y;
      return new Pt2D(newX, newY);
   }

   /**
   * Permet d'ajouter un point a un autre
   *
   * @param pt
   * @return
   */
   public Pt2D add(Pt2D pt) {
      double newX = x + pt.x;
      double newY = y + pt.y;
      return new Pt2D(newX, newY);
   }

   // Permet de multiplier un point a un autre
   public Pt2D multiply(double constant) {
      double newX = x * constant;
      double newY = y * constant;
      return new Pt2D(newX, newY);
   }

   // Permet de divisier un point a un autre
   public Pt2D divideBy(double constant) {
      double newX = x / constant;
      double newY = y / constant;
      return new Pt2D(newX, newY);
   }

   /** equation lineaire de vecteurs, permet d'avoir le vecteur unitaire */
   public Pt2D getUnitVector() {
      double hypotenus = Math.sqrt(x * x + y * y);
      return this.divideBy(hypotenus);
   }

   /** Permet de verifier si deux points sont egaux */
   public boolean areEqual(Pt2D pt, double tol) {
      if (((Object) pt).equals((Object) this)) return true;
      return UMath.equalsApprox(x, pt.x, tol) && UMath.equalsApprox(y, pt.y, tol);
   }

   /**
   * Calculate the angle from the vertical of the point as if it was a vector from the origin.
   * Counter-Clockwise is positive and Clockwise is negative
   * The angle is given in a -180° to 180° referential
   * <pre>
   *            0°
   *     45° x | x  -45°
   *      x    |    x
   *  90 x     |     x -90°
   * ---------------------
   *     x     |     x
   *  135°x    |    x -135°
   *         x | x
   *       180° -180°
   * </pre>
   *
   * @return Angle in degree
   */
   public double getDegAngleFromVertical() {
      double angle = Math.toDegrees(Math.atan2(x, y));
      return angle;
   }
   public double getDegAngleFromHorizontal() {
      double angle = Math.toDegrees(Math.atan2(y, x));
      return angle;
   }

   /**
   * Function used to give the modification of values after the vector rotation. Positive angle
   * variation clockwise. Angle value is given to the function in degrees.
   *
   * @param uAngDeg angle en degré, positif en sens ANTI-HORAIRE
   */
   public Pt2D rotateVectorCCW(double uAngDeg) {
      double uAngRad = Math.toRadians(uAngDeg);
      double relx = (this.x * Math.cos(uAngRad)) - (this.y * Math.sin(uAngRad));
      double rely = (this.x * Math.sin(uAngRad)) + (this.y * Math.cos(uAngRad));

      Pt2D newVec = new Pt2D(relx, rely);
      return newVec;
   }

   /**
   * Safely print a shortened text illustrating this Pose's internal values. Will deal with null
   * object gracefully by printing empty text.
   */
   public int getClosestPt(ArrayList<Pt2D> listJointPoints) {
      int closestPtIndex = -1; // output
      double closestDistanceDeg = 999999999;
      if (null == listJointPoints) return closestPtIndex;

      for (int i = 0; i < listJointPoints.size(); i++) {
         Pt2D targetAngleDeg = listJointPoints.get(i);
         double distance = distanceTo(targetAngleDeg);
         if (distance < closestDistanceDeg) {
         closestDistanceDeg = distance;
         closestPtIndex = i;
         }
      }
      return closestPtIndex;
   }

   /**
   * @param list
   * @return
   */
   public boolean contains(ArrayList<Pt2D> list) {
      if (list == null) return false;
      for (int i = 0; i < list.size(); i++) {
         if (list.get(i).x == this.x && list.get(i).y == this.y) {
         return true;
         }
      }
      return false;
   }
}
