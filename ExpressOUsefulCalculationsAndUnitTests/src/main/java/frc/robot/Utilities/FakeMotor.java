package frc.robot.Utilities;

/**
 * This motor equations are inspired from DC motor models:
 *      https://docs.wpilib.org/en/2020/docs/software/wpilib-tools/robot-characterization/introduction.html
 * and other sites talking about DC Mnotor's math models.
 *
 */
public class FakeMotor {

   // public final double ks;
   public final double ka;
   public final double ksAt0; // back-emf: resisting voltage
   public final double kRatio;
   double volt         = 0;
   double position     = 0; // native encoder unit
   double velocity     = 0; // native encoder unit
   double acceleration = 0; // native encoder unit

   /**
    * Example constants:
    *    [0 to max:  2s; max to 0:  1s] :  ks= 3.16; ka=3.852; kEmf=0.046
    *    [0 to max: 18s; max to 0: 18s] :  ks=0.002; ka=0.742; kEmf=0.005
    *
    * @param ks   positive; it's the friction force that will always opposite velocity; cannot be 0, higher than 0.000_001
    * @param ka   positive; it's the acceleration proportional to input voltage; cannot be 0, higher than 0.000_001
    * @param kRatio positive, encoder unit to required user unit; pure scaling only applied at output
    */
   public FakeMotor(double ka, double ksAt0, double kRatio){//double ks, double ka, double ksAt0){
      // this.ks     = Math.max(0.000_000_001, Math.abs(ks));
      this.ka     = Math.max(0.000_001, Math.abs(ka));
      this.ksAt0  = Math.max(0.000_001, Math.abs(ksAt0));
      this.kRatio = setEncoderToUserRatio(kRatio);
   }

   public void update(double _volt, double sec){
      volt = _volt;
      acceleration = ka * volt;
      velocity = velocity*kRatio + (acceleration * sec);

      // Special case: adjust friction if voltage is close to neutral (0 volts): forced breaking by CTRE
      double velocity_from_breaking = 0;
      if (Math.abs(volt) < 0.001)
         velocity_from_breaking = get_same_sign_and_smaller(velocity, ksAt0);
      velocity = velocity - velocity_from_breaking;

      // Update position
      position += velocity * sec;
   }

   private double get_same_sign_and_smaller(double ref, double val){
      return Math.copySign(Math.min(Math.abs(ref), Math.abs(val)), ref);
   }

   /**
    * Only tune this ratio after finished tuning all other constants.
    * This is a pure scaling, will not affect motor acceleration or velocity behaviors.
    */
   public double setEncoderToUserRatio(double kRatio){
      return Math.max(0.000_001, Math.min(999_999, Math.abs(kRatio)));
   }

   public double getRawPosition     (){ return position    ; }
   public double getRawVelocity     (){ return velocity    ; }
   public double getRawAcceleration (){ return acceleration; }
   public double getUserPosition    (){ return position    ; }// * kRatio; }
   public double getUserVelocity    (){ return velocity    ; }// * kRatio; }
   public double getUserAcceleration(){ return acceleration; }// * kRatio; }

}
