package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDShoot extends PIDCommand {

	private static final double P = 0.00005;
	private static final double I = 0.000001;
	private static final double D = 0.0000005;

	private static final int setpoint = 27500;

	public PIDShoot() {
		super(P, I, D);

		requires(Robot.shooter);
	}

	@Override
	public void execute() {
		setSetpoint(setpoint);

		SmartDashboard.putNumber("Encoder Value", Robot.shooter.getEncVelocity());
		SmartDashboard.putNumber("Error", setpoint - Robot.shooter.getEncVelocity());
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
