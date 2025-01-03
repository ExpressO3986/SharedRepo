package frc.robot.Utilities;

public class SuperiorSlewRate {
   private double m_positiveRateLimit;
   private double m_negativeRateLimit;
   private double m_preval;
   private double m_prevTime;

   /**
    * Creates a new SlewRateLimiter with the given positive and negative rate limits and initial
    * value.
    *
    * @param positiveRateLimit The rate-of-change limit in the positive direction, in units per
    *     second. This is expected to be positive.
    * @param negativeRateLimit The rate-of-change limit in the negative direction, in units per
    *     second. This is expected to be negative.
    * @param initialValue The initial value of the input.
    */
   public SuperiorSlewRate(double positiveRateLimit, double negativeRateLimit, double initialValue) {
      m_positiveRateLimit = positiveRateLimit;
      m_negativeRateLimit = negativeRateLimit;
      m_preval = initialValue;
      m_prevTime = initialValue;
   }

   /**
    * Creates a new SlewRateLimiter with the given positive rate limit and negative rate limit of
    * -rateLimit.
    *
    * @param rateLimit The rate-of-change limit, in units per second.
    */
   public SuperiorSlewRate(double rateLimit) {
      this(rateLimit, -rateLimit, 0);
   }

   /**
    * Filters the input to limit its slew rate.
    *
    * @param input The input value whose slew rate is to be limited.
    * @return The filtered value, which will not change faster than the slew rate.
    */
   public double calculate(double input) {
      double currentTime = System.currentTimeMillis() * 1e-3;
      double elapsedTime = currentTime - m_prevTime;
      m_preval += UMath.clamp(input - m_preval, m_negativeRateLimit * elapsedTime, m_positiveRateLimit * elapsedTime);
      m_prevTime = currentTime;
      return m_preval;
   }

   /**
    * Resets the slew rate limiter to the specified value; ignores the rate limit when doing so.
    *
    * @param value The value to reset to.
    */
   public void reset(double value) {
      m_preval = value;
      m_prevTime = System.currentTimeMillis() * 1e-3;
   }

   /**
    * Modify slew rate slops.
    */
   public void setLimits(double positiveRateLimit, double negativeRateLimit) {
      m_positiveRateLimit = positiveRateLimit;
      m_negativeRateLimit = negativeRateLimit;
   }
}
