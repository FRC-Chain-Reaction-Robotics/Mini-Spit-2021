package frc.robot.subsystems;

import javax.lang.model.util.ElementScanner6;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.controller.PIDController;
import frc.Constants;

public class Shooter
{        
    TalonFX shootMotorLeft =  new TalonFX(Constants.SHOOTER_MOTOR_LEFT_ID);
    TalonFX shootMotorRight = new TalonFX(Constants.SHOOTER_MOTOR_RIGHT_ID);

    TalonSRX loadMotor = new TalonSRX(Constants.LOAD_MOTOR_ID);
    
    public Shooter()
    {
        shootMotorRight.setInverted(true);
    }

    public void shoot()
    {
       shootMotorLeft.config_kP(, )
        loadMotor.set(ControlMode.PercentOutput, power)

        shootMotorLeft
      
      
        //shooterPID.set

    }
    
    public void blabla(double velocity)
    {

    }
}