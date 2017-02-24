package org.usfirst.frc.team2521.robot.commands.automation;

import org.usfirst.frc.team2521.robot.Robot;
import org.usfirst.frc.team2521.robot.subsystems.Sensors;

import edu.wpi.first.wpilibj.command.PIDCommand;

public class TurnToBoiler extends PIDCommand {
	private static final double P = 0.0025;
	private static final double I = 0.00015;
	private static final double D = 0;

	public TurnToBoiler() {
		super(P, I, D);
		requires(Robot.drivetrain);
	}

	@Override
	protected void initialize() {
		Robot.sensors.setCVCamera(Sensors.Camera.REAR);
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
		if (Robot.sensors.getBlobFound()) {
			Robot.drivetrain.setLeft(-output);
			Robot.drivetrain.setRight(output);
		} else {
			Robot.drivetrain.setLeft(0);
			Robot.drivetrain.setRight(0);
		}
	}
}
