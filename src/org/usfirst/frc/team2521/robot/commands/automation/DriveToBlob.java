package org.usfirst.frc.team2521.robot.commands.automation;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 * This abstract class provides a template for driving to the gear or the boiler.
 */
public abstract class DriveToBlob extends PIDCommand {
	private static final double CAMERA_PROJ_PLANE_DISTANCE = 216.226;

	/** true if we're straight-on */
	protected boolean oriented = false;
	/** true if we're on the left side of the target */
	private boolean onLeftSide;
	private boolean hasFoundBlob = false;

	/**
	 * @param onLeftSide whether we're on the left side of the target
	 */
	protected DriveToBlob(double p, double i, double d, boolean onLeftSide) {
		super(p, i, d);
		this.onLeftSide = onLeftSide;
		requires(Robot.drivetrain);
	}

	protected abstract double getSlowSpeed();

	@Override
	protected abstract void initialize();

	@Override
	protected final void execute() {
		// Angle between camera line of sight and target
		double targetAngle = Math.atan(Robot.sensors.getCVOffsetX() / CAMERA_PROJ_PLANE_DISTANCE);

		// Convert angle to degrees
		targetAngle *= 180 / Math.PI;

		// Consider ourselves oriented when our angle error is less than 20
		oriented = Math.abs(targetAngle) < 20;

		targetAngle += Robot.sensors.getNavxAngle();
		setSetpoint(targetAngle);

		if (Robot.sensors.getBlobFound()) {
			hasFoundBlob = true;
		}
	}

	@Override
	protected final double returnPIDInput() {
		return Robot.sensors.getNavxAngle();
	}

	protected void setCurrentBlobFoundMotorSpeed() {
		if (hasFoundBlob) {
			Robot.drivetrain.setLeft(getSlowSpeed());
			Robot.drivetrain.setRight(getSlowSpeed());
		} else {
			// Turn clockwise if we're too far left, counter-clockwise if we're too far right
			Robot.drivetrain.setLeft(onLeftSide ? -getSlowSpeed() : getSlowSpeed());
			Robot.drivetrain.setRight(onLeftSide ? -getSlowSpeed() : getSlowSpeed());
		}
	}
}
