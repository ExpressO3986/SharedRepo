package frc.robot.Utilities;

import java.util.ArrayList;

/**
 * Outputs:
 * Pose d'arrivé
 * Prochain pt2D
 * Distance total
 *
 * art ascii de Thav
 *
 *
 * Start O----------------------O 0
 *                 0            |
 *                              |
 *                              |
 *                              |
 *                            1 |
 *                              |
 *                              |
 *                              |
 *                              | 1
 *                              O----------------------------O Ending
 *                                             2
 *
*/
public class Traj2D {
   // Inputs: a recevoir par les constructeurs
   Pt2D[] wayPointsList = null; // jamais nulle, peut être de taille 0
   // double velocity = 0; // 0 = arrêt
   double endOrientation = 999; //field oriented in degree -180 to 180

   // Variables internes ou calculees
   double[] lengths = null; //jamais nulle, peut être de taille 0, toujours 1 de plus que taille de wayPoints

   /**
    * Constructer for 1 line between 2 points
    * @param _start First point
    * @param _end Last point
    * @param _reverse Reverse the direction of the highway trajectory
    */
   public Traj2D(Pt2D _start, Pt2D _end){
      wayPointsList = new Pt2D[2];
      wayPointsList[0]  = _start;
      wayPointsList[1]  = _end;

      lengths = calculateLength(wayPointsList);
   }

   /**
    * Constructer with all points
    * @param _waypoints First point = Start, All points in between = waypoints, last point = End
    * @param _reverse Reverse the direction of the highway trajectory
    */
   public Traj2D(ArrayList<Pt2D> _waypoints){
      wayPointsList = new Pt2D[_waypoints.size()];

      for(int i = 0; i < _waypoints.size(); i++){
         wayPointsList[i] = _waypoints.get(i);
      }

      lengths = calculateLength(wayPointsList);
   }

   /**
    * Constructer for one line + end direction of the robot and end velocity
    * @param _start
    * @param _end
    * @param _reverse
    * @param _endVelocity
    * @param _endAngleDeg
    */
   public Traj2D(ArrayList<Pt2D> _waypoints,  double _endAngleDeg){
      endOrientation = _endAngleDeg;

      wayPointsList = new Pt2D[_waypoints.size()];

      for(int i = 0; i < _waypoints.size(); i++){
         wayPointsList[i] = _waypoints.get(i);
      }

      lengths = calculateLength(wayPointsList);
   }

   public Traj2D(Pt2D _start, Pt2D _end, double _endVelocity, double _endAngleDeg){

      wayPointsList = new Pt2D[2];
      endOrientation = _endAngleDeg;

      wayPointsList[0]  = _start;
      wayPointsList[1]  = _end;

      lengths = calculateLength(wayPointsList);
   }

   /**
    * set an end orientation to the Traj2d
    * @param angle
    */
   public void setEndOrientation(double angle){
      endOrientation = angle;
   }

   /**
    * @param _currentIndex Current index on the list of points
    * @return waypoints to attend on current index
    */
   public Pt2D get(int _currentIndex){
      if(_currentIndex > wayPointsList.length-1 || _currentIndex < 0){
         return null;
      }
      return wayPointsList[_currentIndex];
   }

   /**
    * @return Distance from the start to the end
    */
   public double getTotalDistance() {
      if (lengths == null || lengths.length == 0) {
         return 0;
      }

      if (lengths.length == 0) {
         return 0;
      }

      double totalDistance = 0;
      for (int i = 0; i < lengths.length; i++) {
         totalDistance += lengths[i];
      }
      return totalDistance;
   }

   /**
    * @return Amount of waypoints in the trajectory
    */
   public int getListSize(){
      if(wayPointsList == null){
         return 0;
      }
      return wayPointsList.length;
   }

   /**
    * True if both trajectory are the same
    * False if both trajectory are not the same
    * @param trajectoire Trajectory to compare to
    * @param tolerance Tolerance between points
    * @return
    */
   public boolean areEqual(Traj2D trajectoire, double tolerance){
      if(!(wayPointsList.length == trajectoire.getListSize())){return false;}
      for(int i = 0; i <= trajectoire.getListSize(); i++ ){
         if(!(wayPointsList[i].areEqual(trajectoire.get(i), tolerance))){
               return false;
         }
      }
      return true;
   }

   /**
    *
    * @return Point list in ArrayList
    */
   public ArrayList<Pt2D> getArrayList(){
      ArrayList<Pt2D> points = new ArrayList<Pt2D>();
      for(int i = 0; i < wayPointsList.length; i++){
         points.add(wayPointsList[i]);
      }
      return points;
   }

   /**
    * Store distances between each pair of waypoints.
    * @param _waypoints will never return null, zero-sized array if input is invalid
    * @return
    */
   public static double[] calculateLength(Pt2D[] _waypoints){
      double[] newLengths = new double[0];
      if (null == _waypoints || _waypoints.length <= 1)
         return newLengths;

      newLengths = new double[_waypoints.length-1];
      for(int i = 0; i < newLengths.length; i++){
         newLengths[i] = _waypoints[i].distanceTo(_waypoints[i + 1].x, _waypoints[i + 1].y);
      }

      return newLengths;
   }
}
