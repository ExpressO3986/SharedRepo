package frc.robot.Utilities;

public class UAng {

   /**
    * Change angle that are between -infinity and +infinity, to between -179.99999 and +180.0 deg
    *
    * @param angle
    * @return
    */
   public static double to180(double angle) {
      angle = angle % 360;
      angle = (angle + 360) % 360;
      if (angle > 180)
         angle -= 360;
      if (angle < -180)
         angle += 360;
      return angle;
   }

   public static double to360(double originalAngle) {
      double angle = originalAngle % 360.0;
      if (angle < 0)
         angle += 360;
      return angle;
   }

   /**
    * // 1er type d'utilisation: Trouver l'angle le plus court pour faire face à
    * une certaine orientation absolue.
    *
    * <p>
    * Après qu'un robot ou une roue de swerve a tourné plusieurs fois sur lui-même dans un match,
    * le pilote vouloir orienter le robot le plus rapidement vers une direction donnée. Exemple, vers
    * l'angle 0 degré = l'alliance opposé. Si le robot a déjà fait 10 tour sur lui-même dans le sens
    * positif, puis pointe vers la gauche (90 d), on veut alors que le robot tourne simplement dans
    * sens horaire de -90 d pour pointer vers le devant (0d). Or, si on disait aux encodeurs ou
    * gyroscope de revenir à l'angle 0 d, le robot qui est présentement à 10 * 360 + 90 = 3690 d,
    * tournerait pendant 10 tours pour annuler tour le 3690 degré, ce qui est trop inefficace.
    *
    * <p>
    * En appelant cette fonction avec convertToContinuousWrappedAngle(3690, 0), elle va retourner
    * 3600, et on peut envoyer cet angle aux encodeurs ou gyroscope pour très rapidement effectuer un
    * -90 sur l'angle courant pour pointer vers "l'équivalent" du 0 degré (3600 d).
    *
    * <p>
    * // 2e type d'utilisation: construire un angle continuel à partir des angles limités entre
    * -180 et +180 Si ton gyroscope to donne seulement des angles entre -180 et +180 même si le robot
    * continue de tourner dans le même sens, cette fonction détecte un changement
    *
    * <p>
    * Cas 1:
	*    Inputs: 170, 172, 175, 178, -179
	*    Outputs: 170, 172, 175, 178, 181
    *
    * <p>
    * Cas 2:
	*    Inputs: 360+170, 360+172, 360+175, 360+178, 2*360-179)
    *    Outputs:360+170, 360+172, 360+175, 360+178, 360+181)
    *
    * @param _desiredDegree cette angle est normalement compris entre -180 et 180 degre
    * @param _currentDegree
    * @return
    */
   public static double convertToContinuousWrappedAngle(double _currentDegree, double _desiredDegree) {
      double shortestDistance = getShortestSignedAngleDistance(_currentDegree, _desiredDegree);
      double output = shortestDistance + _currentDegree;
      return output;
   }

    /**
     * Cette fonction permet de calculer quelles est la plus petite distance entre le currentAngle et le desired
     * Les deux angles peuvent être compris entre -inf et +inf
     * cas1:
     *      Si _angleDegFrom = 100 et que le _angleDegTo = 0
     *      return -100 Deg
     * cas2:
     *      Si _angleDegFrom = 100 et que le _angleDegTo = -179
     *      return +81 Deg
     * cas3:
     *      Si _angleDegFrom = 179 et que le _angleDegTo = -179
     *      return +2 Deg
     * cas3:
     *      Si _angleDegFrom = 0 et que le _angleDegTo = 361
     *      return +1 Deg
     * cas3:
     *      Si _angleDegFrom = 1 et que le _angleDegTo = 720
     *      return -1 Deg
     * @param _angleDegreeFrom est donné en Deg
     * @param _angleDegreeTo est donné en Deg
     * @return la distance entre les deux angles en Deg
     */
   public static double getShortestSignedAngleDistance(double _angleDegreeFrom, double _angleDegreeTo) {
      double angleDegreeFrom = to180(_angleDegreeFrom);
      double angleDegreeTo = to180(_angleDegreeTo);
      double distance = angleDegreeTo - angleDegreeFrom;

      if (Math.signum(angleDegreeTo) == Math.signum(angleDegreeFrom)) {
         return distance;
      }
      if (angleDegreeTo < 0) {
         angleDegreeTo = angleDegreeTo + 360;
      }
      distance = angleDegreeTo - angleDegreeFrom;
      if (distance > 180) {
         distance = to180(distance);
      }
      return distance;
   }

   /**
    * Les deux angles en parametre sont fournis par les valeurs calculees de
    * wheelAngle et sont compris entre -inf et +inf en degre
    *
    * @return les newDesiredAngle est retourne dans un referentiel de -180 a +180
    *         et toujours en degre
    */
   public static double inverseToOptimizeSwerveAngle(double currentAngle, double desiredAngle) {
      currentAngle = to180(currentAngle);
      desiredAngle = to180(desiredAngle);

      double differenceBetweenCurrentAndDesired = Math.abs(currentAngle - desiredAngle);
      differenceBetweenCurrentAndDesired = Math.abs(to180(differenceBetweenCurrentAndDesired));

      if (differenceBetweenCurrentAndDesired <= 90) {
         return desiredAngle;
      } else {
         double newDesiredAngle = desiredAngle - 180;
         newDesiredAngle = to180(newDesiredAngle);
         return newDesiredAngle;
      }
   }

   public static double rotationToDegree(double rotations) {
      return rotations * 360.0;
   }

   public static double degreeToRotation(double degree) {
      return degree / 360.0;
   }

   public static double flippedAngle(double degreeToFlip, double referenceAng){
      double deltaAng = degreeToFlip - referenceAng;
      double flippedAng = referenceAng + deltaAng;
      return flippedAng;
   }

   /** given robot's current fieldToBot Pose, and a target fieldToBot pose, at which angle should
    * robot turn to to look at the target with its shooter.
    * For 2024 robot, we want the angle when shooter aims on target. Shooter has 180 offset from intake.
    */
   public static double getTargetAngleFromXAxis(Pt2D currentPose, Pt2D targetPt, double intakeToShooterOffsetDeg) {
      Pt2D deltas = targetPt.substract(currentPose);
      double angleLockDeg = UAng.to180(deltas.getDegAngleFromHorizontal()+intakeToShooterOffsetDeg);
      return angleLockDeg;
   }
}
