package org.usfirst.frc.team2521.robot.commands.base;

import org.usfirst.frc.team2521.robot.vision.Looper;

import edu.wpi.first.wpilibj.command.Command;

public class CameraLooper extends Command {
	@Override
	protected void execute() {
		Looper.getInstance().loop();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
