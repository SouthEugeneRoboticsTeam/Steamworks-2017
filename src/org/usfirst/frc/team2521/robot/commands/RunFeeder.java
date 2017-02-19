package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command feeds the balls to the shooter.
 */
public class RunFeeder extends Command {
	private static final double FEEDER_SPEED = 0.75;

	public RunFeeder() {
		requires(Robot.feeder);
	}

	@Override
	protected void execute() {
		Robot.feeder.setMotor(-FEEDER_SPEED);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.feeder.setMotor(0);
	}
}
