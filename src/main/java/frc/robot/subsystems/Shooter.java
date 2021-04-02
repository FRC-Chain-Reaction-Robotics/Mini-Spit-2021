package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;

import frc.robot.Constants;

public class Shooter
{        
    public WPI_TalonFX shootMotorLeft =  new WPI_TalonFX(Constants.SHOOTER_MOTOR_LEFT_ID);
    public WPI_TalonFX shootMotorRight = new WPI_TalonFX(Constants.SHOOTER_MOTOR_RIGHT_ID);

    final double SHOOTER_POWER = 1;
    
    public Shooter()
    {
        shootMotorRight.setInverted(false);
        shootMotorLeft.setInverted(true);
        
        shootMotorLeft.setNeutralMode(NeutralMode.Coast);
        shootMotorRight.setNeutralMode(NeutralMode.Coast);
    }

    public void shoot()
    {
        shootMotorLeft.set(TalonFXControlMode.PercentOutput, SHOOTER_POWER);
        shootMotorRight.set(TalonFXControlMode.PercentOutput, SHOOTER_POWER);
    }

    public void stop()
    {
        shootMotorLeft.set(TalonFXControlMode.PercentOutput, 0);
        shootMotorRight.set(TalonFXControlMode.PercentOutput, 0);
    }


}