package frc.robot;

import edu.wpi.first.wpilibj.controller.PIDController;

public class Constants
{
    public static final int LOAD_MOTOR_ID = 3;

    public static final int SHOOTER_MOTOR_LEFT_ID = 0;
    public static final int SHOOTER_MOTOR_RIGHT_ID = 1;
    
    public static final int INTAKE_Motor_ID = 6;
    public static final int INTAKE_LIFTER_ID = 5;

    public static final int DISTANCE_SENSOR_ANALOG_IN_PORT = 3;


    public static final class Drivetrain
    {
        //  drive motor IDs
        public static final int LF = 7;
        public static final int LB = 4;
        public static final int RF = 2;
        public static final int RB = 8;

        public static final double autoMaxOutput = 0.5; // for autonomous modes/skills challenges
		public static final double teleopMaxOutput = 0.5;

        //  PID
        public static final PIDController turnPID = new PIDController(0.02, 0, 0);
        public static final double turnPIDTolerance = 0.2;
        
        public static final PIDController distPID = new PIDController(2.5, 0, 0);
		public static final double distPIDTolerance = 0.005;
    }
}