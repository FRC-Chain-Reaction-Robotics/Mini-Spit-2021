package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import frc.robot.sensors.Limelight;

/**
 * warning: probably need more aggressive PIDs due to the smaller allowable errors
 * probably also need i-terms and iZone
 */
public class TrapezoidMec extends Mecanum
{
    public TrapezoidMec(Limelight ll) { super(ll); }

    Timer profileTimer = new Timer();

    
    boolean isFirstCallDriving, isCompleteDriving;
    @Override
    public boolean driveToDistance(double distance)
    {
        final TrapezoidProfile distanceProfile = new TrapezoidProfile(
            new TrapezoidProfile.Constraints(10, 20),   // TODO: wat values ?
            new TrapezoidProfile.State(distance, 0));   // end state is stationary @ distance

        if (isFirstCallDriving)
        {
            profileTimer.reset();
            profileTimer.start();
            isFirstCallDriving = true;
        }
        else if (isCompleteDriving)
            isFirstCallDriving = false;
        

        double currentTime = profileTimer.get();
        isCompleteDriving = distanceProfile.isFinished(currentTime);

        var curSetpoint = distanceProfile.calculate(currentTime);

        super.driveToDistance(curSetpoint.position);

        return isCompleteDriving;
    }

    
    
    boolean isFirstCallTurning, isCompleteTurning;
    @Override
    public boolean turnToAngle(double angle)
    {
        final TrapezoidProfile turnProfile = new TrapezoidProfile(
            new TrapezoidProfile.Constraints(10, 20),   // TODO: wat values ?
            new TrapezoidProfile.State(angle, 0));      // end state is stationary @ distance

        if (isFirstCallTurning)
        {
            profileTimer.reset();
            profileTimer.start();
            isFirstCallTurning = true;
        }
        else if (isCompleteTurning)
            isFirstCallTurning = false;
        

        double currentTime = profileTimer.get();
        isCompleteDriving = turnProfile.isFinished(currentTime);

        var curSetpoint = turnProfile.calculate(currentTime);

        super.turnToAngle(curSetpoint.position);

        return isCompleteDriving;
    }
}
