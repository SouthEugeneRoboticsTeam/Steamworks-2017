package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 * Command for driving to the gear automatically
 */
public class DriveToGear extends PIDCommand {
	private static final double P = 0.005;
	private static final double I = 0;
	private static final double D = 0.001;
	
	private static final double STRAIGHT_SPEED = 0.7;
	/**
	 * Speed to go at once we're centered on the target
	 */
	
	private static final double MAX_TURN_SPEED = 0.3;
	/**
	 * Maximum speed to go while still turning toward the target
	 */
	
	private static final double OFFSET_CUTOFF = 20;
	
	public DriveToGear() {
		super(P, I, D);
		requires(Robot.drivetrain);
	}
	

	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected double returnPIDInput() {
		return Robot.sensors.getCVOffsetX();
	}

	@Override
	protected void usePIDOutput(double output) {
		if (Math.abs(output) > MAX_TURN_SPEED) {
			output = MAX_TURN_SPEED * Math.signum(output);
		}
		
		if (Math.abs(Robot.sensors.getCVOffsetX()) < OFFSET_CUTOFF) {
			Robot.drivetrain.setLeft(-STRAIGHT_SPEED);
			Robot.drivetrain.setRight(STRAIGHT_SPEED);
		}
		
		// If CV offset is positive, we're too far left. If it's negative, we're too far right
		// This way we also are always moving forward
		if (Robot.sensors.getCVOffsetX() < 0) {
			Robot.drivetrain.setLeft(-output);
			Robot.drivetrain.setRight(0.5*output);
		} else {
			Robot.drivetrain.setLeft(0.5*output);
			Robot.drivetrain.setRight(-output);
		}
	}

}
