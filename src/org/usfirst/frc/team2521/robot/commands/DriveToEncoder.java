package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveToEncoder extends Command {
	private int leftEncoderStart;
	private int rightEncoderStart;
	
	private int setpoint;

	public DriveToEncoder(int setpoint) {
		requires(Robot.drivetrain);

		this.setpoint = setpoint;
	}

	@Override
	protected void initialize() {
		leftEncoderStart = Robot.drivetrain.getLeftEnc();
		rightEncoderStart = Robot.drivetrain.getLeftEnc();
	}
	
	@Override
	protected void execute() {
		Robot.drivetrain.setLeft(0.5);
		Robot.drivetrain.setRight(0.5);
	}

	@Override
	protected boolean isFinished() {
		System.out.println("Left: " + (Robot.drivetrain.getLeftEnc() - leftEncoderStart));
		System.out.println("Right: " + (Robot.drivetrain.getRightEnc() - rightEncoderStart));
		
		if (Robot.drivetrain.getLeftEnc() - leftEncoderStart >= setpoint && Robot.drivetrain.getRightEnc() - rightEncoderStart >= setpoint) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void end() {
		Robot.drivetrain.setLeft(0);
		Robot.drivetrain.setRight(0);
	}
}
