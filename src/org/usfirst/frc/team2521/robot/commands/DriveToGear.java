package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Command for driving to the gear automatically
 */
public class DriveToGear extends Command {
	private static final double P = 0.002;
	private static final double I = 0;
	private static final double D = 0;
	
	public DriveToGear() {
		//super(P, I, D);
		requires(Robot.drivetrain);
	}
	
	@Override
	protected void execute() {
		if (Math.abs(Robot.sensors.getCVOffsetX()) < 20) {
			Robot.drivetrain.setLeft(-.3);
			Robot.drivetrain.setRight(.3);
		}
		
		if (Robot.sensors.getCVOffsetX() < 0) {
			Robot.drivetrain.setLeft(-.2);
		} else {
			Robot.drivetrain.setRight(.2);
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
	/*
	@Override
	protected double returnPIDInput() {
		return Robot.sensors.getCVOffsetX();
	}

	@Override
	protected void usePIDOutput(double output) {
		if (Robot.DEBUG) SmartDashboard.putNumber("Drive to gear output", output);

		if (Math.abs(Robot.sensors.getCVOffsetX()) < 10) {
			Robot.drivetrain.setLeft(.5);
			Robot.drivetrain.setRight(.5);
		}
		
		// If CV offset is positive, we're too far left. If it's negative, we're too far right
		// This way we also are always moving forward
		if (Robot.sensors.getCVOffsetX() > 0) {
			Robot.drivetrain.setLeft(output);
		} else {
			Robot.drivetrain.setRight(output);
		}
	}
	*/
}
