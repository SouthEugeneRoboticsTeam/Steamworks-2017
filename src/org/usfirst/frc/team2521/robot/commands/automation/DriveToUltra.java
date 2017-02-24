package org.usfirst.frc.team2521.robot.commands.automation;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This command drives to a specified ultrasonic distance automatically.
 */
public class DriveToUltra extends PIDCommand {
	private static final double P = 0.01;
	private static final double I = 0;
	private static final double D = 0;

	private static final double ERROR_THRESHOLD = 1;

	private double setpoint;

	/**
	 * @param setpoint the ultrasonic setpoint
	 */
	public DriveToUltra(double setpoint) {
		super(P, I, D);
		this.setpoint = setpoint;
		requires(Robot.drivetrain);

		setSetpoint(setpoint);
	}

	@Override
	protected boolean isFinished() {
		if (Robot.DEBUG) {
			SmartDashboard.putNumber("Drive to ultra setpoint", setpoint);
			SmartDashboard.putNumber("Drive to ultra error", getPIDController().getAvgError());
		}
		return Math.abs(setpoint) < ERROR_THRESHOLD;
	}

	@Override
	protected double returnPIDInput() {
		return Robot.sensors.getFrontUltraInches();
	}

	@Override
	protected void usePIDOutput(double output) {
		Robot.drivetrain.setLeft(output);
		Robot.drivetrain.setRight(-output);
	}
}
