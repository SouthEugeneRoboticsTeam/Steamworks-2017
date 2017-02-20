package org.usfirst.frc.team2521.robot.commands.automation;

import org.usfirst.frc.team2521.robot.Robot;
import org.usfirst.frc.team2521.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2521.robot.subsystems.Sensors;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This command drives to the gear drop-off automatically.
 */
public class DriveToGear extends PIDCommand {
	private static final double P = 0.008;
	private static final double I = 0;
	private static final double D = 0;

	private static final double CAMERA_PROJ_PLANE_DISTANCE = 216.226;

	/** true if we're straight-on */
	private boolean oriented = false;
	/** true if we're on the left side of the target */
	private boolean onLeftSide;

	private boolean hasFoundBlob = false;

	/**
	 * @param onLeftSide whether we're on the left side of the target
	 */
	public DriveToGear(boolean onLeftSide) {
		super(P, I, D);
		this.onLeftSide = onLeftSide;
		requires(Robot.drivetrain);
	}

	@Override
	protected void initialize() {
		Robot.sensors.setCVCamera(Sensors.Camera.FRONT);
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

		if (Robot.sensors.getBlobFound()) {
			hasFoundBlob = true;
		}

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
		if (Robot.DEBUG) {
			SmartDashboard.putNumber("Drive to gear output", output);
		}
		if (Robot.sensors.getBlobFound()) {
			// If we are already oriented, drive straight
			if (oriented) {
				Robot.drivetrain.setLeft(Drivetrain.SLOW_SPEED);
				Robot.drivetrain.setRight(-Drivetrain.SLOW_SPEED);
			} else if (output < 0) {
				Robot.drivetrain.setLeft(output);
			} else {
				Robot.drivetrain.setRight(output);
			}
		} else {
			if (hasFoundBlob) {
				Robot.drivetrain.setLeft(Drivetrain.SLOW_SPEED);
				Robot.drivetrain.setRight(-Drivetrain.SLOW_SPEED);
			} else {
				// Turn clockwise if we're too far left, counter-clockwise if we're too far right
				Robot.drivetrain.setLeft(onLeftSide ? -Drivetrain.SLOW_SPEED : Drivetrain.SLOW_SPEED);
				Robot.drivetrain.setRight(onLeftSide ? -Drivetrain.SLOW_SPEED : Drivetrain.SLOW_SPEED);
			}
		}
	}
}
