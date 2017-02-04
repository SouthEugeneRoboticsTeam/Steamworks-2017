package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive to the gear automatically
 */
public class DriveToGear extends PIDCommand {
	private static final double P = 0.008;
	private static final double I = 0;
	private static final double D = 0;

	private static final double CAMERA_PROJ_PLANE_DISTANCE = 216.226;

	// `true` if we're straight-on
	private boolean oriented = false;

	// `true` if we're on the left side of the target
	private boolean onLeftSide;

	public DriveToGear(boolean onLeftSide) {
		super(P, I, D);
		
		requires(Robot.drivetrain);
		
		this.onLeftSide = onLeftSide;
	}

	@Override
	protected void execute() {
		// Angle between camera line of sight and target
		double targetAngle = Math.atan(Robot.sensors.getCVOffsetX() / CAMERA_PROJ_PLANE_DISTANCE);

		// Convert angle to degrees
		targetAngle *= 180 / Math.PI;

		// Consider ourselves oriented when our angle error is less than 20
		oriented = Math.abs(targetAngle) < 20;

		targetAngle += Robot.sensors.getNavxAngle();

		setSetpoint(targetAngle);

		if (Robot.DEBUG) {
			SmartDashboard.putNumber("Drive to gear setpoint", targetAngle);
			SmartDashboard.putBoolean("Oriented", oriented);
		}
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
		if (Robot.sensors.getBlobFound()) {
			// If we are already oriented, drive straight
			if (oriented) {
				Robot.drivetrain.setLeft(-0.2);
				Robot.drivetrain.setRight(0.2);
			} else if (output < 0) {
				Robot.drivetrain.setLeft(output);
			} else {
				Robot.drivetrain.setRight(output);
			}
		} else {
			// Turn clockwise if we're too far left, counter-clockwise if we're too far right
			Robot.drivetrain.setLeft(onLeftSide ? .2 : -0.2);
			Robot.drivetrain.setRight(onLeftSide ? .2 : -0.2);
		}
	}
}
