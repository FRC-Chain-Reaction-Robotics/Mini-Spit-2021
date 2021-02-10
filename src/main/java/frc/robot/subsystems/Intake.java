package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.Constants;

public class Intake
{
    TalonSRX load = new TalonSRX(Constants.INTAKE_Motor_ID);

    public void in(boolean trigger)
    {
        if (trigger)
            load.set(ControlMode.PercentOutput, 1);
        else
            load.set(ControlMode.PercentOutput, 0);
    }
}