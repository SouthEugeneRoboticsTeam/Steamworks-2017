package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveToUltra extends Command {
	private final static double ERROR_THRESHOLD = 1;

	// How far from the right side of the boiler the robot's center should be
	private static final double X_SETPOINT = 21;

	private static final double ROBOT_HALF_WIDTH = 15.75;

	// How far the side ultrasonic is from front of the robot
	private static final double SIDE_ULTRA_OFFSET = 19.5;

	private static final double ROBOT_LENGTH = 26;

	private double ultrasonicValue;
	private double setpoint;

	// `true` if we should calculate setpoint using align shooter formula
	private boolean isAlignShooter;

	// `true` if we should use the front ultrasonic
	private boolean useRearUltra = false;

	public DriveToUltra(double setpoint, boolean isAlignShooter) {
		this.setpoint = setpoint;
		this.isAlignShooter = isAlignShooter;
	}

	public DriveToUltra(double setpoint, boolean isAlignShooter, boolean useRearUltra) {
		this.setpoint = setpoint;
		this.isAlignShooter = isAlignShooter;
		this.useRearUltra = useRearUltra;
	}

	protected void initialize() {
		if (isAlignShooter) {
			setpoint = Robot.sensors.getSideLidarInches() + X_SETPOINT * Math.sqrt(2) + ROBOT_HALF_WIDTH
					- SIDE_ULTRA_OFFSET - 0.5 * ROBOT_LENGTH;
		}
	}

	protected void execute() {
		if (useRearUltra) {
			ultrasonicValue = Robot.sensors.getRearUltraInches();
		} else {
			ultrasonicValue = Robot.sensors.getFrontUltraInches();
		}

		Robot.drivetrain.setLeft((setpoint - ultrasonicValue > 0) ? .2 : -.2);
		Robot.drivetrain.setRight((setpoint - ultrasonicValue > 0) ? -.2 : .2);
	}

	protected boolean isFinished() {
		if (Robot.DEBUG) {
			SmartDashboard.putNumber("Drive to ultra setpoint", setpoint);
			SmartDashboard.putNumber("Drive to ultra error", setpoint - ultrasonicValue);
		}
		return Math.abs(setpoint - ultrasonicValue) < ERROR_THRESHOLD;
	}
}
