package org.usfirst.frc.team2521.robot.commands.shooter;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This command spins up the flywheel and attempts to maintain a constant speed.
 */
public class SpinShooter extends PIDCommand {
	private static final double P = 0.01;
	private static final double I = 0;
	private static final double D = 0;

	private static final int SETPOINT = -150;

	public SpinShooter() {
		super(P, I, D);

		requires(Robot.shooter);
	}

	@Override
	public void initialize() {
		setSetpoint(SETPOINT);
	}

	@Override
	public void execute() {
		if (Robot.DEBUG) {
			SmartDashboard.putNumber("Encoder Value", -Robot.shooter.getEncVelocity());
			SmartDashboard.putNumber("Error", SETPOINT + Robot.shooter.getEncVelocity());
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.shooter.setMotor(0);
	}

	@Override
	protected void interrupted() {
		Robot.shooter.setMotor(0);
	}

	@Override
	protected double returnPIDInput() {
		System.out.println(Robot.shooter.getEncVelocity());
		return Robot.shooter.getEncVelocity();
	}

	@Override
	protected void usePIDOutput(double output) {
		System.out.println(-output);
		Robot.shooter.setMotor(-output);
	}
}
