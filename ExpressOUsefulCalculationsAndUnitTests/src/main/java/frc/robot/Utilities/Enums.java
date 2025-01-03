package frc.robot.Utilities;

/**
 * These states low-level, hardware specific information,
 * Keeping these states as short as possible will help reduce code complexity.
 */
public class Enums {
   
   public enum mode{
      auto,
      pilot,
   }
   public enum extender {
      none ,
      base ,
      close,
      far  ,
   }
   public enum claw {
      close,
      open,
   }
   public enum wrist {
      up,
      down,
   }

   public enum intake {
      stopped,
      ingest,
      eject,
   }
   public enum elevator {
      none,
      ground,
      intake,
      raiseIntake,
      lowBar,
      lowPlace,
      highBar,
      highPlace,
      preClimb,
      climbBegin,
      climbFinish,
      lowBasket,
      highBasket,
      max,
   }
}
