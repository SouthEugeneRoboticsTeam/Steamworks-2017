package org.usfirst.frc.team2521.robot.commands.automation;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team2521.robot.subsystems.Drivetrain;

/**
 * This command drives to a specified angle using the navX.
 */
public class DriveToAngle extends PIDCommand {
	private static final double P = 0.02;
	private static final double I = 0;
	private static final double D = 0;

	private static final double ERROR_THRESHOLD = 3;
	private static final double MIN_OUTPUT = 0.1;

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
		
		if (Robot.DEBUG) {
			SmartDashboard.putString("Auto place", "DriveToAngle");
		}
	}

	@Override
	protected boolean isFinished() {
		System.out.println("Navx angle error" + (Robot.sensors.getNavxAngle() - targetAngle));
		return Math.abs(Robot.sensors.getNavxAngle() - targetAngle) < ERROR_THRESHOLD;
	}

	@Override
	protected double returnPIDInput() {
		return Robot.sensors.getNavxAngle();
	}

	@Override
	protected void usePIDOutput(double output) {
		System.out.println(output);
		/*if (Math.abs(output) < MIN_OUTPUT) {
			output = Math.signum(output) * MIN_OUTPUT;
		}
		Robot.drivetrain.setLeft(output);
		Robot.drivetrain.setRight(-output);*/
		Robot.drivetrain.setLeft(Math.copySign(Drivetrain.SLOW_SPEED, -output));
		Robot.drivetrain.setLeft(Math.copySign(Drivetrain.SLOW_SPEED, output));
	}
}