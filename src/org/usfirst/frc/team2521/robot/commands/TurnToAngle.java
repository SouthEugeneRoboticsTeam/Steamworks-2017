package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TurnToAngle extends PIDCommand {
	private static final double P = 0.008;
	private static final double I = 0;
	private static final double D = 0;
	
	private final static double ERROR_THRESHOLD = 20;
	
	private double targetAngle;

	public TurnToAngle(double deltaAngle) {
		super(P, I, D);
		requires(Robot.drivetrain);
		targetAngle = deltaAngle;
	}

	@Override
	protected void initialize() {
		targetAngle += Robot.sensors.getNavxAngle();
	}
	
	@Override
	protected void execute() {
		SmartDashboard.putString("Com place", "Drive to angle");
		setSetpoint(targetAngle);
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(targetAngle - Robot.sensors.getNavxAngle()) < ERROR_THRESHOLD;
	}

	@Override
	protected double returnPIDInput() {
		return Robot.sensors.getNavxAngle();
	}

	@Override
	protected void usePIDOutput(double output) {
		if (output < 0) {
			Robot.drivetrain.setLeft(output);
		} else {
			Robot.drivetrain.setRight(output);
		}
	}
}
