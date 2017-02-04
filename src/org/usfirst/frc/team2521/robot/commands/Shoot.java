package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class Shoot extends TimedCommand {
	public Shoot(double timeout) {
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
