package frc.robot.Utilities;

/** Calculate a triangle's 3 angles, none are required to be 90 deg, if we know
 * the the length of its 3 sides.
 *                         A /\
 *                          /  \
 *                         /    \
 *                        /      \
 *                       /        \
 *                      /          \
 *                     /            \
 *                 B  /--------------\ C
 * If you give 3 invalid lengths, such as when they can't form a triangle,
 * the angles will be set to 0.
 *
 * Tests indicate that one triangle calculation takes about 800 nanoseconds.
 */
public class TriAngleFrom3Sides {
   public double angleDegA = 0;
   public double angleDegB = 0;
   public double angleDegC = 0;

   public TriAngleFrom3Sides(double sideAB, double sideAC, double sideBC){
      // Quit if the sides can't form a triangle.
      if (sideAB <= 0 || sideAC <= 0 || sideBC <= 0){
         return;
      }
      if (sideAB + sideAC < sideBC
      || sideAB + sideBC < sideAC
      || sideAC + sideBC < sideAB){
         return;
      }

      // Calculate the angles.
      double sideABAB = sideAB * sideAB;
      double sideACAC = sideAC * sideAC;
      double sideBCBC = sideBC * sideBC;

      angleDegA = Math.toDegrees(Math.acos((sideABAB + sideACAC - sideBCBC) / (2 * sideAB * sideAC)));
      angleDegB = Math.toDegrees(Math.acos((sideABAB + sideBCBC - sideACAC) / (2 * sideAB * sideBC)));
      angleDegC = Math.toDegrees(Math.acos((sideACAC + sideBCBC - sideABAB) / (2 * sideAC * sideBC)));

   }

   /**
    *
    *
    * <pre>
    *       [c]
    *  ____________
    *  \          /
    *   \        /
    * [a]\      /[b]
    *     \    /
    *      \  /
    *       \/
    *       [C]
    * </pre>
    *
    * @param a Longueur du côté gauche dans le triangle (côté [a] dans l'ascii)
    * @param b Longueur du côté droit  dans le triangle (côté [b] dans l'ascii)
    * @param c Longueur du côté opposé à l'angle calculé (côté [c] dans l'ascii)
    * @return L'angle [C] dans l'ascii en degré
    */
   public static double solveTriangleWithCosRule(double a, double b, double c) {
      return Math.toDegrees(Math.acos((a * a + b * b - c * c) / (2.0 * a * b)));
   }
}
