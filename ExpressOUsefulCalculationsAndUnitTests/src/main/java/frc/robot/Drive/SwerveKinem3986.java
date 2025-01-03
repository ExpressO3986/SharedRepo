package frc.robot.Drive;

import frc.robot.Utilities.Pt2D;
import frc.robot.Utilities.UAng;

/** You can use this class to calculate a swerve module's angle and speed, no matter where it is positioned
 * on the drive base.
 *                                                                           
 *                 ---------------------------------------------             
 *                 |    ^ +x                             ^     |             
 *                 |    |                                |     |             
 *                 | <--o                                o-->  |             
 *                 |                                       +x  |             
 *                 |                  +y ^                     |             
 *                 |                     |                     |             
 *                 |                     |                     |             
 *                 |               drive o---->                |             
 *                 |               center    +x                |             
 *                 |                                           |             
 *                 |                                     ^     |             
 *                 |                                     |     |             
 *                 |    o-->                             o-->  |             
 *                 |    |                                  +x  |             
 *                 | +x v                                      |             
 *                 ---------------------------------------------                            
 *                                                                           
 * As input to the constructor, provide the xy coordinate of 1 of the swerve modules
 * relative to the center of the drive, if your drive doesn't change shape during a match.
 * 
 * To periodically calculate module angle and speed, you'll need to provide the desired
 * speed in x,y of drive chassis, and the desired angular speed.
 */
public class SwerveKinem3986 {
   private final boolean _REAL_MATCH_YES_OPTIM_ANGLE = true; // set to true for competition
   public final Pt2D distFromCenterRobotSwerveModule;
   private final double angleSwerveModuleDeg;
   public double velocityPreOptim = 0;
   public double anglePreOptim = 0;
   public double velocityOptim = 0;
   public double angleOptim = 0;

   public SwerveKinem3986(Pt2D _botCenterToModuleCenterPt) {
      distFromCenterRobotSwerveModule = new Pt2D(_botCenterToModuleCenterPt);
      angleSwerveModuleDeg =
         Math.toDegrees(
               Math.atan2(distFromCenterRobotSwerveModule.y, distFromCenterRobotSwerveModule.x));
   }

   public double getVelocityOptimSwerve   () { return velocityOptim     ;}
   public double getAngleOptimSwerve      () { return angleOptim        ;}
   public double getVelocityPreOptimSwerve() { return velocityPreOptim  ;}
   public double getAnglePreOptimSwerve   () { return anglePreOptim     ;}

   /**
    *
    * @param x desired translation speed, can be in any unit as long as it's consistent with y and rot
    * @param y desired translation speed, can be in any unit as long as it's consistent with x and rot
    * @param rot desired angular speed, as long as it leads to roughly the same translation on the circomference as x and y
    */
   public void calculateWheelSpeedAngle(double x, double y, double rot) {
      // vecteur Rotate
      Pt2D rotate = new Pt2D(rot, 0);
      Pt2D xyRotateBot = rotate.rotateVectorCCW(angleSwerveModuleDeg + 90);
      Pt2D combinedDirection = new Pt2D(x + xyRotateBot.x, y + xyRotateBot.y);
      velocityPreOptim = Math.hypot(combinedDirection.x, combinedDirection.y);
      anglePreOptim = Math.toDegrees(Math.atan2(combinedDirection.y, combinedDirection.x));
   }

   /**
    * Tourner le module vers la direction la plus proche pour atteindre l'angle de conduite désiré pour la base.
    * Il arrive que c'est préférable d'inverser la vitesse du module car l'angle futur inversé (+180)
    * est plus proche de l'angle actuel du module. Cette fonction va donc te donner l'angle futur inversé,
    * tout en inversant aussi la vitesse du module.
    * @param _currentAngleDeg L'angle à appliquer à l'axe x du robot pour devenir l'axe x du module.
    *     Cet angle peut avoir fait plus d'un tour complet depuis le début du match, dont il est compris
    *     entre -inf et +inf en degre. Exemple: 3601 deg si le module a fait 10 tours + 1 degre actuellement.
    * @return les newDesiredAngle est retourne dans un referentiel de -180 a +180 et toujours en degre
    */
   public void calculateOptimizedAngle(double currentAngleWrapped) {
      if (_REAL_MATCH_YES_OPTIM_ANGLE){
         double currentAngle = UAng.to180(currentAngleWrapped);
         double targetAngle = UAng.to180(anglePreOptim);
         double differenceBetweenCurrentAndTargetAngle = Math.abs(currentAngle - targetAngle);
         differenceBetweenCurrentAndTargetAngle = Math.abs(UAng.to180(differenceBetweenCurrentAndTargetAngle));

         double newTargetAngle = anglePreOptim;
         if (differenceBetweenCurrentAndTargetAngle <= 90) {
            velocityOptim = velocityPreOptim;
         } else {
            // If the non-optimized angle is not equal to the optimized angle we inverse the speed of the
            // speed motors. This way, the furthest a wheel will ever rotate is 90 degrees
            velocityOptim = -velocityPreOptim;
            newTargetAngle = UAng.to180(targetAngle - 180);
         }
         angleOptim = UAng.convertToContinuousWrappedAngle(currentAngleWrapped, newTargetAngle);
      } else {
         angleOptim = anglePreOptim;
         velocityOptim = velocityPreOptim;
      }
   }
}
