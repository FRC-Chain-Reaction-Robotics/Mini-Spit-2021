package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Constants;

public class BallMgmt
{
    TalonSRX loadMotor = new TalonSRX(Constants.LOAD_MOTOR_ID);

    public BallMgmt()
    {
        loadMotor.setInverted(true);
    }

    public void load()
    {
        loadMotor.set(ControlMode.PercentOutput, 1);
    }

    public void reverse()
    {
        loadMotor.set(ControlMode.PercentOutput, -1);
    }

    public void stop()
    {
        loadMotor.set(ControlMode.PercentOutput, 0);
    }
}
