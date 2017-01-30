package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 * Command for driving to the gear automatically
 */
public class DriveToGear extends PIDCommand {
	private static final double P = 0.01;
	private static final double I = 0;
	private static final double D = 0.001;
	
	private static final double CAMERA_PROJ_PLANE_DISTANCE = 216.226;
	
	public DriveToGear() {
		super(P, I, D);
		requires(Robot.drivetrain);
	}
	
	@Override
	protected void execute() {
		double targetAngle = Math.atan(Robot.sensors.getCVOffsetX()/CAMERA_PROJ_PLANE_DISTANCE);
		// Should be angle between camera line of sight and target
		
		targetAngle *= 180/Math.PI; // Convert to degrees
		
		targetAngle += Robot.sensors.getNavxAngle();
		setSetpoint(targetAngle);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected double returnPIDInput() {
		return Robot.sensors.getNavxAngle();
	}

	@Override
	protected void usePIDOutput(double output) {
		if (output < 0) {
			Robot.drivetrain.setLeft(output);
		} else {
			Robot.drivetrain.setRight(output);
		}
	}

}
