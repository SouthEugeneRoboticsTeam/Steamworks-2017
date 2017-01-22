package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shoot extends PIDCommand {
	private static final double P = 0.0001;
	private static final double I = 0.000001;
	private static final double D = 0.0001;
	
	public Shoot() {
		super(P, I, D);
		requires(Robot.shooter);
	}
	
	public void execute() {
		setSetpoint(27000); // 25925.0
		SmartDashboard.putNumber("Encoder value", Robot.shooter.getEncVelocity());
		SmartDashboard.putNumber("Error", 27500-Robot.shooter.getEncVelocity());
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected double returnPIDInput() {
		return Robot.shooter.getEncVelocity();
	}

	@Override
	protected void usePIDOutput(double output) {
		Robot.shooter.setMotor(output);
	}

}
