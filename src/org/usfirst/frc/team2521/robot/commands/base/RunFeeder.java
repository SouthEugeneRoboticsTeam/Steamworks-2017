package org.usfirst.frc.team2521.robot.commands.base;

import org.usfirst.frc.team2521.robot.Robot;
import org.usfirst.frc.team2521.robot.subsystems.Feeder;

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
		Robot.feeder.setMotor(-Feeder.FEEDER_SPEED);
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
