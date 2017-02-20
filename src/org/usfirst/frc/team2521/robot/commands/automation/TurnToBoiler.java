package org.usfirst.frc.team2521.robot.commands.automation;

import org.usfirst.frc.team2521.robot.Robot;
import org.usfirst.frc.team2521.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2521.robot.subsystems.Sensors;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnToBoiler extends PIDCommand {
	private static final double P = 0.004;
	private static final double I = 0;//0.00015;
	private static final double D = 0;
	
	public TurnToBoiler() {
		super(P, I, D);
		requires(Robot.drivetrain);
	}
	
	@Override
	protected void initialize() {
		SmartDashboard.putBoolean("Done", false);
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
	protected void end() {
		SmartDashboard.putBoolean("Done", true);
	}

	@Override
	protected void usePIDOutput(double output) {
		if (Robot.sensors.getBlobFound()) {
			SmartDashboard.putNumber("Turn to boiler output", output);
			Robot.drivetrain.setLeft(-output);
			Robot.drivetrain.setRight(-output);
		} else {
			Robot.drivetrain.setLeft(0);
			Robot.drivetrain.setRight(0);
		}
		
	}

}
