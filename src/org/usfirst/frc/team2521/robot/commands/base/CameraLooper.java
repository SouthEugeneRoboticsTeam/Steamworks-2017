package org.usfirst.frc.team2521.robot.commands.base;

import org.usfirst.frc.team2521.robot.vision.Looper;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CameraLooper extends Command {
	public CameraLooper() {
		requires(Robot.camera);
	}

	@Override
	protected void execute() {
		if (Robot.camera.isRunning()) Looper.getInstance().loop();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
