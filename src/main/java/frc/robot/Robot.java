/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.SlewRateLimiter;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static edu.wpi.first.wpilibj.GenericHID.Hand.*;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

import frc.robot.subsystems.*;

public class Robot extends TimedRobot
{
	Mecanum dt = new Mecanum();
	Shooter shooter = new Shooter();
	XboxController driverController = new XboxController(0);
	Skillz skills = new Skillz(dt);

	// for pid tune testing
	double angle;

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
		
		angle = SmartDashboard.getNumber("Turn To?", 0);
		SmartDashboard.putNumber("Turn To?", angle);
	}

	@Override
	public void teleopInit()
	{
		dt.resetSensors();
	}



	// This will limit the rate of change of the joystick inputs, meaning it'll accelerate less quickly.
	// This may help with brownout issues, imprecise PID movements, etc.
	double accelLimit = 1024;	//	Units per second
	SlewRateLimiter accelLimiter = new SlewRateLimiter(accelLimit);

	@Override
	public void teleopPeriodic()
	{
		
		if (driverController.getBButton())
		{
			shooter.shootMotorLeft.set(TalonFXControlMode.PercentOutput, driverController.getTriggerAxis(Hand.kRight));
			shooter.shootMotorRight.set(TalonFXControlMode.PercentOutput, driverController.getTriggerAxis(Hand.kRight));
		} else
		{
			shooter.shootMotorLeft.set(TalonFXControlMode.PercentOutput, 0);
			shooter.shootMotorRight.set(TalonFXControlMode.PercentOutput, 0);
		}

		if (driverController.getAButton()) shooter.shoot();
		else shooter.stop();


		double forwardPower = driverController.getY(kLeft);
		double strafePower = driverController.getX(kLeft);
		double turnPower = driverController.getX(kRight);


		// Recalculates such that the rate of change is limited to accelLimit
		forwardPower = accelLimiter.calculate(forwardPower);
		// If you want to limit strafing and turning as well, you will need separate SlewRateLimiter objects.


		dt.drive(strafePower, forwardPower, turnPower);
		
		// pid tune testing code
		// if (driverController.getAButton())
		// 	dt.turnToAngle(angle);
		// else
		// 	dt.drive(0, 0, 0);
	}

	@Override
	public void testInit()
	{
		skills.init(skills::selectSlalomPath);
	}

	@Override
	public void testPeriodic()
	{
		skills.periodic();
	}
}
