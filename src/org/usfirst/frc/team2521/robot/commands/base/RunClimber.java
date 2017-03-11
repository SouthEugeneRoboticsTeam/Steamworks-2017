package org.usfirst.frc.team2521.robot.commands.base;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command allows a driver to control the climber during teleop.
 */
public class RunClimber extends Command {
	public RunClimber() {
		requires(Robot.climber);
	}

	@Override
	protected void execute() {
		Robot.climber.runClimber();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void end() {
		Robot.climber.stopClimber();
	}
}
