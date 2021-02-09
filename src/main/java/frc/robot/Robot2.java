/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import static edu.wpi.first.wpilibj.GenericHID.Hand.*;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;

public class Robot2 extends TimedRobot
{
    XboxController driverController = new XboxController(0);
    Mecanum dt = new Mecanum();

    @Override
    public void teleopPeriodic()
    {
        dt.drive(-driverController.getX(kLeft), -driverController.getY(kLeft),
				driverController.getX(kRight));
    }
}
