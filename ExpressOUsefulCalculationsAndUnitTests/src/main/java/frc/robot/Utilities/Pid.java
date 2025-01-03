package frc.robot.Utilities;

/** These calculations are inspired from this page:
 * http://brettbeauregard.com/blog/2011/04/improving-the-beginner%e2%80%99s-pid-reset-windup/
 */
public class Pid {
   long lastTime;
   double input = 0, lastInput = Double.NaN; // NaN allows us to detect a non-usable value at first time
   double setpoint = 0; // on veut centrer la cible, 0 degree
   double ITerm = 0;
   double kp = 0, ki = 0, kd = 0, iZone = 99999999;
   double outMin = -99999999, outMax = 99999999;

   double feedForward_positif = 0;
   double feedForwardMinError = 99999999; // error must be higher than this threshold for FF to be applied
   double FFTerm = 0; //term to apply

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
      FFTerm = 0;
      lastInput = input;
   }


   /**
    * Simplified constructor to only use PID constants
    * @param _kp Proportional ratio, unit is same as setpoint's unit divide by output's unit
    * @param _ki Integral ratio, unit is same as setpoint's unit
    * @param _kd Derivative ratio, unit is same as setpoint's unit
    * @param _setpoint Target this sensor value in the futur
    */
   public Pid(double _kp, double _ki, double _kd, double _setpoint) {
      init(_kp, _ki, _kd, _setpoint, 99999999);
   }

   /**
    * Complete constructor to customize all PID parameters
    * @param _kp Proportional ratio, unit is same as setpoint's unit divide by output's unit
    * @param _ki Integral ratio, unit is same as setpoint's unit
    * @param _kd Derivative ratio, unit is same as setpoint's unit
    * @param _iZone Only start calculating integral term if error is smaller than this value. Positive value only.
    */
   public Pid(double _kp, double _ki, double _kd, double _setpoint, double _iZone) {
      init(_kp, _ki, _kd, _setpoint, _iZone);
   }

   private void init(double _kp, double _ki, double _kd, double _setpoint, double _iZone) {
      lastTime = System.currentTimeMillis();
      kp = _kp;
      ki = _ki;
      kd = _kd;
      setpoint = _setpoint;
      iZone = Math.abs(_iZone);
   }

   /**
    * @param _kp Proportional ratio, unit is same as setpoint's unit divide by output's unit
    * @param _ki Integral ratio, unit is same as setpoint's unit
    * @param _kd Derivative ratio, unit is same as setpoint's unit
    */
   public void setParamsPID(double _kp,double _ki, double _kd) {
      kp = _kp;
      ki = _ki;
      kd = _kd;
   }

   /**
    * This allows PID to apply a minimum output. Ex: to counter effect of gravity on an arm.
    *
    * Use the FFMinError to create a zone around setpoint where no FF will be applied to output,
    * to prevent constant flipping between FF_plus and FF_minus. Ex: for drive bases.
    * @param _feedForward If output is negative and smaller than -FFMinError, add this value in negative.
    *                     If output is positive and higher than FFMinError, add this value in positive. Set to 0 to remove FF's effect.
    * @param _feedForwardMinError Don't add FeedForward value if abs(error) is smaller than this threshold.
    *                             Positive value only. Set to infinity to completely remove FF's effect.
    */
   public void setFeedForward(double _feedForward, double _feedForwardMinError) {
      feedForward_positif = Math.abs(_feedForward);
      feedForwardMinError = Math.abs(_feedForwardMinError);
   }

   /**
    * Ex: Prevent motor speed from exceeding these values when aiming for the set position in pulses.
    * @param Min the integral term and output will both never be smaller than this value
    * @param Max the integral term and output will both never be higher than this value
    */
   public void setOutputLimits(double Min, double Max) {
      outMin = Math.min(Min, Max);
      outMax = Math.max(Min, Max);

      if(ITerm > outMax) ITerm = outMax;
      else if(ITerm < outMin) ITerm = outMin;

      if (output > outMax) output = outMax;
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

      // Integral term
      if (Math.abs(error) <= iZone){
         double elapsedS = getElapsedTimeMs() / 1000.0;
         ITerm += ki * error * elapsedS; //
      } else {
         ITerm = 0; // reset integral term if error is too big, so integral only adjust for a remaining small error
      }

      // prevent integral from becoming too large
      if     (outMax < ITerm) ITerm = outMax;
      else if(ITerm < outMin) ITerm = outMin;

      // Derivative term
      double dInput = (input - lastInput);

      // FeedForward term
      FFTerm = 0;
      if (feedForwardMinError < Math.abs(error)){
         if (error < 0) FFTerm = -feedForward_positif;
         else           FFTerm =  feedForward_positif;
      }

      // Compute PID Output -----------------------------------
      outputP = kp * error;
      outputI = ITerm;
      outputD = -kd * dInput;
      outputFF = FFTerm;
      output = outputP + outputI + outputD + outputFF;
      if      (outMax < output) output = outMax;
      else if (output < outMin) output = outMin;

      // Remember some variables for next time
      lastInput = input;
      lastTime = now;

      return output;
   }

}
