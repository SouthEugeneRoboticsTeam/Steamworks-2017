package org.usfirst.frc.team2521.robot.commands.automation;

import org.usfirst.frc.team2521.robot.Robot;
import org.usfirst.frc.team2521.robot.subsystems.Sensors;

import edu.wpi.first.wpilibj.command.PIDCommand;

import static org.usfirst.frc.team2521.robot.subsystems.Sensors.Camera;

public class TurnToBoiler extends PIDCommand {
	private static final double P = 0.002;
	private static final double I = 0.00015;
	private static final double D = 0;

	public TurnToBoiler() {
		super(P, I, D);
		requires(Robot.drivetrain);
		requires(Robot.camera);
	}

	@Override
	protected void initialize() {
		Robot.sensors.setCVCamera(Camera.Type.REAR);
		Robot.camera.setRunning(true);
	}

	@Override
	protected boolean isFinished() {
		Double offsetX = Robot.sensors.getCVOffsetX();
		return offsetX != null && Math.abs(offsetX) < 5;
	}

	@Override
	protected double returnPIDInput() {
		Double offsetX = Robot.sensors.getCVOffsetX();
		return offsetX == null ? Sensors.DEFAULT_CV_OFFSET : offsetX;
	}

	@Override
	protected void usePIDOutput(double output) {
		if (Robot.sensors.hasFoundBlob()) {
			Robot.drivetrain.setLeft(-output);
			Robot.drivetrain.setRight(output);
		} else {
			Robot.drivetrain.setLeft(0);
			Robot.drivetrain.setRight(0);
		}
	}

	@Override
	protected void end() {
		Robot.camera.setRunning(false);
	}
}
