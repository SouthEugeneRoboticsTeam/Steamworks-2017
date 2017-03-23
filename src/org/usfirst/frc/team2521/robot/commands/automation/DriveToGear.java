package org.usfirst.frc.team2521.robot.commands.automation;

import org.usfirst.frc.team2521.robot.Robot;
import org.usfirst.frc.team2521.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static org.usfirst.frc.team2521.robot.subsystems.Sensors.Camera;

/**
 * This command drives to the gear drop-off automatically.
 */
public class DriveToGear extends DriveToBlob {
	private static final double P = 0.008;
	private static final double I = 0;
	private static final double D = 0;
	private boolean hasFoundBlob = false;

	public DriveToGear() {
		super(P, I, D);
	}

	private double getSlowSpeed() {
		return Drivetrain.SLOW_SPEED;
	}

	@Override
	protected void initialize() {
		Robot.sensors.setCVCamera(Camera.Type.FRONT);
	}

	@Override
	protected boolean isFinished() {
		return !Robot.sensors.hasFoundBlob();
	}

	@Override
	protected final void usePIDOutput(double output) {
		if (Robot.DEBUG) {
			SmartDashboard.putNumber("Drive to gear output", output);
			SmartDashboard.putBoolean("Has found blob", hasFoundBlob);
		}
		if (Robot.sensors.hasFoundBlob()) {
			hasFoundBlob = true;
			// If we are already oriented, drive straight
			if (oriented) {
				Robot.drivetrain.setLeft(getSlowSpeed());
				Robot.drivetrain.setRight(getSlowSpeed());
			} else if (output > 0) {
				Robot.drivetrain.setLeft(output);
			} else {
				Robot.drivetrain.setRight(-output);
			}
		} else {
			if (hasFoundBlob) {
				Robot.drivetrain.setLeft(getSlowSpeed());
				Robot.drivetrain.setRight(getSlowSpeed());
			}
		}
	}

	@Override
	protected double returnPIDInput() {
		return Robot.sensors.getNavxAngle();
	}
}
