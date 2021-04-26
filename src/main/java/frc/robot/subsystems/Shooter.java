package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    public void shoot(double power)
    {
        // 0.3 bottom green zone, back wheel aligned with 5ft mark
        // 1 high port yellow zone
        // 0.89 botom port on 15ft mark (blue zone)
        // 1 bottom port last zone

        shootMotorLeft.set(TalonFXControlMode.PercentOutput, power);
        shootMotorRight.set(TalonFXControlMode.PercentOutput, power);

        SmartDashboard.putNumber("shooter power", power);
    }

    public void stop()
    {
        shootMotorLeft.set(TalonFXControlMode.PercentOutput, 0);
        shootMotorRight.set(TalonFXControlMode.PercentOutput, 0);
    }

    public void greenShot()
    {
        shoot(0.3);
    }

    
    public void yellowShot()
    {
        shoot(1);
    }

    
    public void blueShot()
    {
        shoot(0.89);
    }

    
    public void redShot()
    {
        shoot(1);
    }
}