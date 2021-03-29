package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

public class Intake
{
    TalonSRX intakeMotor = new TalonSRX(Constants.INTAKE_Motor_ID);
    CANSparkMax intakeLifter = new CANSparkMax(Constants.INTAKE_LIFTER_ID, MotorType.kBrushless);

    public Intake()
    {
        intakeMotor.setInverted(true);
        intakeLifter.setIdleMode(IdleMode.kBrake);
        resetEncoder();
    }

    public void on()
    {
        intakeMotor.set(ControlMode.PercentOutput, 1);
    }

    public void off()
    {
        intakeMotor.set(ControlMode.PercentOutput, 0);
    }

    public void up()
    {
        intakeLifter.set(1);
    }

    public void down()
    {
        intakeLifter.set(1);
    }

    public void freezePosition()
    {
        intakeLifter.set(0);
    }


    public void printPosition()
    {
        SmartDashboard.putNumber("intake lifter position", intakeLifter.getEncoder().getPosition());
    }

    public void downToPosition()
    {
        final int position = 0;   //  TODO: measure encoder position when down
        intakeLifter.getPIDController().setReference(position, ControlType.kPosition);
    }

    public void resetEncoder()
    {
        intakeLifter.getEncoder().setPosition(0);
    }
}