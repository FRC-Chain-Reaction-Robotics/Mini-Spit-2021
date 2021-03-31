package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;

public class Intake
{
    //#region intake
    TalonSRX intakeMotor = new TalonSRX(Constants.INTAKE_Motor_ID);

    public Intake()
    {
        intakeMotor.setInverted(true);
        intakeLifter.setIdleMode(IdleMode.kBrake);
    }

    public void on()
    {
        intakeMotor.set(ControlMode.PercentOutput, 1);
    }

    public void off()
    {
        intakeMotor.set(ControlMode.PercentOutput, 0);
    }
    

    public void reverse()
    {
        intakeMotor.set(ControlMode.PercentOutput, -1);
    }
    //#endregion
    
    //#region intake lifter
    CANSparkMax intakeLifter = new CANSparkMax(Constants.INTAKE_LIFTER_ID, MotorType.kBrushless);

    public void up()
    {
        intakeLifter.set(1);
    }

    public void down()
    {
        intakeLifter.set(-1);
    }

    public void freezePosition()
    {
        intakeLifter.set(0);
    }
    //#endregion
}