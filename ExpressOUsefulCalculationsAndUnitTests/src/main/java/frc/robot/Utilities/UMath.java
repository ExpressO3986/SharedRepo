package frc.robot.Utilities;

import java.util.ArrayList;

public class UMath {

   public static double clamp(double val, double low, double high) {
      if (val < low) return low;
      else if (high < val) return high;
      return val;
   }

   public static int clamp(int val, int low, int high) {
      if (val < low) return low;
      else if (val > high) return high;
      return val;
   }

   public static double clamp(double power, double maxPower) {
      if      (power >  maxPower) power =  maxPower;
      else if (power < -maxPower) power = -maxPower;
      return power;
   }

   public static boolean equalsApprox(double a, double b) {
      return equalsApprox(a, b, 0.0001);
   }

   public static boolean equalsApprox(double a, double b, double tol) {
      tol = Math.abs(tol);
      if (Math.abs(a - b) < tol) return true;
      return false;
   }

   public static double deadband(final double joystickValue, final double deadband) {
      if (Math.abs(joystickValue) < deadband) return 0;
      return joystickValue;
   }

   public static double tracerDroite(double m, double b, double x) {
      double y = m * x + b;
      return y;
   }

   /**
    * For 2x, y points which will orm a line, you can ask for the y value corresponding to any x value
    *
    * @param x1
    * @param y1
    * @param x2
    * @param y2
    * @param xCurrent
    * @return
    */
   public static double tracerDroite(double x1, double y1, double x2, double y2, double xCurrent) {
      // y = mx + b
      // b = y - mx
      double m = (y2 - y1) / (x2 - x1);
      double b = y1 - m * x1;
      return tracerDroite(m, b, xCurrent);
   }

   public static double tracer2DroitesConnectees(double x, double b1, double m1, double m2, double limit12) {
      double y = 0;
      if (x < limit12) y = tracerDroite(m1, b1, x);
      else {
      double b2 = tracerDroite(m1, b1, limit12);
      y = tracerDroite(m2, b2, x - limit12);
      }
      return y;
   }

   public static double tracer2Droites4PtsConnectes(double xCurrent, double x1, double y1, double x2, double y2,
   		 double x3, double y3, double x4, double y4){
      double m1 = (y1 - y2) / (x1 - x2);
      double b1 = y1 - m1 * x1;

      double m2 = (y3 - y4) / (x3 - x4);
      double b2 = y3 - m2 * x3;

      double xIntersection = (b2 - b1) / (m1 - m2);
      double y = 0;

      if (xCurrent > xIntersection) {
      	 y = m2 * xCurrent + b2;
      } else {
         y = m1 * xCurrent + b1;
      }
      return y;
   }

   public static double tracer3DroitesConnectees(
      double x, double b1, double m1, double m2, double m3, double limit12, double limit23) {
      double y = 0;
      if (x < limit12) y = tracerDroite(m1, b1, x);
      else if (x < limit23) {
      double b2 = tracerDroite(m1, b1, limit12);
      y = tracerDroite(m2, b2, x - limit12);
      } else {
      double b2 = tracerDroite(m1, b1, limit12);
      double b3 = tracerDroite(m2, b2, limit23 - limit12);
      y = tracerDroite(m3, b3, x - limit23);
      }
      return y;
   }

   public static double tracer4DroitesConnectees(double x, double b1, double m1, double m2, double m3, double m4,
         double limit12, double limit23, double limit34){
      double y = 0;
      if (x < limit12) y = tracerDroite(m1, b1, x);
      else if (x < limit23) {
         double b2 = tracerDroite(m1, b1, limit12);
         y = tracerDroite(m2, b2, x - limit12);
      } else if (x < limit34) {
         double b2 = tracerDroite(m1, b1, limit12);
         double b3 = tracerDroite(m2, b2, limit23 - limit12);
         y = tracerDroite(m3, b3, x - limit23);
      } else {
         double b2 = tracerDroite(m1, b1, limit12);
         double b3 = tracerDroite(m2, b2, limit23 - limit12);
         double b4 = tracerDroite(m3, b3, limit34 - limit23);
         y = tracerDroite(m4, b4, x - limit34);
      }
      return y;
   }

   public static double moy(double x, double y) {
      double moyXY = (x + y) / 2d;
      return moyXY;
   }

   public static double round2(double val) {
      val = val * 100;
      val = Math.round(val);
      val = val / 100;
      return val;
   }

   /**
    * Cette fonction permet de faire l'interpolation à partir du vecteur de inputs, calculer selon la
    * règle de 3, et donner le résultat selon l'échelle du vecteur de output. C'est utilisé pour
    * faire la conversion de distance au vélocité de tir à partir d'une série de mesures notées au
    * préalable.
     *
     * <p>À partir de deux vecteurs, exemple:
     *  input  [0   1   2   3]
     *  output [0 100 200 300]
     * Si j'ai une nouvelle input = 2.5, ce serait quoi la output correspondant? Réponse 250.
     *
     * <p>Will return negative value if something went wrong.
     */
   public static double getInterpolatedVal(
      double independantVal, double[] inputArray, double[] outputArray) throws Exception {
      if (inputArray.length <= 1
         || outputArray.length <= 1
         || inputArray.length != outputArray.length)
      throw new Exception("La taille des listes est invalide");

      for (int i = 0; i < inputArray.length - 1; i++) {
      if ((inputArray[i] <= independantVal && independantVal <= inputArray[i + 1])
            || (inputArray[i + 1] <= independantVal && independantVal <= inputArray[i])) {
         double yInterpolated =
            InterpolateY(
                  independantVal,
                  inputArray[i],
                  inputArray[i + 1],
                  outputArray[i],
                  outputArray[i + 1]);
         return yInterpolated;
      }
      }
      return outputArray[(int) (inputArray.length / 2)];
   }

   /**
    * Pour deux pairs de mesure x-y, si on a une valeur de x entre ces mesures, on veut trouver la
    * valeur interpolee de y.
    *
    * @param currentX
    * @param minX
    * @param maxX
    * @param minY
    * @param maxY
    * @return
    */
   public static double InterpolateY(
      double currentX, double minX, double maxX, double minY, double maxY) {
      double m = (maxY - minY) / (maxX - minX);
      double b = (maxY) - (m * maxX);
      double finalVal = m * currentX + b;
      return finalVal;
   }

   public static double roundToDecimal(double valToRound, int nbOfDecimal) {
      if (nbOfDecimal < 0) nbOfDecimal = 0;
      double powerOfTen = Math.pow(10, nbOfDecimal);
      return Math.round(valToRound * powerOfTen) / powerOfTen;
   }

	/**
	 * Add new value to the end of list, pop off the oldest value if list exceeds size limit.
	 */
    public static<T> void addToFixedSizeList(int sizeLimit, ArrayList<T> list, T newVal){
        if(null == list) list = new ArrayList<T>();
        list.add(newVal);
        if(sizeLimit < list.size()) list.remove(0);
    }

    public static int getNbOfTrue(ArrayList<Boolean> list){
        if(null == list) return 0;
        int nb = 0;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i)) nb++;
        }
        return nb;
    }

    public static int getNbOfFalse(ArrayList<Boolean> list){
        if(null == list) return 0;
        int nb = getNbOfTrue(list);
        return list.size() - nb;
    }

    public static int getSumOfInts(ArrayList<Integer> list){
        if(null == list) return 0;
        int sum = 0;
        for(int i = 0; i < list.size(); i ++){
            sum += list.get(i);
        }
        return sum;
    }

    public static double getSumDoubles(ArrayList<Double> list){
        if(null == list) return 0;
        double sum = 0;
        for(int i = 0; i < list.size(); i ++){
            sum += list.get(i);
        }
        return sum;
    }

    public static double getMeanDoubles(ArrayList<Double> list){
        if(null == list) return 0;
        double sum = getSumDoubles(list);
        double mean = sum / list.size();
        return mean;
    }

   /** Returns the average */
   public static double average(double v1, double v2) {
      return (v1 + v2) / 2.0;
   }

   public static Pt2D rotateAroundCenter(double x, double y, double deg) {
      double angleCos = Math.cos(Math.toRadians(deg));
      double angleSin = Math.sin(Math.toRadians(deg));
      Pt2D newPoint = new Pt2D(x * angleCos - y * angleSin, x * angleSin + y * angleCos);
      return newPoint;
   }

   /**
    * When you need to find the shooter RPM for any given distance, you first measure at several
    * distances what the best RPM is, create an array for all these distance-to-RPM pairs, then pass
    * this array and a new distance measured live by a sensor. This function will give you the
    * interpolated RPM between closest recorded distance points.
    *
    * <p>If the live-distance is smaller or higher than the min or max recorded-distance, function
    * will not extrapolate. The recorded array must have ascending order x (ex: distance) values,
    * going from smaller to larger.
    *
    * @param currentX
    * @param arrayOfPoints
    * @return
    */
   public static double interpolateYClosestPoints(double currentX, Pt2D[] arrayOfPoints) {
      if (arrayOfPoints == null || arrayOfPoints.length < 2) return 0;
      if (currentX <= arrayOfPoints[0].x) return arrayOfPoints[0].y;
      if (arrayOfPoints[arrayOfPoints.length - 1].x <= currentX)
      return arrayOfPoints[arrayOfPoints.length - 1].y;

      for (int i = 0; i < arrayOfPoints.length - 1; i++) {
      int nextIndex = i + 1;
      if (arrayOfPoints[i].x <= currentX && currentX <= arrayOfPoints[nextIndex].x) {
         double yVal =
            InterpolateY(
                  currentX,
                  arrayOfPoints[i].x,
                  arrayOfPoints[nextIndex].x,
                  arrayOfPoints[i].y,
                  arrayOfPoints[nextIndex].y);
         return yVal;
      }
      }
      return arrayOfPoints[arrayOfPoints.length - 1].y;
   }

   public static boolean isValueBetween(double val, double limit1, double limit2, double tolerance) {
      tolerance = Math.abs(tolerance);
      double lowPosition = 0;
      double highPosition = 0;
      if (limit1 < limit2) {
      lowPosition = limit1;
      highPosition = limit2;
      } else {
      lowPosition = limit2;
      highPosition = limit1;
      }
      return (lowPosition - tolerance) <= val && val <= (highPosition + tolerance);
   }

   /**
    * From a distance in inches, convert it to meters
    * @param inchValue
    * @return
    */
   public static double toMeter(double inchValue) {
      return inchValue / 39.37;
   }


   public static double inchToMeters(double inchValue){
      return inchValue/39.37;
   }

   /**
    * From a distance in meters, convert it to inches
    * @param inchValue
    * @return
    */
   public static double metersToInch(double metersValue) {
      return metersValue * 39.37;
   }

   /**
    * Find which of the 2 points are closer to basePoint
    *
    * @param basePoint
    * @param option1Point
    * @param option2Point
    * @return 0 = both are same distance, 1 = option 1, 2 = option 2
    */
   public static int findClosestPt2D(Pt2D basePoint, Pt2D option1Point, Pt2D option2Point) {
      double distOption1 = basePoint.distanceTo(option1Point);
      double distOPtion2 = basePoint.distanceTo(option2Point);
      if (distOption1 < distOPtion2) return 1;
      else if (distOPtion2 < distOption1) return 2;
      else return 0;
   }

}
