package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Command for driving to the gear automatically
 */
public class DriveToGear extends Command {
	double targetAngle;
	double initAngle;

	private final double CAMERA_PROJ_PLANE_DISTANCE = 216.226;
	/* Distance in pixels to imaginary camera projection plane
	Calculated from camera FOV (61.39) and half image width (380 pixels): (380/2)/tan(61.39/2) */
	
	private final double LIDAR_WIDTH = 25.6; //in
	
	public DriveToGear() {
		requires(Robot.drivetrain);
	}

	protected void initialize() {
		initAngle =  Robot.sensors.getNaxAngle();
	}

	protected void execute() {
		
		double alpha = Math.toDegrees(Math.atan((LIDAR_WIDTH/(Robot.sensors.getRightLidarInches() - Robot.sensors.getLeftLidarInches()))));
		// Gets the angle between camera's line of sight and a plane parallel to the wall
		
		double beta = Math.toDegrees(Math.atan(Robot.sensors.getCVOffsetX()/CAMERA_PROJ_PLANE_DISTANCE));
		// Angle between the line of sight of the camera and the vision target
		
		targetAngle = 0.5*alpha + beta;
		
		if(Robot.DEBUG) {
			SmartDashboard.putNumber("Init angle", initAngle);
			SmartDashboard.putNumber("Delta angle", Robot.sensors.getNaxAngle() - initAngle);
			SmartDashboard.putNumber("Alpha", alpha);
			SmartDashboard.putNumber("Beta", beta);
			SmartDashboard.putNumber("Target angle", targetAngle);
		}
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		SmartDashboard.putNumber("Target angle", targetAngle);
	}

	protected void interrupted() {
	}
}
