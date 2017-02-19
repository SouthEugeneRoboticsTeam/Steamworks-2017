package org.usfirst.frc.team2521.robot.commands.base;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ToggleCamera extends Command {
	@Override
	protected void initialize() {
		Robot.sensors.toggleCVCamera();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}