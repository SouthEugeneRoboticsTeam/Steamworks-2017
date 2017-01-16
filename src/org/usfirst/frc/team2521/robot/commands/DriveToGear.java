package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveToGear extends Command {

	public DriveToGear() {
		requires(Robot.drivetrain);
	}

	protected void initialize() {}

	protected void execute() {}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {}

	protected void interrupted() {
	}
}
