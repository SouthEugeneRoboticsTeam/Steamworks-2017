package org.usfirst.frc.team2521.robot.commands.automation;

import org.usfirst.frc.team2521.robot.Robot;
import org.usfirst.frc.team2521.robot.subsystems.Sensors;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnToBoiler extends PIDCommand {
	private static final double P = 0.002;
	private static final double I = 0;
	private static final double D = 0.001;
	
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
		return Math.abs(Robot.sensors.getCVOffsetX()) < 5;
	}

	@Override
	protected double returnPIDInput() {
		return Robot.sensors.getCVOffsetX();
	}

	@Override
	protected void usePIDOutput(double output) {
		if (Math.abs(output) < 0.1) {
			output = Math.signum(output) * 0.1;
		}
		Robot.drivetrain.setLeft(-output);
		Robot.drivetrain.setRight(-output);
	}

}
