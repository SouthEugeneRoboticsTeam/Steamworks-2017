package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This command drives to a specified ultrasonic distance automatically.
 */
public class DriveToUltra extends Command {
	private final static double ERROR_THRESHOLD = 1;

	protected double setpoint;
	/** {@code true} if we should use the front ultrasonic */
	protected boolean useRearUltra = false;
	private double ultrasonicValue;

	/**
	 * @param setpoint     the ultrasonic setpoint
	 * @param useRearUltra whether we should use the rear ultrasonic
	 */
	public DriveToUltra(double setpoint, boolean useRearUltra) {
		this.setpoint = setpoint;
		this.useRearUltra = useRearUltra;
	}

	@Override
	protected void execute() {
		ultrasonicValue = useRearUltra ? Robot.sensors.getRearUltraInches() : Robot.sensors.getFrontUltraInches();

		if (useRearUltra) {
			Robot.drivetrain.setLeft(setpoint - ultrasonicValue > 0 ? .2 : -.2);
			Robot.drivetrain.setRight(setpoint - ultrasonicValue > 0 ? -.2 : .2);
		} else {
			Robot.drivetrain.setLeft(setpoint - ultrasonicValue > 0 ? -.2 : .2);
			Robot.drivetrain.setRight(setpoint - ultrasonicValue > 0 ? .2 : -.2);
		}
	}

	@Override
	protected boolean isFinished() {
		if (Robot.DEBUG) {
			SmartDashboard.putNumber("Drive to ultra setpoint", setpoint);
			SmartDashboard.putNumber("Drive to ultra error", setpoint - ultrasonicValue);
		}
		return Math.abs(setpoint - ultrasonicValue) < ERROR_THRESHOLD;
	}
}
