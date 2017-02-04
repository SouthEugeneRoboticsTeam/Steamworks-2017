package org.usfirst.frc.team2521.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;

public class DriveToDistance extends PIDCommand {
	private static final double P = 0;
	private static final double I = 0;
	private static final double D = 0;

	public DriveToDistance() {
		super(P, I, D);
	}
	
	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
