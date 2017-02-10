package org.usfirst.frc.team2521.robot.commands.shooter;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 * This command feeds the balls to the shooter.
 */
public class RunFeeder extends TimedCommand {
	public RunFeeder(double timeout) {
		super(timeout);

		requires(Robot.feeder);
	}

	@Override
	protected void execute() {
		Robot.feeder.setMotor(0.7);
	}

	@Override
	protected void end() {
		Robot.feeder.setMotor(0);
	}
}
