package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveToUltra extends Command {
	private final static double ERROR_THRESHOLD = 1;
	
	private double setpoint;

	public DriveToUltra(double setpoint) {
		this.setpoint = setpoint;
	}

	protected void initialize() {
	}

	protected void execute() {
		SmartDashboard.putString("Com place", "Drive to " + setpoint + " inches");
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
