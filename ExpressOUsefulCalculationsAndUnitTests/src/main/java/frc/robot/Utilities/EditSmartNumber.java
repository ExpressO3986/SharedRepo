package frc.robot.Utilities;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Mettre un nombre modifiable sur SmartDashboard avec le nom spécifié.
 * Peridiquement effectue une lecture avec getValue().
 * getHasChanged() retourne true si la valeur a changé depuis la dernière lecture.
*/
public class EditSmartNumber {

   private boolean hasChanged = false;
   private String variableName = "";

   private double oldValue = 0;

   public EditSmartNumber(String _variableName, double defaultValue) {
      SmartDashboard.putNumber(_variableName, defaultValue);
      variableName = _variableName;
      oldValue = defaultValue;
   }

   public boolean isChanged() {
      getValue();
      if (!hasChanged) return false;
      hasChanged = false;
      return true;
   }

   public double getValue() {
      final double tolerance = 0.000002; // may need to pass in input if you need a different tolerance
      double newValue = SmartDashboard.getNumber(variableName, oldValue);
      if (!UMath.equalsApprox(newValue, oldValue, tolerance)) hasChanged = true;
      oldValue = newValue;
      return oldValue;
   }

}
