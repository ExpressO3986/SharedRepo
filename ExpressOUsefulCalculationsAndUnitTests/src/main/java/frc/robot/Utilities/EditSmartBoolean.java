package frc.robot.Utilities;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Mettre un boolean modifiable sur SmartDashboard avec le nom spécifié.
 * Peridiquement effectue une lecture avec getValue().
 * getHasChanged() retourne true si la valeur a changé depuis la dernière lecture.
*/
public class EditSmartBoolean {

   private boolean hasChanged  = false;
   private String variableName = "";

   private boolean oldValue = false;

   public EditSmartBoolean(String _variableName, boolean defaultValue) {
      SmartDashboard.putBoolean(_variableName, defaultValue);
      variableName = _variableName;
      oldValue = defaultValue;
   }

   public boolean getHasChanged() {
      if (!hasChanged) return false;
      hasChanged = false;
      return true;
   }

   public boolean getValue() {
      boolean newValue = SmartDashboard.getBoolean(variableName, oldValue);
      if (newValue != oldValue) hasChanged = true;

      oldValue = newValue;
      return oldValue;
   }

}
