package org.usfirst.frc.team2521.robot.commands.shooter;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command feeds the balls to the shooter.
 */
public class RunFeeder extends Command {
	public RunFeeder() {
		requires(Robot.feeder);
	}

	@Override
	protected void execute() {
		Robot.feeder.setMotor(0.7);
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
