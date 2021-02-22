package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.subsystems.*;

public class Skillz
{
    Mecanum dt;
    
    @FunctionalInterface
    interface Action
    {
        boolean execute(double param);
    }
    
	Action drive;
    Action turn;
    
    int step = 0;

    Object[][] auton;
    
    Field2d field = new Field2d();

    public Skillz(Mecanum dt)
    {
        this.dt = dt;

        drive = dt::driveToDistance;
        turn = dt::turnToAngle;

        SmartDashboard.putData(field);
    }

	public void init(Runnable pathSelection)
	{
        pathSelection.run();

        dt.resetSensors();
        
        step = 0;
    }
    
	public void periodic()
	{
        Action currentAction = (Action) auton[step][0];
        double param = (double) auton[step][1];
        
        boolean currentActionCompleted = currentAction.execute(param);
        
        if (currentActionCompleted && step < auton.length-1)
        {
            step++;
            dt.resetSensors();
        }
        
        String nameCurAction = (currentAction == drive) ? "drive" : "turn";
        SmartDashboard.putString("Current Action", nameCurAction);
        SmartDashboard.putBoolean("Complete?", currentActionCompleted);
    }
    
    public void selectSimplePath()
    {
        auton = new Object[][]
        {
            {drive, 1.0},
            {turn, 90.0},
            {drive, 1.0},
            {turn, 90.0},
            {drive, 1.0},
            {turn, 90.0},
            {drive, 1.0},
            {turn, 90.0},

            
            {drive, 1.0},
            {turn, 90.0},
            {drive, 1.0},
            {turn, 90.0},
            {drive, 1.0},
            {turn, 90.0},
            {drive, 1.0},
            {turn, 90.0},

            
            {drive, 1.0},
            {turn, 90.0},
            {drive, 1.0},
            {turn, 90.0},
            {drive, 1.0},
            {turn, 90.0},
            {drive, 1.0},
            {turn, 90.0},

            
            {drive, 1.0},
            {turn, 90.0},
            {drive, 1.0},
            {turn, 90.0},
            {drive, 1.0},
            {turn, 90.0},
            {drive, 1.0},
            {turn, 90.0}
        };
    }

	public void selectGalacticPathARed()
	{
        auton = new Object[][]
        {
            {drive, 1.524},
            {turn, 26.565},
            {drive, 1.704},
            {turn, -98.13},
            {drive, 2.410},
            {turn, 71.65},
            {drive, 3.81}
        };
    }
	
	public void selectGalacticPathABlue()
	{
        auton = new Object[][]
        {
            {drive, 4.72},
            {turn, -72.565},
            {drive, 2.41},
            {turn, 81.87},
            {drive, 1.7},
            {turn, -9.31},
            {drive, 1.524}
        };
    }
	
	public void selectGalacticPathBRed()
	{
        auton = new Object[][]
        {
            {drive, 1.524},
            {turn, 45.0},
            {drive, 2.155},
            {turn, -90.0},
            {drive, 2.155},
            {turn, 45.0},
            {drive, 2.268}
        };
    }
    
	public void selectGalacticPathBBlue()
	{
        auton = new Object[][]
        {
            {drive, 3.381},
            {turn, -45.0},
            {drive, 2.155},
            {turn, 90.0},
            {drive, 2.155},
            {turn, -45.0},
            {drive, 0.762}
        };
    }
    
    public void selectSquare()
	{
        auton = new Object[][]
        {
            //SQUARE
            {drive, 1.0},
            {turn, 90.0},
            {drive, 1.0},
            {turn, 90.0}, 
            {drive, 1.0},
            {turn, 90.0},
            {drive, 1.0},
            {turn, 90.0}
        };
    }
    
	public void selectMatthewPath()
	{
        auton = new Object[][]
        {
            {drive, 1.704},
            {turn, -63.0},
            {drive, 2.41},
            {turn, 136.0},
            {drive, 1.078},
            {turn, -32.0},
            {drive, 2.41},
            {turn, -117.0},
            {drive, 2.41},
            {turn, 152.0},
            {drive, 0.762},
            {turn, -76.0},
            {drive, 2.41},
            {turn, -76.0},
            {drive, 1.704},
            {turn, 136.0}
        };
	}
	
	public void selectSlalomPath()
	{
        auton = new Object[][]
        {
            {drive, 0.9},
            {turn, -45.0},
            {drive, 2.155},
            {turn, 45.0},
            {drive, 3.302},
            {turn, 45.0},
            {drive, 2.067},
            {turn, -95.0},
            {drive, 1.27},
            {turn, -80.0},
            {drive, 1.27},
            {turn, -100.0},
            {drive, 2.342},
            {turn, 50.0},
            {drive, 3.048},
            {turn, 42.0},
            {drive, 2.155}
        };
	}
}
