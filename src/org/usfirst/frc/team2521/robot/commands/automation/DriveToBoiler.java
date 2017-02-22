package org.usfirst.frc.team2521.robot.commands.automation;

import org.usfirst.frc.team2521.robot.Robot;
import org.usfirst.frc.team2521.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2521.robot.subsystems.Sensors;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This command drives to the correct spot to shoot from automatically.
 */
public class DriveToBoiler extends DriveToBlob {
	private static final double P = 0.008;
	private static final double I = 0;
	private static final double D = 0;

	private static final double DISTANCE_SETPOINT = 39;
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
	
	@Override
	protected final void usePIDOutput(double output) {
		if (Robot.DEBUG) {
			SmartDashboard.putNumber("Drive to gear output", output);
		}
		if (Robot.sensors.getBlobFound()) {
			// If we are already oriented, drive straight
			SmartDashboard.putBoolean("Oriented", oriented);
			if (oriented) {
				Robot.drivetrain.setLeft(getSlowSpeed());
				Robot.drivetrain.setRight(getSlowSpeed());
			} else if (-output > 0) {
				Robot.drivetrain.setLeft(output);
			} else {
				Robot.drivetrain.setRight(-output);
			}
		} else {
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
}
