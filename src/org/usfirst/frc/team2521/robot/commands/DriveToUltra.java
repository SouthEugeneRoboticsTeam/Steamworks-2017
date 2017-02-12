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
	
	private double setpoint;
	
	private boolean isAlignShooter;

	public DriveToUltra(double setpoint, boolean isAlignShooter) {
		this.setpoint = setpoint;
		this.isAlignShooter = isAlignShooter;
	}
	
	protected void initialize() {
		if (isAlignShooter) {
			setpoint = Robot.sensors.getSideLidarInches() + X_SETPOINT*Math.sqrt(2) + ROBOT_HALF_WIDTH - SIDE_ULTRA_OFFSET - 0.5*ROBOT_LENGTH;
		}
	}

	protected void execute() {
		Robot.drivetrain.setLeft((setpoint - Robot.sensors.getFrontUltraInches() > 0) ? .2 : -.2);
		Robot.drivetrain.setRight((setpoint - Robot.sensors.getFrontUltraInches() > 0) ? -.2 : .2);
	}

	protected boolean isFinished() {
		if (Robot.DEBUG) {
			SmartDashboard.putNumber("Drive to ultra setpoint", setpoint);
			SmartDashboard.putNumber("Drive to ultra error", setpoint - Robot.sensors.getFrontUltraInches());
		}
		return Math.abs(setpoint - Robot.sensors.getFrontUltraInches()) < ERROR_THRESHOLD;
	}
}
