package frc.robot;

import static com.revrobotics.CANSparkMaxLowLevel.MotorType.*;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import static com.revrobotics.CANSparkMax.IdleMode.*;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Sendable;

import static edu.wpi.first.wpilibj.SPI.Port.*;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Mecanum
{
    /// Spitfire BIG
    CANSparkMax lf = new CANSparkMax(1, kBrushless);
    CANSparkMax lb = new CANSparkMax(6, kBrushless);
    CANSparkMax rf = new CANSparkMax(2, kBrushless);
    CANSparkMax rb = new CANSparkMax(5, kBrushless);

    /// Minispit
    // CANSparkMax lf = new CANSparkMax(2, kBrushless);
    // CANSparkMax lb = new CANSparkMax(1, kBrushless);
    // CANSparkMax rf = new CANSparkMax(3, kBrushless);
    // CANSparkMax rb = new CANSparkMax(4, kBrushless);

    MecanumDrive md = new MecanumDrive(lf, lb, rf, rb);

    CANEncoder m_leftFrontEncoder = lf.getEncoder();
    CANEncoder m_leftBackEncoder = lb.getEncoder();
    CANEncoder m_rightFrontEncoder = rf.getEncoder();
    CANEncoder m_rightBackEncoder = rb.getEncoder();

    Gyro gyro = new ADXRS450_Gyro(kOnboardCS0);

    PIDController aimPID = new PIDController(0.01, 0, 0);

    
    public Mecanum() 
    {
        md.setMaxOutput(.44); // spitfire

        // md.setMaxOutput(.35); minispit

        gyro.reset();
        gyro.calibrate();
        
        m_rightFrontEncoder.setPositionConversionFactor(0.0454);
        m_rightBackEncoder.setPositionConversionFactor(0.0454);
        m_leftFrontEncoder.setPositionConversionFactor(0.0454);
        m_leftBackEncoder.setPositionConversionFactor(0.0454);

        /* For spitfire, not for new robot
        lf.setInverted(true);
		lb.setInverted(true);
		rb.setInverted(true);
        */

        // Minispit
        rf.setInverted(true);

		lb.setIdleMode(kCoast);
		rf.setIdleMode(kCoast);
		rb.setIdleMode(kCoast);
		lf.setIdleMode(kCoast);

		lf.burnFlash();
		lb.burnFlash();
		rf.burnFlash();
        rb.burnFlash();
        
        Shuffleboard.getTab("Velocities").addNumber("elbeee Velocity", m_leftBackEncoder::getVelocity);
        Shuffleboard.getTab("Velocities").addNumber("lf Velocity", m_leftFrontEncoder::getVelocity);
        Shuffleboard.getTab("Velocities").addNumber("rb Velocity", m_rightBackEncoder::getVelocity);
        Shuffleboard.getTab("Velocities").addNumber("rf Velocity", m_rightFrontEncoder::getVelocity);

        
        // turnPID.setIntegratorRange(0, 15);
    }
    
    
    // public void drive(double xSpeed, double ySpeed, double zRotation)
    // {
    //     this.drive(xSpeed, ySpeed, zRotation, false, false);
    // }

    // public void drive(double xSpeed, double ySpeed, double zRotation, boolean slowMode, boolean fieldOriented)
    // {
    //     double multiplier = slowMode ? 0.5 : 1;
        
    //     if (fieldOriented)
    //         md.driveCartesian(multiplier*xSpeed, multiplier*ySpeed, multiplier*zRotation, gyro.getAngle());
    //     else
    //         md.driveCartesian(multiplier*xSpeed, multiplier*ySpeed, multiplier*zRotation);
    // }
    
    public void drive(double xSpeed, double ySpeed, double zRotation)
    {
        md.driveCartesian(xSpeed, ySpeed, zRotation);
    }

    // displays the heading data in the gyro, form of a compass
	public void displayGyro()
	{
        // SmartDashboard.putNumber("gyro", gyro.getAngle());
        SmartDashboard.putData((Sendable) gyro);
    }

    public void displayEncoders()
    {
		SmartDashboard.putNumber("lf position", m_leftFrontEncoder.getPosition());
		SmartDashboard.putNumber("rf position", m_rightFrontEncoder.getPosition());
		SmartDashboard.putNumber("lb position", m_leftBackEncoder.getPosition());
		SmartDashboard.putNumber("rb position", m_rightBackEncoder.getPosition());
    }

    public void resetSensors()
    {
        gyro.reset();
        m_leftBackEncoder.setPosition(0);
        m_rightBackEncoder.setPosition(0);
        m_leftFrontEncoder.setPosition(0);
        m_rightFrontEncoder.setPosition(0);
    }
    
    
    // 1.0/15 is for carpet
    // 1.0/20 is for sully room (for now) HMMM
    /// update: never drive in sully's room, it's too slippery

    PIDController turnPID = new PIDController(1.0/15, 0, 0);

    public void turnToAngle(double angle)
    {
        md.driveCartesian(0, 0, turnPID.calculate(gyro.getAngle(), angle));
    }

    PIDController distPID = new PIDController(2.5, 0, 0);

    public void driveToDistance(double distance)
    {
        // double error = m_leftFrontEncoder.getPosition() - m_rightFrontEncoder.getPosition() / 2;
        double error = m_leftFrontEncoder.getPosition();
        md.driveCartesian(0, distPID.calculate(error, distance), turnPID.calculate(gyro.getAngle(), 0));
    }
}