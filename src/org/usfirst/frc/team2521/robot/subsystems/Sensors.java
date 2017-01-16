package org.usfirst.frc.team2521.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogInput;

import org.usfirst.frc.team2521.robot.Robot;
import org.usfirst.frc.team2521.robot.RobotMap;
import org.usfirst.frc.team2521.robot.commands.DisplaySensors;

public class Sensors extends Subsystem {
	private AnalogInput leftLidar;
	private AnalogInput rightLidar;
	
	public Sensors() {
		leftLidar = new AnalogInput(RobotMap.LEFT_LIDAR_PORT);
		rightLidar = new AnalogInput(RobotMap.RIGHT_LIDAR_PORT);
	}

	public void display() {
		if (Robot.DEBUG) {
			SmartDashboard.putNumber("Left lidar", leftLidar.getValue());
			SmartDashboard.putNumber("Right lidar", rightLidar.getValue());
			SmartDashboard.putNumber("Lidar difference", getLeftLidar()-getRightLidar());
		}
	}
	
	public double getLeftLidar() {
		return leftLidar.getValue();
	}
	
	public double getRightLidar() {
		return rightLidar.getValue();
	}

	public void initDefaultCommand() {
		setDefaultCommand(new DisplaySensors());
	}
}

