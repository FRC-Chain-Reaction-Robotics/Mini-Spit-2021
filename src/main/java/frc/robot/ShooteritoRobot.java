package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.subsystems.Shooterito;

public class ShooteritoRobot extends Robot
{
    Shooterito shooter = new Shooterito(loader);
    
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
            shooter.shoot();
            dt.aim();
        }
        else if (driverController.getBButton())
			loader.reverse();
		else if (driverController.getAButton())
			loader.load();	//	just in case, to spit out balls that are jammed
        else
            shooter.stop();
		//#endregion

		//#region driving
		double triggerValue = driverController.getTriggerAxis(Hand.kRight);
		
		final double minOutput = 0.2;
		double output = (1 + minOutput) - triggerValue;	//	the more you press it, the lower the output is

		dt.setMaxOutput(output);
		


		double forwardPower = -driverController.getY(Hand.kLeft);
		double strafePower = driverController.getX(Hand.kLeft);
		double turnPower = driverController.getX(Hand.kRight);

		// forwardPower = forwardLimiter.calculate(forwardPower);

		dt.drive(strafePower, forwardPower, turnPower);
		//#endregion
	}
}
