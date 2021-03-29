package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Constants;

public class Shooter
{        
    public TalonFX shootMotorLeft =  new TalonFX(Constants.SHOOTER_MOTOR_LEFT_ID);
    public TalonFX shootMotorRight = new TalonFX(Constants.SHOOTER_MOTOR_RIGHT_ID);

    TalonSRX loadMotor = new TalonSRX(Constants.LOAD_MOTOR_ID);

    final double SHOOTER_POWER = 0.5;
    
    public Shooter()
    {
        loadMotor.setInverted(true);

        shootMotorRight.follow(shootMotorLeft);
        shootMotorRight.setInverted(false);
        shootMotorLeft.setInverted(false);

        
        // shootMotorLeft.config_kP(0, 0.01 * 0.25);   //  use 0.25 on talons
    }

    public void shoot()
    {
        // shootMotorLeft.set(TalonFXControlMode.Velocity, 1000);  //  incorrect units. do we need a wrapper class??
        
        // if (shootMotorLeft.getClosedLoopError(0) < 50)
            loadMotor.set(ControlMode.PercentOutput, 1);

        shootMotorLeft.set(TalonFXControlMode.PercentOutput, SHOOTER_POWER);
    }

    public void stopShooting()
    {
        shootMotorLeft.set(TalonFXControlMode.PercentOutput, 0);
    }


    public void load()
    {
        loadMotor.set(ControlMode.PercentOutput, 1);
    }

    public void reverseLoader()
    {
        loadMotor.set(ControlMode.PercentOutput, -1);
    }

    public void stopLoading()
    {
        loadMotor.set(ControlMode.PercentOutput, 0);
    }
}