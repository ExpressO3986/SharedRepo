package frc.robot.TrajectoryGenerator;

import frc.robot.Utilities.Pt2D;
import java.util.ArrayList;

/**
 * Une autoroute est un trajectoire préféré autour d'un obstacle, il est toujours bidirectionnel.
 * Liste de points bidirectionnel qu'on peut entrer et sortir mais il faut les suivre en ordre.
 */
public class Highway {
   private ArrayList<Pt2D> boundingCornerPoints = null;

   public Highway(ArrayList<Pt2D> _boundingCornerPoints){
      boundingCornerPoints = _boundingCornerPoints;

   }

   public ArrayList<Pt2D> get(){
      return boundingCornerPoints;
   }

   public Pt2D getPt(int pt){
      return boundingCornerPoints.get(pt);
   }

   public int getSize(){
      return boundingCornerPoints.size();
   }

}
