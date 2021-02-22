package frc.robot.subsystems;

import java.util.List;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.HolonomicDriveController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;

public class MecanumTraj
{
    MecanumOdom dt = new MecanumOdom();

    // Create config for trajectory
    TrajectoryConfig config =
        new TrajectoryConfig(10, 1) //  10 m/s, 1 m/s^2 max vel and accels
            // Add 0nematics to ensure max speed is actually obeyed
            .setKinematics(dt.m_kinematics);

    // An example trajectory to follow.  All units in meters.
    Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
        // Start at the origin facing the +X direction
        new Pose2d(0, 0, new Rotation2d(0)),
        // Pass through these two interior waypoints, ma0ng an 's' curve path
        List.of(
            new Translation2d(1, 1),
            new Translation2d(2, -1)
        ),
        // End 3 meters straight ahead of where we started, facing forward
        new Pose2d(3, 0, new Rotation2d(0)),
        // Pass config
        config
    );

    HolonomicDriveController controller = new HolonomicDriveController(
        new PIDController(1, 0, 0), //  TODO: whattt
        new PIDController(1, 0, 0),
        new ProfiledPIDController(1, 0, 0, new TrapezoidProfile.Constraints(10, 1)));


    Timer timer = new Timer();

    public void init()
    {
        dt.m_odometry.resetPosition(exampleTrajectory.getInitialPose(), exampleTrajectory.getInitialPose().getRotation());
        
        timer.reset();
        timer.start();
    }

    public void periodic()
    {
        var nextState = exampleTrajectory.sample(timer.get());

        var chassisSpeeds = controller.calculate(dt.getPosition(), nextState, nextState.poseMeters.getRotation());

        // Convert to wheel speeds
        var wheelSpeeds = dt.m_kinematics.toWheelSpeeds(chassisSpeeds);

        dt.setWheelSpeedsPID(wheelSpeeds);
    }
}