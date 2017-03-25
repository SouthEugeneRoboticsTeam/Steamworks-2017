package org.usfirst.frc.team2521.robot.subsystems;

import org.usfirst.frc.team2521.robot.commands.automation.camera.CameraLooper;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Camera extends Subsystem {
	private boolean running;

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new CameraLooper());
	}
}
