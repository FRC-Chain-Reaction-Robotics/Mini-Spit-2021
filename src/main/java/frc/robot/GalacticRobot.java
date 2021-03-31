/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.sensors.*;

public class GalacticRobot extends Robot    //  now that is what one would refer to as Big Brain
{
    DistanceSensor d = new DistanceSensor();

	@Override
	public void testInit()
	{
        dt.setMaxOutput(Constants.Drivetrain.autoMaxOutput);

		// okay but this whole thing wouldn't work if we always start in b1-d1
		// 	OOOO how about a starting *angle* change, and we could use the gyro or distance sensor
		//	or start further away from the edge of the field and change that distance

		final double differentDistance = 100; // cm
		
		double currentDistance = takeFiveSamples(d::getDistance);


        // Modify accordingly.
        final boolean isPathB = false;


        final Runnable redPath = isPathB ? skills::selectGalacticPathBRed : skills::selectGalacticPathARed;
        final Runnable bluePath = isPathB ? skills::selectGalacticPathBBlue : skills::selectGalacticPathABlue;

		if (currentDistance < differentDistance)
			skills.init(bluePath);
		else
			skills.init(redPath);
	}


	/**
	 * just to get more accurate sensor readings when used in init() methods
	 * @param getter method reference or lambda that returns a double
	 * @return average of 5 samples of the sensor value
	 */
	public double takeFiveSamples(DoubleSupplier getter)
	{
		double averageOfFiveSamples = 0;
		
		for (int i = 0; i < 5; i++)
		{
			double currentValue = getter.getAsDouble();
			
			averageOfFiveSamples += currentValue;
			SmartDashboard.putNumber("distance", currentValue);
		}
		
		return averageOfFiveSamples;
	}
}
