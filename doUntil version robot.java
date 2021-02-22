/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.subsystems.*;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static edu.wpi.first.wpilibj.GenericHID.Hand.*;

public class Robot extends TimedRobot
{
	Mecanum dt = new Mecanum();
	XboxController driverController = new XboxController(0);
	Timer timer = new Timer();

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
	}

	@Override
	public void autonomousInit()
	{
		dt.resetSensors();
		timer.reset();
		timer.start();
	}

	@Override
	public void autonomousPeriodic()
	{
	}

	@Override
	public void teleopInit()
	{
		dt.resetSensors();
	}

	@Override
	public void teleopPeriodic()
	{
		dt.drive(-driverController.getX(kLeft), -driverController.getY(kLeft),
				driverController.getX(kRight));
	}

	@Override
	public void testInit()
	{
		dt.resetSensors();
		timer.reset();
		timer.start();
	}

	



	Action drive = dt::driveToDistance;
	Action turn = dt::turnToAngle;

	@Override
	public void testPeriodic()
	{
		// double time = timer.get();


		// doUntil(drive, -90, time, 0, 5);
		// doUntil(drive, 1, time, 0, 5);

		// square(time);
		// galacticPathARed(time);
		// galacticPathABlue(time);

		// slalomPath(time);

		
		double angle = SmartDashboard.getNumber("Turn To?", 0);
		SmartDashboard.putNumber("Turn To?", angle);
		
		if (driverController.getAButton())
			dt.turnToAngle(angle);
		else
			dt.drive(0, 0, 0);
	}

	@FunctionalInterface
	interface Action { void execute(double param); }

	private void doUntil(Action action, double param, double curTime, double fromTime, double untilTime)
	{
		if (fromTime < curTime && curTime < untilTime - 0.05)
			action.execute(param);
		else if (fromTime < curTime && curTime < untilTime)
			dt.resetSensors();
	}

	@SuppressWarnings("unused")
	private void galacticPathARed(double time)
	{
		doUntil(drive, 1.524, time, 0, 2.2);
		doUntil(turn, 26.565, time, 2.2, 3);
		doUntil(drive, 1.704, time, 3, 4.3);
		doUntil(turn, -98.13, time, 4.3, 5.5);
		doUntil(drive, 2.410, time, 5.5, 9);
		doUntil(turn, 71.65, time, 9, 11);
		doUntil(drive, 3.81, time, 11, 14.5);
	}

		/// Backup; this works pretty well
		// doUntil(drive, 1.524, time, 0, 2.5);
		// doUntil(turn, 26.565, time, 2.5, 3);
		// doUntil(drive, 1.704, time, 3, 4.3);
		// doUntil(turn, -98.13, time, 4.3, 5.5);
		// doUntil(drive, 2.410, time, 5.5, 9);
		// doUntil(turn, 71.65, time, 9, 11);
		// doUntil(drive, 3.81, time, 11, 14.5);
	

	@SuppressWarnings("unused")
	private void galacticPathABlue(double time)
	{
		doUntil(drive, 4.72, time, 0, 4);
		doUntil(turn, -72.565, time, 4, 8);
		doUntil(drive, 2.41, time, 8, 12);
		doUntil(turn, 81.87, time, 12, 16);
		doUntil(drive, 1.7, time, 16, 20);
		doUntil(turn, -9.31, time, 20, 24);
		doUntil(drive, 1.524, time, 24, 28);
	}

	@SuppressWarnings("unused")
	private void galacticPathBRed(double time)
	{
		doUntil(drive, 1.524, time, 0, 4);
		doUntil(turn, 45, time, 4, 6);
		doUntil(drive, 2.155, time, 6, 10);
		doUntil(turn, -90, time, 10, 12);
		doUntil(drive, 2.155, time, 12, 16);
		doUntil(turn, 45, time, 16, 18);
		doUntil(drive, 2.268, time, 18, 22);
	}

	@SuppressWarnings("unused")
	private void galacticPathBBlue(double time)
	{
		doUntil(drive, 3.381, time, 0, 4);
		doUntil(turn, -45, time, 4, 6);
		doUntil(drive, 2.155, time, 6, 10);
		doUntil(turn, 90, time, 10, 12);
		doUntil(drive, 2.155, time, 12, 16);
		doUntil(turn, -45, time, 16, 18);
		doUntil(drive, 0.762, time, 18, 22);
	}

	@SuppressWarnings("unused")
	private void square(double time)
	{
		//SQUARE
		doUntil(drive, 1, time, 0, 2);
		doUntil(turn, 90, time, 2, 4);
		doUntil(drive, -1, time, 4, 6);
		doUntil(turn, 90, time, 6, 8);
		doUntil(drive, 1, time, 8, 10);
		doUntil(turn, -90, time, 10, 12);
		doUntil(drive, 1, time, 12, 14);
		doUntil(turn, -90, time, 14, 16);
	}

	@SuppressWarnings("unused")
	private void MatthewPath(double time)  //all degrees are relative to the field. Needs to be managed
	{
		doUntil(drive, 1.704, time, 0, 4);
		doUntil(turn, -63, time, 4, 8);
		doUntil(drive, 2.41, time, 8, 12);
		doUntil(turn, 136, time, 12, 16);
		doUntil(drive, 1.078, time, 16, 20);
		doUntil(turn, -32, time, 20, 24);
		doUntil(drive, 2.41, time, 24, 28);
		doUntil(turn, -117, time, 28, 32);
		doUntil(drive, 2.41, time, 32, 36);
		doUntil(turn, 152, time, 36, 40);
		doUntil(drive, 0.762, time, 40, 44);
	//	doUntil(drive, 2.41, time, 44, 48);
		doUntil(turn, -76, time, 44, 48);
		doUntil(drive, 2.41, time, 48, 52);
		doUntil(turn, -76, time, 52, 56);
		doUntil(drive, 1.704, time, 56, 60);
		doUntil(turn, 136, time, 60, 64);
	}
	
	@SuppressWarnings("unused")
	private void slalomPath(double time)
	{
		doUntil(drive, 0.9, time, 0, 5);
		doUntil(turn, -45, time, 5, 10);
		doUntil(drive, 2.155, time, 10,15);
		doUntil(turn, 45, time, 15, 20);
		doUntil(drive, 3.302, time, 20,25);
		doUntil(turn, 45, time, 25,30);
		doUntil(drive, 2.067, time, 30,35);
		doUntil(turn, -95, time, 35,40);
		doUntil(drive, 1.27, time, 40,45);
		doUntil(turn, -80, time, 45, 50);
		doUntil(drive, 1.27, time, 50, 55);
		doUntil(turn, -100, time, 55, 60);
		doUntil(drive, 2.342, time, 60, 65);
		doUntil(turn, 50, time, 65, 70);
		doUntil(drive, 3.048, time, 70, 75);
		doUntil(turn, 42, time, 75, 80);
		doUntil(drive, 2.155, time, 80, 85);
	}
}
