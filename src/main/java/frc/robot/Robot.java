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
	DistanceSensor distanceSensor = new DistanceSensor();
	Mecanum dt = new Mecanum(ll);
	Shooter shooter = new Shooter();
	BallMgmt loader = new BallMgmt();
	Intake intake = new Intake();
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

		SmartDashboard.putNumber("ultrasonic", distanceSensor.getDistance());
	}

	@Override
	public void teleopInit()
	{
		dt.resetSensors();
		dt.setMaxOutput(1);
	}

	SlewRateLimiter forwardLimiter = new SlewRateLimiter(1/(2^16));	//	2^16 = 65536

	@Override
	public void teleopPeriodic()
	{
		//#region intake
		if (driverController.getPOV(0) == 0)
			intake.up();
		else if (driverController.getPOV(0) == 180)
			intake.down();
		else
			intake.freezePosition();


		if (driverController.getBumper(Hand.kRight))
			intake.on();
		else if (driverController.getBumper(Hand.kLeft))
			intake.reverse();
		else
			intake.off();
		//#endregion

		//#region shooting
		if (driverController.getXButton())
		{
            dt.aim();
			shooter.shoot();
		}
		else
			shooter.stop();

		if (driverController.getAButton())
			loader.load();
		else if (driverController.getBButton())
			loader.reverse();
		else
			loader.stop();
		//#endregion

		//#region driving
		double triggerValue = driverController.getTriggerAxis(Hand.kRight);
		
		final double minOutput = 0.2;
		double output = (1 + minOutput) - triggerValue;	//	the more you press it, the lower the output is

		dt.setMaxOutput(output);
		


		double forwardPower = -driverController.getY(kLeft);
		double strafePower = driverController.getX(kLeft);
		double turnPower = driverController.getX(kRight);

		// forwardPower = forwardLimiter.calculate(forwardPower);

		dt.drive(strafePower, forwardPower, turnPower);
		//#endregion
	}

	@Override
	public void testInit()
	{
        dt.setMaxOutput(Constants.Drivetrain.autoMaxOutput);
		skills.init(skills::selectSquare);
	}

	@Override
	public void testPeriodic()
	{
		skills.periodic();
	}
}
