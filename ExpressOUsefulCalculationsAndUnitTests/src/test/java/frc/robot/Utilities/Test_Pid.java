package frc.robot.Utilities;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class Test_Pid {
   final double tol = 0.0000001;

   @Test
   public void calculateOutput_Proportional() {
      Pid pid = null;
      double out, err;
      for (double kp = 0; kp < 10; kp += 0.001) {
         for (double set = -10; set < 10; set += 0.1) {
               for (double in = -10; in < 10; in += 0.1) {
                  pid = new Pid(kp, 0, 0, 0, 0);
                  pid.setOutputLimits(-99999, 99999);
                  pid.setSetpoint(set);
                  out = pid.calculateOutput(in);
                  err = set-in;
                  debugAssertEqual(err*kp, out, tol);
               }
         }
      }
   }

   @Test
   public void calculateOutput_Integral() throws InterruptedException {
      Pid pid = null;
      double out, out2, err;

//        pid = new Pid(0, 0.1, 0, 0, 1.0);
//        pid.setOutputLimits(-99999, 99999);
//        pid.setSetpoint(-10);
//        out  = pid.calculateOutput(-9.8);
//        err = -0.1999999999999993;
//        Thread.sleep(1);
//        out2 = pid.calculateOutput(-9.8);

      for (double iZone = 0; iZone < 5; iZone += 1) {
         for (double ki = 0; ki < 1; ki += 0.1) {
               for (double set = -5; set < 5; set += 0.3) {
                  for (double in = -5; in < 5; in += 0.3) {
                     pid = new Pid(0, ki, 0, 0, iZone);
                     pid.setOutputLimits(-99999, 99999);
                     pid.setSetpoint(set);
                     out = pid.calculateOutput(in);
                     out = pid.calculateOutput(in);
                     err = set - in;

                     if (Math.abs(err) <= 0 || iZone < Math.abs(err) || Math.abs(ki - tol) <= tol) {
                           debugAssertEqual(0, out, tol);
                     } else {
                           Thread.sleep(1);
                           out2 = pid.calculateOutput(in);
                           if (err < 0) {
                              if (out <= out2)
                                 assertTrue(out2 < out); //should not land here
                              else
                                 assertTrue(out2 < out);
                           } else {
                              if (out2 <= out)
                                 assertTrue(out < out2); //should not land here
                              else
                                 assertTrue(out < out2);
                           }
                     }
                  }
               }
         }
      }
   }

   @Test
   public void calculateOutput_FeedForward() {
      double pid_kP = 0;
      double pid_kI = 0;
      double pid_kD = 0;
      double pid_kIZone = 0; //degrees

      Pid pid = new Pid(pid_kP, pid_kI, pid_kD, 0, pid_kIZone);
      pid.setOutputLimits(-99999, 99999);

      double out, err;
      for (double minE = 0; minE < 10; minE += 0.1) {
         for (double ff = 0; ff < 10; ff += 0.1) {
               for (double set = -10; set < 10; set += 0.1) {
                  for (double in = -10; in < 10; in += 0.1) {
                     pid.setFeedForward(ff, minE);
                     pid.setSetpoint(set);
                     out = pid.calculateOutput(in);
                     err = set-in;
                     if (Math.abs(err) <= minE){
                           debugAssertEqual(0, out, tol);
                     } else {
                           if (err < 0) debugAssertEqual(-ff, out, tol);
                           else         debugAssertEqual( ff, out, tol);
                     }

                  }
               }
         }
      }
   }

   /**
    * This function is only to allow pausing with a breakpoint a code that's going to fail.
    */
   private void debugAssertEqual(double expected, double actual, double tolerance){
      if (Math.abs(expected-actual) <= tolerance) return;
      assertEquals( expected, actual, tolerance);
   }
}