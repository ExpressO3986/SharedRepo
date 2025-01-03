package frc.robot.Utilities;

/**
 * Classe où on réunie les fonctions utiles que tout le monde peut avoir besoin, sans avoir à les
 * réécrire dans chaque sous-système
 */
public class Util {

   /** Si deux valeurs décimal sont suffisamment proche, on dit qu'ils sont égales. */
   public static boolean areEqual(double val1, double val2) {
      return Math.abs(val1 - val2) < 0.001;
   }

   /** Si deux valeurs décimal sont suffisamment proche, on dit qu'ils sont égales. */
   public static boolean areEqual(double val1, double val2, double tol) {
      return Math.abs(val1 - val2) < tol;
   }


   public static double trimDecimals0(double longDouble) {
      int temp = Math.round((float) (longDouble));
      return ((double) temp);
   }

   public static double trimDecimals1(double longDouble) {
      int temp = Math.round((float) (longDouble * 10.0));
      return ((double) temp) / 10.0;
   }
   /**
   * Useful to when printing. So instead of printing 12.023121651313 on Driver Station, we print
   * 12.02, rounded to last decimal.
   *
   * @return
   */
   public static double trimDecimals2(double longDouble) {
      int temp = Math.round((float) (longDouble * 100.0));
      return ((double) temp) / 100.0;
   }

   public static double trimDecimals3(double longDouble) {
      int temp = Math.round((float) (longDouble * 1000.0));
      return ((double) temp) / 1000.0;
   }

   public static double trimDecimals4(double longDouble) {
      int temp = Math.round((float) (longDouble * 10000.0));
      return ((double) temp) / 10000.0;
   }
}
