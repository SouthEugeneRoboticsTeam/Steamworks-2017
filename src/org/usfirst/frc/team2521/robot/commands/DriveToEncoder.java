package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveToEncoder extends Command {
	private final double ENC_PULSES_PER_REVOLUTION = 8129;
	private final double WHEEL_DIAMETER = 6;
	
	private double leftDistance;
	private double rightDistance;

	public DriveToEncoder(double distance) {
		requires(Robot.drivetrain);
		
		distance = (distance / (WHEEL_DIAMETER * Math.PI)) * ENC_PULSES_PER_REVOLUTION;
		distance = 1000;
		SmartDashboard.putNumber("Left initial", Robot.drivetrain.getLeftPosition());
		SmartDashboard.putNumber("Right initial", Robot.drivetrain.getRightPosition());
		leftDistance = distance + Robot.drivetrain.getLeftPosition();
		SmartDashboard.putNumber("Left setpoint", leftDistance);
		rightDistance = distance + Robot.drivetrain.getRightPosition();
		SmartDashboard.putNumber("Right setpoint", rightDistance);
	}
	
	protected void execute() {
		Robot.drivetrain.setPosition(leftDistance);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
