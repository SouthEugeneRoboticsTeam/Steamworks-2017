package org.usfirst.frc.team2521.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team2521.robot.commands.automation.camera.CameraLooper;

public class Camera extends Subsystem {
	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new CameraLooper());
	}
}
