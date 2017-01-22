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

	public DriveToGear() {
		requires(Robot.drivetrain);
	}

	protected void initialize() {
		initAngle =  Robot.sensors.getNaxAngle();
	}

	protected void execute() {
		
		if(Robot.DEBUG) {
			SmartDashboard.putNumber("Init angle", initAngle);
			
			SmartDashboard.putNumber("Delta angle", Robot.sensors.getNaxAngle() - initAngle);
		}
		
		double alpha = Robot.sensors.getAngleFromCamToWallPlane();
		SmartDashboard.putNumber("Alpha", alpha);
		double beta = Robot.sensors.getAngleFromCamToVisionTarget();
		targetAngle = 0.5*alpha + beta;
		SmartDashboard.putNumber("Target angle", targetAngle);
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
