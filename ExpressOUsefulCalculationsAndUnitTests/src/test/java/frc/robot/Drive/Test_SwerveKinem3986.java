package frc.robot.Drive;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import frc.robot.Utilities.Pt2D;

/** Add your docs here. */
public class Test_SwerveKinem3986 {

    @Test
    public void simpleCases() {

        // Assume a square drive base, modules are at 1 meter in x and y from the center
        //
        //      ---------------------------------------------             
        //      |    ^ +x                             ^     |             
        //      |    |                                |     |             
        //      | <--o                                o-->  |             
        //      |                                       +x  |             
        //      |                  +y ^                     |             
        //      |                     |                     |             
        //      |                     |                     |             
        //      |               drive o---->                |             
        //      |               center    +x                |             
        //      |                                           |             
        //      |                                     ^     |             
        //      |                                     |     |             
        //      |    o-->                             o-->  |             
        //      |    |                                  +x  |             
        //      | +x v                                      |             
        //      ---------------------------------------------        

        SwerveKinem3986 swerveTopRight    = new SwerveKinem3986(new Pt2D(1d, 1d));
        SwerveKinem3986 swerveTopLeft     = new SwerveKinem3986(new Pt2D(-1d, 1d));
        SwerveKinem3986 swerveBottomRight = new SwerveKinem3986(new Pt2D(1d, -1d));
        SwerveKinem3986 swerveBottomLeft  = new SwerveKinem3986(new Pt2D(-1d, -1d));

        // case: drive straight toward +y
        swerveTopRight   .calculateWheelSpeedAngle(0, 1, 0);
        swerveTopLeft    .calculateWheelSpeedAngle(0, 1, 0);
        swerveBottomRight.calculateWheelSpeedAngle(0, 1, 0);
        swerveBottomLeft .calculateWheelSpeedAngle(0, 1, 0);
        
        // assume modules are angled as drawn in diagram above
        swerveTopRight   .calculateOptimizedAngle(0);
        swerveTopLeft    .calculateOptimizedAngle(+89.9999);
        swerveBottomRight.calculateOptimizedAngle(0);
        swerveBottomLeft .calculateOptimizedAngle(-90.0001);

        // verify results
        assertEquals(1, swerveTopRight   .getVelocityOptimSwerve(), 0.001);
        assertEquals(1, swerveTopLeft    .getVelocityOptimSwerve(), 0.001);
        assertEquals(1, swerveBottomRight.getVelocityOptimSwerve(), 0.001);
        assertEquals(-1, swerveBottomLeft .getVelocityOptimSwerve()); // angle and speed are inverted

        assertEquals(90, swerveTopRight   .getAngleOptimSwerve(), 0.001);
        assertEquals(90, swerveTopLeft    .getAngleOptimSwerve(), 0.001);
        assertEquals(90, swerveBottomRight.getAngleOptimSwerve(), 0.001);
        assertEquals(-90, swerveBottomLeft .getAngleOptimSwerve(), 0.001); // angle and speed are inverted
    }

}
