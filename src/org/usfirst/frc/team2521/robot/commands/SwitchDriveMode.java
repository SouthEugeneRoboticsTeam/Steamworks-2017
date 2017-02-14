package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SwitchDriveMode extends Command {

	@Override
	protected void initialize() {
		Robot.drivetrain.isArcade = !Robot.drivetrain.isArcade;
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
