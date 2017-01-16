package org.usfirst.frc.team2521.robot.subsystems;

import org.usfirst.frc.team2521.robot.Robot;
import org.usfirst.frc.team2521.robot.RobotMap;
import org.usfirst.frc.team2521.robot.commands.DisplaySensors;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Sensors extends Subsystem {
	private AnalogInput leftLidar;
	private AnalogInput rightLidar;
	private NetworkTable table;

	public Sensors() {
		leftLidar = new AnalogInput(RobotMap.LEFT_LIDAR_PORT);
		rightLidar = new AnalogInput(RobotMap.RIGHT_LIDAR_PORT);
		table = NetworkTable.getTable("Vision");
	}

	public void display() {
		if (Robot.DEBUG) {
			SmartDashboard.putNumber("Left lidar", leftLidar.getValue());
			SmartDashboard.putNumber("Right lidar", rightLidar.getValue());
			SmartDashboard.putNumber("Lidar difference", getLeftLidar() - getRightLidar());
		}
	}

	public double getLeftLidar() {
		return leftLidar.getValue();
	}

	public double getRightLidar() {
		return rightLidar.getValue();
	}

	public double getCVOffsetX() {
		return table.getNumber("offset_x", 0.0);
	}
	
	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DisplaySensors());
	}
}

