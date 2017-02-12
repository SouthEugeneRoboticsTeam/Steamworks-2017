package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveToUltra extends Command {
	private final static double ERROR_THRESHOLD = 1;
	
	private static final double X_SETPOINT = 21;
	private static final double ROBOT_HALF_WIDTH = 15.75;
	private static final double SIDE_ULTRA_OFFSET = 19.5;
	private static final double ROBOT_LENGTH = 26;
	
	private double setpoint;

	public DriveToUltra(double setpoint) {
		this.setpoint = setpoint;
	}
	
	public DriveToUltra() {
		setpoint = Robot.sensors.getSideLidarInches() + X_SETPOINT*Math.sqrt(2) + ROBOT_HALF_WIDTH - SIDE_ULTRA_OFFSET - 0.5*ROBOT_LENGTH;
	}

	protected void execute() {
		if (setpoint - Robot.sensors.getFrontUltraInches() > 0) {
			Robot.drivetrain.setLeft(.2);
			Robot.drivetrain.setRight(-.2);
		} else {
			Robot.drivetrain.setLeft(-.2);
			Robot.drivetrain.setRight(.2);
		}
		
	}

	protected boolean isFinished() {
		SmartDashboard.putNumber("Error", setpoint - Robot.sensors.getFrontUltraInches());
		return Math.abs(setpoint - Robot.sensors.getFrontUltraInches()) < ERROR_THRESHOLD;
	}
}
