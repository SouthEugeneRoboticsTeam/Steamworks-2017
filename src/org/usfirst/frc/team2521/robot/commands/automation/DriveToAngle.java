package org.usfirst.frc.team2521.robot.commands.automation;

import org.usfirst.frc.team2521.robot.Robot;
import org.usfirst.frc.team2521.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This command drives to a specified angle using the navX.
 */
public class DriveToAngle extends PIDCommand {
	private static final double P = 0.02;
	private static final double I = 0;
	private static final double D = 0;

	private static final double ERROR_THRESHOLD = 1;

	private double targetAngle;

	public DriveToAngle(double targetAngle) {
		super(P, I, D);
		this.targetAngle = targetAngle;

		requires(Robot.drivetrain);
	}

	@Override
	protected void initialize() {
		Robot.sensors.resetNavxAngle();
		targetAngle += Robot.sensors.getNavxAngle();
		setSetpoint(targetAngle);
	}

	@Override
	protected void execute() {
		if (Robot.DEBUG) {
			SmartDashboard.putNumber("Navx angle error",
									 (Robot.sensors.getNavxAngle() - targetAngle));
		}
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(Robot.sensors.getNavxAngle() - targetAngle) < ERROR_THRESHOLD;
	}

	@Override
	protected double returnPIDInput() {
		return Robot.sensors.getNavxAngle();
	}

	@Override
	protected void usePIDOutput(double output) {
		if (output > 0) {
			Robot.drivetrain.setLeft(Drivetrain.SLOW_SPEED);
		} else {
			Robot.drivetrain.setRight(Drivetrain.SLOW_SPEED);
		}
	}
}