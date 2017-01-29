package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Allows a driver to control the drivetrain during teleop
 */
public class TeleopDrivetrain extends Command {
	public TeleopDrivetrain() {
		requires(Robot.drivetrain);
	}

	@Override
	protected void execute() {
		Robot.drivetrain.teleoperatedDrive();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
