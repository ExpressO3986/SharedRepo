package frc.robot.TrajectoryGenerator;

import frc.robot.Utilities.Pt2D;
import java.util.ArrayList;

/** Define spots on the field the robot cannot physically go into */
public class RestrictedArea {
   private ArrayList<Pt2D> pointsRestrictedArea = null;

   public RestrictedArea(ArrayList<Pt2D> _restrictedArea){
      pointsRestrictedArea = _restrictedArea;
   }

   /**
    *
    * @return null if no points
    */
   public ArrayList<Pt2D> get(){
      if(pointsRestrictedArea == null) return null;
      return pointsRestrictedArea;
   }

   /**
    *
    * @param index
    * @return null if no points
    */
   public Pt2D getPt(int index){
      if(pointsRestrictedArea == null) return null;
      return pointsRestrictedArea.get(index);
   }

   public int getSize(){
      return pointsRestrictedArea.size();
   }

}
