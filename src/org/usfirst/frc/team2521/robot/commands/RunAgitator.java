package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class RunAgitator extends Command {
	private boolean forward;

	public RunAgitator(boolean forward) {
		requires(Robot.agitator);
		this.forward = forward;
	}

	@Override
	protected void execute() {
		if (forward) {
			Robot.agitator.setMotor(-1);
		} else {
			Robot.agitator.setMotor(1);
		}
	}

	@Override
	protected void end() {
		Robot.agitator.setMotor(0);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
