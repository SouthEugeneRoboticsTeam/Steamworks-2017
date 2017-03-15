package org.usfirst.frc.team2521.robot.commands.automation;

import org.usfirst.frc.team2521.robot.Robot;
import org.usfirst.frc.team2521.robot.subsystems.Sensors;

/**
 * This command drives to the correct spot to shoot from automatically.
 */
public class DriveToBoiler extends DriveToBlob {
	private static final double P = 0.008;
	private static final double I = 0;
	private static final double D = 0;

	private static final double P2 = 0.0125;
	private static final double DISTANCE_SETPOINT = 35;
	private static final double DISTANCE_ERROR_THRESHOLD = 3;

	public DriveToBoiler() {
		super(P, I, D, false);
	}

	@Override
	protected double getSlowSpeed() {
		return P2 * (DISTANCE_SETPOINT - Robot.sensors.getRearUltraInches());
	}

	@Override
	protected void initialize() {
		Robot.sensors.setCVCamera(Sensors.Camera.REAR);
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(DISTANCE_SETPOINT - Robot.sensors.getRearUltraInches()) < DISTANCE_ERROR_THRESHOLD;
	}

	@Override
	protected final void usePIDOutput(double output) {
		if (Robot.sensors.getBlobFound()) {
			// If we are already oriented, drive straight
			if (oriented) {
				Robot.drivetrain.setLeft(getSlowSpeed());
				Robot.drivetrain.setRight(getSlowSpeed());
			} else if (-output > 0) {
				Robot.drivetrain.setLeft(output);
			} else {
				Robot.drivetrain.setRight(-output);
			}
		}
	}

}
