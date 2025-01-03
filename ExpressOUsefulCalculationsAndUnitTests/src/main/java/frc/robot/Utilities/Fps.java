package frc.robot.Utilities;

/**
 * Calculate how much time elapsed since last time getFps() was called.
 */
public class Fps {
   long lastNanoTimeAtLastSecond = 0;
   final double nbNanoInSecondDouble = 1_000_000_000d;
   double fps = 0;
   double lastDeltaNanoTime = 1;
   double weightOfCurrentElapsedTime = 0.8; // must be between 0 and 1, inclusively. To smooth out fast variations

   /**
    * Create a weighted Fps calculator, where the last elapsed time is *averaged* with previous elapsed time.
    * If you want a simple average, use 0.5 (50%).
    * If you want to add more weight to the most recent elapsed time so it reacts quicker to changes,
    * use anything between 0.5 and 1.0.
    * If you want a smoother FPS, use a weight between 0.1 and 0.5.
    * @param _weightOfCurrentElapsedTime Give a value between 0.01 and 1.00
    */
   public Fps(double _weightOfCurrentElapsedTime){
      init(_weightOfCurrentElapsedTime);
   }

   private void init(double _weightOfCurrentElapsedTime){
      lastNanoTimeAtLastSecond = System.nanoTime();
      fps = 0;
      weightOfCurrentElapsedTime = _weightOfCurrentElapsedTime;

      // protection so values don't exceed 0.01 ~ 1.0
      if (weightOfCurrentElapsedTime <= 0) weightOfCurrentElapsedTime = 0.01;
      if (1  < weightOfCurrentElapsedTime) weightOfCurrentElapsedTime = 1.00;
   }

   /**
    * Call this function at each cycle to trigger internal elapsed time calculations
    * @return
    */
   public double getFps() {
      long currentNanoTime = System.nanoTime();
      long deltaNanoTime = currentNanoTime - lastNanoTimeAtLastSecond;
      lastNanoTimeAtLastSecond = currentNanoTime;
      if (deltaNanoTime <= 0) deltaNanoTime = 1; // ensures a minimum of 1 to avoid division by 0

      double combined = (weightOfCurrentElapsedTime * deltaNanoTime) +
               ((1-weightOfCurrentElapsedTime) * lastDeltaNanoTime);

      fps = nbNanoInSecondDouble / combined;
      lastDeltaNanoTime = deltaNanoTime; // prepare for next call
      return Util.trimDecimals4(fps);
   }

}
