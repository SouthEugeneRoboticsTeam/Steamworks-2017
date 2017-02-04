package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command allows a driver to control the climber during teleop.
 */
public class TeleopClimber extends Command {
	/**
	 * Constructor.
	 */
	public TeleopClimber() {
		requires(Robot.climber);
	}

	@Override
	protected void execute() {
		Robot.climber.teleoperatedClimb();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
