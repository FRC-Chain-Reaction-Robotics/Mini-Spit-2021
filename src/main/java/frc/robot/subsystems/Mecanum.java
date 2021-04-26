package frc.robot.subsystems;

import static com.revrobotics.CANSparkMaxLowLevel.MotorType.*;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMax;

import static com.revrobotics.CANSparkMax.IdleMode.*;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.Timer;

import static edu.wpi.first.wpilibj.SPI.Port.*;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.sensors.*;
import frc.robot.Constants;

public class Mecanum
{
    CANSparkMax lf = new CANSparkMax(Constants.Drivetrain.LF, kBrushless);
    CANSparkMax lb = new CANSparkMax(Constants.Drivetrain.LB, kBrushless);
    CANSparkMax rf = new CANSparkMax(Constants.Drivetrain.RF, kBrushless);
    CANSparkMax rb = new CANSparkMax(Constants.Drivetrain.RB, kBrushless);

    MecanumDrive md = new MecanumDrive(lf, lb, rf, rb);

    CANEncoder m_leftFrontEncoder = lf.getEncoder();
    CANEncoder m_leftBackEncoder = lb.getEncoder();
    CANEncoder m_rightFrontEncoder = rf.getEncoder();
    CANEncoder m_rightBackEncoder = rb.getEncoder();

	CANPIDController lf_PID = lf.getPIDController();
    CANPIDController lb_PID = lb.getPIDController();
    CANPIDController rf_PID = rf.getPIDController();
	CANPIDController rb_PID = rb.getPIDController();
    
    Gyro gyro = new ADXRS450_Gyro(kOnboardCS0);


    Limelight ll;

    public Mecanum(Limelight ll) 
    {
        this.ll = ll;
        // md.setMaxOutput(0.44); // spitfire
        

        // md.setMaxOutput(0.5); // minispit


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
        rb.setInverted(true);

		lb.setIdleMode(kCoast);
		rf.setIdleMode(kCoast);
		rb.setIdleMode(kCoast);
        lf.setIdleMode(kCoast);
        
        // md.setMaxOutput(0.44);
        // md.setMaxOutput(1);

        lf.setSmartCurrentLimit(40);
        lb.setSmartCurrentLimit(40);
        rb.setSmartCurrentLimit(40);
        rf.setSmartCurrentLimit(40);

        CANSparkMax.enableExternalUSBControl(false);

		lf.burnFlash();
		lb.burnFlash();
		rf.burnFlash();
        rb.burnFlash();
        
        distPID.setTolerance(Constants.Drivetrain.distPIDTolerance);
        turnPID.setTolerance(Constants.Drivetrain.turnPIDTolerance);
        
        // turnPID.setIntegratorRange(0, 15);

    }    
    /**
   * Drive method for Mecanum platform.
   *
   * <p>Angles are measured clockwise from the positive X axis. The robot's speed is independent
   * from its angle or rotation rate.
   *
   * @param ySpeed The robot's speed along the Y axis [-1.0..1.0]. Right is positive.
   * @param xSpeed The robot's speed along the X axis [-1.0..1.0]. Forward is positive.
   * @param zRotation The robot's rotation rate around the Z axis [-1.0..1.0]. Clockwise is
   *     positive.
   */
    public void drive(double ySpeed, double xSpeed, double zRotation)
    {
        md.driveCartesian(ySpeed, xSpeed, zRotation);
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
        
        SmartDashboard.putNumber("rb Velocity", m_rightBackEncoder.getVelocity());
        SmartDashboard.putNumber("elbeee Velocity", m_leftBackEncoder.getVelocity());
        SmartDashboard.putNumber("lf Velocity", m_leftFrontEncoder.getVelocity());
        SmartDashboard.putNumber("rf Velocity", m_rightFrontEncoder.getVelocity());
    }

    public void resetSensors()
    {
        gyro.reset();
        m_leftBackEncoder.setPosition(0);
        m_rightBackEncoder.setPosition(0);
        m_leftFrontEncoder.setPosition(0);
        m_rightFrontEncoder.setPosition(0);
    }
    
    //#region actions
    PIDController turnPID = Constants.Drivetrain.turnPID;

    public boolean turnToAngle(double angle)
    {
        md.driveCartesian(0, 0, turnPID.calculate(gyro.getAngle(), angle));

        return turnPID.atSetpoint();
    }

    PIDController distPID = Constants.Drivetrain.distPID;

    public boolean driveToDistance(double distance)
    {
        double distanceOutput = distPID.calculate(m_leftFrontEncoder.getPosition(), distance);
        double turnOutput = turnPID.calculate(gyro.getAngle(), 0);
        // double turnOutput = 0;
        
        md.driveCartesian(0, distanceOutput, turnOutput);

        return distPID.atSetpoint();
    }

    
    PIDController aimPID = Constants.Drivetrain.aimPID;

    public boolean aim()
    {
        md.driveCartesian(0, 0, aimPID.calculate(-ll.getTx(), 0));

        return turnPID.atSetpoint();
    }


    Timer waitTimer = new Timer();
    
    public boolean startWaiting()   //  this is sad. command based plsz
    {
        waitTimer.reset();
        waitTimer.start();
        return true;
    }
    /**
     * returns true when wait is over
     */
    public boolean wait(double time)
    {
        double distanceOutput = distPID.calculate(m_leftFrontEncoder.getPosition(), 0);

        md.driveCartesian(0, distanceOutput, 0);
        return waitTimer.get() <= time;
    }
    //#endregion

    public void setMaxOutput(double maxOutput)
    {
        md.setMaxOutput(maxOutput);
    }
}