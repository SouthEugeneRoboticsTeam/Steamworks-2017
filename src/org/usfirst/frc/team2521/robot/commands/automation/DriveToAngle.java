package org.usfirst.frc.team2521.robot.commands.automation;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 * This command drives to a specified angle using the navX.
 */
public class DriveToAngle extends PIDCommand {
	private static final double P = 0.003;
	private static final double I = 0;
	private static final double D = 0;

	private final static double ERROR_THRESHOLD = 1;

	private static final double MIN_OUTPUT = 0.1;

	private double targetAngle;

	/**
	 * @param targetAngle the angle we want to drive to
	 */
	public DriveToAngle(double targetAngle) {
		super(P, I, D);
		requires(Robot.drivetrain);
		this.targetAngle = targetAngle;
		getPIDController().setAbsoluteTolerance(ERROR_THRESHOLD);
	}

	@Override
	protected void initialize() {
		Robot.sensors.resetNavxAngle();
		targetAngle += Robot.sensors.getNavxAngle();
		setSetpoint(targetAngle);
	}

	@Override
	protected boolean isFinished() {
		return getPIDController().onTarget();
	}

	@Override
	protected double returnPIDInput() {
		return Robot.sensors.getNavxAngle();
	}

	@Override
	protected void usePIDOutput(double output) {
		if (Math.abs(output) < MIN_OUTPUT) {
			output = Math.signum(output) * MIN_OUTPUT;
		}
		Robot.drivetrain.setLeft(output);
		Robot.drivetrain.setRight(output);
	}
}
