package org.usfirst.frc.team2521.robot.commands.automation;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;

public class TurnToBoiler extends PIDCommand {
	private static final double P = 0.002;
	private static final double I = 0;
	private static final double D = 0;
	
	public TurnToBoiler() {
		super(P, I, D);
		requires(Robot.drivetrain);
	}
	
	@Override
	protected void initialize() {
		Robot.sensors.setCVCamera(false);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected double returnPIDInput() {
		return Robot.sensors.getCVOffsetX();
	}

	@Override
	protected void usePIDOutput(double output) {
		Robot.drivetrain.setLeft(-output);
		Robot.drivetrain.setRight(-output);
	}

}
