package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.Constants;

public class Shooter
{        
    TalonFX shootMotorLeft =  new TalonFX(Constants.SHOOTER_MOTOR_LEFT_ID);
    TalonFX shootMotorRight = new TalonFX(Constants.SHOOTER_MOTOR_RIGHT_ID);

    TalonSRX loadMotor = new TalonSRX(Constants.LOAD_MOTOR_ID);
    
    public Shooter()
    {
        loadMotor.setInverted(true);

        shootMotorRight.follow(shootMotorLeft);
        shootMotorRight.setInverted(true);

        
        shootMotorLeft.config_kP(0, 0.01 * 0.25);   //  use 0.25 on talons
    }

    public void shoot()
    {
        shootMotorLeft.set(TalonFXControlMode.Velocity, 1000);  //  incorrect units. do we need a wrapper class??
        
        if (shootMotorLeft.getClosedLoopError(0) < 50) 
            loadMotor.set(ControlMode.PercentOutput, 1);
    }

    public void stop()
    {
        shootMotorLeft.set(TalonFXControlMode.Velocity, 0);
        loadMotor.set(ControlMode.PercentOutput, 0);
    }
}