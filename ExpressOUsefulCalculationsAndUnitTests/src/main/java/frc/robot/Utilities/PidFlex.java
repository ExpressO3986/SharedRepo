package frc.robot.Utilities;

/**
 * This pid has a 2 stage kp. kp1 and kp2. The transition between the 2 is at a kflex point
 * on the error axis:
 *                                      ^
 *                                      |
 *                                      |               ------
 *                                      |         ------
 *                                      |   ------
 *                                      |  /
 *                                      | /
 * -------------------------------------|---|------------------------------>
 *                                kp1 / |   _kflex
 *                                   /  |
 *                      kp2   ------    |
 *                      ------          |
 *                ------                |
 *                                      |
 *                                      v
 * Inpired from:
 * http://brettbeauregard.com/blog/2011/04/improving-the-beginner%e2%80%99s-pid-reset-windup/
 */
public class PidFlex {
   long lastTime;
   double input = 0, lastInput = Double.NaN; // NaN allows us to detect a non-usable value at first time
   double setpoint = 0; // on veut centrer la cible, 0 degree
   double ITerm = 0;
   double kp1 = 0, kflex = 0, kp2 = 0;
   double ki = 0, kd = 0, iZone = 0;
   double outMin = -99999999, outMax = 99999999;

   // Allows reading of each component of the input/output for analysis purpose
   private double output  = 0; public double getOutput  (){ return output  ; }
   private double outputP = 0; public double getOutputP (){ return outputP ; }
   /**
    * Get the current Integral's contribution to output
    */
   private double outputI = 0; public double getOutputI (){ return outputI ; }
   private double outputD = 0; public double getOutputD (){ return outputD ; }
   /**
    * Get the current Feed Forward's contribution to output
    */
   private double outputFF= 0; public double getOutputFF(){ return outputFF; }
   public double getInput  (){ return input; }
   public void setSetpoint(double _setpoint) {
      setpoint = _setpoint;
   }
   public double getSetpoint() {
      return setpoint;
   }
   public void resetOutputs(double _setpoint) {
      setpoint = _setpoint;
      ITerm = 0;
      lastInput = input;
   }

   /**
    * This pid has a 2 stage kp. kp1 and kp2. The transition between the 2 is at a kflex point
    * on the error axis. See class's ascii art for the graph.
    *
    * @param _kp1 Proportional ratio, unit is same as setpoint's unit divide by output's unit
    * @param _kflex Proportional ratio, unit is same as setpoint's unit divide by output's unit
    * @param _kp2 Proportional ratio, unit is same as setpoint's unit divide by output's unit
    * @param _ki Integral ratio, unit is same as setpoint's unit
    * @param _kd Derivative ratio, unit is same as setpoint's unit
    * @param _iZone Only start calculating integral term if error is smaller than this value. Positive value only.
    * Ex: Prevent motor speed from exceeding these values when aiming for the set position in pulses.
    */
   public PidFlex(double _kp1, double _kflex, double _kp2, double _ki, double _kd, double _iZone) {
      init(_kp1, _kflex, _kp2, _ki, _kd, _iZone);
      setSetpoint(0);
   }

   private void init(double _kp1, double _kflex, double _kp2, double _ki, double _kd, double _iZone) {
      lastTime = System.currentTimeMillis();
      kp1 = _kp1;
      kflex = _kflex;
      kp2 = _kp2;
      ki = _ki;
      kd = _kd;
      iZone = Math.abs(_iZone);
   }

   /**
    * Allows for live tuning of PID constants
    */
   public void setConstants(double _kp1, double _kflex, double _kp2, double _ki, double _kd, double _iZone) {
      init(_kp1, _kflex, _kp2, _ki, _kd, _iZone);
   }

   /**
    * Ex: Prevent motor speed from exceeding these values when aiming for the set position in pulses.
    * @param Min the integral term and output will both never be smaller than this value
    * @param Max the integral term and output will both never be higher than this value
    */
   public void setOutputLimits(double Min, double Max) {
      outMin = Math.min(Min, Max);
      outMax = Math.max(Min, Max);

      if      (ITerm > outMax) ITerm = outMax;
      else if (ITerm < outMin) ITerm = outMin;

      if      (output > outMax) output = outMax;
      else if (output < outMin) output = outMin;
   }

   /**
    * @return Time in milliseconds since last time the loop called to calculate output
    */
   public long getElapsedTimeMs() {
      return  System.currentTimeMillis() - lastTime;
   }

   /**
    * Call this function once each loop with the current sensor reading.
    * The pid controller will return the required output to apply on
    * the actuator's (ex: motor), so futur sensor reading will approach
    * the setpoint.
    * @param newInput
    * @return
    */
   public double calculateOutput(double newInput) {
      input = newInput;
      if (Double.isNaN(lastInput)) lastInput = input; // to make sure the first time we don't cause a spike in output

      // How long since we last calculated
      long now = System.currentTimeMillis();

      // Compute all the working error variables
      double error = setpoint - input;
      double absError = Math.abs(error);

      // Integral term
      if (absError <= iZone){
         double elapsedS = getElapsedTimeMs() / 1000.0;
         ITerm += ki * error * elapsedS; //
      } else {
         ITerm = 0; // reset integral term if error is too big, so integral only adjust for a remaining small error
      }

      // prevent integral from becoming too large
      if      (outMax < ITerm) ITerm = outMax;
      else if (ITerm < outMin) ITerm = outMin;

      // Derivative term
      double dInput = (input - lastInput);

      // Compute PID Output -----------------------------------
      if (absError <= kflex) outputP = kp1 * absError;
      else                   outputP = kp1 * kflex + kp2 * (absError-kflex);
      if (error < 0) outputP *= -1;

      outputI = ITerm;
      outputD = -kd * dInput;
      output = outputP + outputI + outputD + outputFF;
      if      (outMax < output) output = outMax;
      else if (output < outMin) output = outMin;

      // Remember some variables for next time
      lastInput = input;
      lastTime = now;

      return output;
   }

}
