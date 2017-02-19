package org.usfirst.frc.team2521.robot.commands.automation;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveToEncoder extends Command {
	private final double ENC_PULSES_PER_REVOLUTION = 8192;
	private final double WHEEL_DIAMETER = 6;

	private double distance;

	public DriveToEncoder(double distance) {
		requires(Robot.drivetrain);

		distance = (distance / (WHEEL_DIAMETER * Math.PI)) * ENC_PULSES_PER_REVOLUTION;
		distance += Robot.drivetrain.getRightPosition();

		this.distance = distance;
	}

	protected void execute() {
		Robot.drivetrain.setPosition(distance);
	}

	@Override
	protected void end() {
		Robot.drivetrain.setLeft(0);
		Robot.drivetrain.setRight(0);
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(Robot.drivetrain.getRightEncoderError()) < 100;
	}
}
