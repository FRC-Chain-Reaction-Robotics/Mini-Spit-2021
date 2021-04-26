/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static edu.wpi.first.wpilibj.GenericHID.Hand.*;

import edu.wpi.first.wpilibj.SlewRateLimiter;

import frc.robot.subsystems.*;
import frc.robot.sensors.*;

public class Robot extends TimedRobot
{
	Limelight ll = new Limelight();
	DistanceSensor d = new DistanceSensor();
	Mecanum dt = new Mecanum(ll);
	Shooter shooter = new Shooter();
	BallMgmt loader = new BallMgmt();
	// Intake intake = new Intake();
	XboxController driverController = new XboxController(0);
	Skillz skills = new Skillz(dt);

	@Override
	public void robotInit()
	{
		dt.resetSensors();
	}

	@Override
	public void robotPeriodic()
	{
		dt.displayEncoders();
		dt.displayGyro();
		ll.Update();
		ll.shuffleboardUpdate();
		SmartDashboard.putNumber("ultrasonic", d.getDistance());
	}

	@Override
	public void teleopInit()
	{
		dt.resetSensors();
		dt.setMaxOutput(Constants.Drivetrain.teleopMaxOutput);
	}

	SlewRateLimiter forwardLimiter = new SlewRateLimiter(1/(2^16));	//	2^16 = 65536

	@Override
	public void teleopPeriodic()
	{
		//#region intake
		// if (driverController.getPOV(0) == 0)
		// 	intake.up();
		// else if (driverController.getPOV(0) == 180)
		// 	intake.down();
		// else
		// 	intake.freezePosition();


		// if (driverController.getBumper(Hand.kRight))
		// 	intake.on();
		// else if (driverController.getBumper(Hand.kLeft))
		// 	intake.reverse();
		// else
		// 	intake.off();
		//#endregion

		if (driverController.getTriggerAxis(Hand.kLeft) > 0)
		{
			if (driverController.getAButton())
				shooter.greenShot();
			else if (driverController.getYButton())
				shooter.yellowShot();
			else if (driverController.getXButton())
				shooter.blueShot();
			else if (driverController.getBButton())
				shooter.redShot();
			else
				shooter.stop();
		}
		
		
		//#region shooting
		if (driverController.getXButton())
		{
            dt.aim();
		}
		else
		{

			//#region driving
			double triggerValue = driverController.getTriggerAxis(Hand.kRight);
					
			final double minOutput = 0.2;
			double output = (1 + minOutput) - triggerValue;	//	the more you press it, the lower the output is

			dt.setMaxOutput(output);

			double forwardPower = -driverController.getY(kLeft);
			double strafePower = -driverController.getX(kLeft);
			double turnPower = driverController.getX(kRight);

			// forwardPower = forwardLimiter.calculate(forwardPower);

			dt.drive(strafePower, forwardPower, turnPower);
			//#endregion
		}

		if (driverController.getAButton())
			loader.load();
		else if (driverController.getBButton())
			loader.reverse();
		else
			loader.stop();
		//#endregion

		
	}

	@Override
	public void testInit()
	{
        dt.setMaxOutput(Constants.Drivetrain.autoMaxOutput);
		skills.init(skills::selectSlalomPath);
	}

	@Override
	public void testPeriodic()
	{
		skills.periodic();
	}
}
