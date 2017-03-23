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

	protected DriveToBlob(double p, double i, double d) {
		super(p, i, d);
		requires(Robot.drivetrain);
	}

	@Override
	protected final void execute() {
		// Angle between camera line of sight and target
		Double offsetX = Robot.sensors.getCVOffsetX();
		if (offsetX == null) return;

		double targetAngle = Math.atan(offsetX / CAMERA_PROJ_PLANE_DISTANCE);

		// Convert angle to degrees
		targetAngle *= 180 / Math.PI;

		// Consider ourselves oriented when our angle error is less than 20
		oriented = Math.abs(targetAngle) < 20;

		targetAngle += Robot.sensors.getNavxAngle();
		setSetpoint(targetAngle);
	}
}
