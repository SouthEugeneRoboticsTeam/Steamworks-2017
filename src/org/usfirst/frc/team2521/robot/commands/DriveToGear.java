package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Command for driving to the gear automatically
 */
public class DriveToGear extends PIDCommand {
	private static final double P = 0.008;
	private static final double I = 0;
	private static final double D = 0;

	private static final double CAMERA_PROJ_PLANE_DISTANCE = 216.226;

	private boolean oriented = false;
	// True if the robot is facing the gear straight

	public DriveToGear() {
		super(P, I, D);
		requires(Robot.drivetrain);
	}

	@Override
	protected void execute() {
		double targetAngle = Math.atan(Robot.sensors.getCVOffsetX() / CAMERA_PROJ_PLANE_DISTANCE);
		// Should be angle between camera line of sight and target

		targetAngle *= 180 / Math.PI; // Convert to degrees

		oriented = Math.abs(targetAngle) < 20;
		// We're considered oriented when our angle error is less than 10

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
		if(Robot.sensors.getBlobFound()) {
			// If we are already oriented, we just drive straight
			if (oriented) {
				Robot.drivetrain.setLeft(-.2);
				Robot.drivetrain.setRight(.2);
			} else if (output < 0) {
				Robot.drivetrain.setLeft(output);
			} else {
				Robot.drivetrain.setRight(output);
			}
		} else {
			Robot.drivetrain.setLeft(.2);
			Robot.drivetrain.setRight(.2);
		}
	}

}
