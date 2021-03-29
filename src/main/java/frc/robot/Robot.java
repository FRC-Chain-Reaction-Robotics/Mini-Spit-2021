/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static edu.wpi.first.wpilibj.GenericHID.Hand.*;

import frc.robot.subsystems.*;

public class Robot extends TimedRobot
{
	Limelight ll = new Limelight();
	DistanceSensor distanceSensor = new DistanceSensor();
	Mecanum dt = new Mecanum(ll);
	Shooter shooter = new Shooter();
	Intake intake = new Intake();
	XboxController driverController = new XboxController(0);
	Skillz skills = new Skillz(dt);

	@Override
	public void robotInit()
	{
		dt.resetSensors();
		intake.resetEncoder();
	}

	@Override
	public void robotPeriodic()
	{
		dt.displayEncoders();
		dt.displayGyro();

		intake.printPosition();
		SmartDashboard.putNumber("ultrasonic", distanceSensor.getDistance());
	}

	@Override
	public void teleopInit()
	{
		dt.resetSensors();
		intake.resetEncoder();
	}

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


		if (driverController.getYButton())
			intake.on();
		else
			intake.off();
		//#endregion

		//#region shooting
		if (driverController.getXButton())
			shooter.shoot();
		else
			shooter.stopShooting();

		if (driverController.getAButton())
			shooter.shoot();
		else if (driverController.getBButton())
			shooter.reverseLoader();
		else
			shooter.stopLoading();
		//#endregion

		//#region driving
		double forwardPower = driverController.getY(kLeft);
		double strafePower = driverController.getX(kLeft);
		double turnPower = driverController.getX(kRight);

		dt.drive(strafePower, forwardPower, turnPower);
		//#endregion
	}

	// @Override
	// public void testInit()
	// {
	// 	skills.init(skills::selectSimplePath);
	// }

	// @Override
	// public void testPeriodic()
	// {
	// 	skills.periodic();
	// }
}
