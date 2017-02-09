package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This command spins up the flywheel and attempts to maintain a constant
 * speed.
 */
public class SpinShooter extends PIDCommand {
	private static final double P = 0.00005;
	private static final double I = 0.000001;
	private static final double D = 0.0000005;

	//private static final int SETPOINT = 27500;
	private int SETPOINT = 27500;

	public SpinShooter() {
		super(P, I, D);

		requires(Robot.shooter);
	}

	@Override
	public void execute() {
		SETPOINT = Preferences.getInstance().getInt("setpoint", 0);
		setSetpoint(SETPOINT);

		if (Robot.DEBUG) {
			SmartDashboard.putNumber("Encoder Value", -Robot.shooter.getEncVelocity());
			SmartDashboard.putNumber("Error", SETPOINT - -Robot.shooter.getEncVelocity());
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected double returnPIDInput() {
		return -Robot.shooter.getEncVelocity();
	}

	@Override
	protected void usePIDOutput(double output) {
		Robot.shooter.setMotor(0.7);
	}
}
