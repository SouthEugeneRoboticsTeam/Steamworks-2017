package org.usfirst.frc.team2521.robot.commands.automation;

import org.usfirst.frc.team2521.robot.Robot;
import org.usfirst.frc.team2521.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2521.robot.subsystems.Sensors;

/**
 * This command drives to the correct spot to shoot from automatically.
 */
public class DriveToBoiler extends DriveToBlob {
	private static final double P = 0.008;
	private static final double I = 0;
	private static final double D = 0;

	private static final double DISTANCE_SETPOINT = 38;
	private static final double DISTANCE_ERROR_THRESHOLD = 1;

	public DriveToBoiler(boolean onLeftSide) {
		super(P, I, D, onLeftSide);
	}

	@Override
	protected double getSlowSpeed() {
		return -Drivetrain.SLOW_SPEED;
	}

	@Override
	protected void initialize() {
		Robot.sensors.setCVCamera(Sensors.Camera.REAR);
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(DISTANCE_SETPOINT - Robot.sensors.getRearUltraInches()) < DISTANCE_ERROR_THRESHOLD;
	}
}
