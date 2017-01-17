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
	
	private final double CAMERA_PROJ_PLANE_DISTANCE = 216.226;
	/* Distance in pixels to imaginary camera projection plane
	Calculated from camera FOV (61.39) and half image width (380 pixels): (380/2)/tan(61.39/2) */
	private final double LIDAR_WIDTH = 25.6; //in

	public Sensors() {
		leftLidar = new AnalogInput(RobotMap.LEFT_LIDAR_PORT);
		rightLidar = new AnalogInput(RobotMap.RIGHT_LIDAR_PORT);
		leftLidar.setAverageBits(2);
		rightLidar.setAverageBits(2);
		table = NetworkTable.getTable("Vision");
	}

	public void display() {
		if (Robot.DEBUG) {
			SmartDashboard.putNumber("Left lidar", getLeftLidarInches());
			SmartDashboard.putNumber("Right lidar", getRightLidarInches());
			SmartDashboard.putNumber("Lidar difference", getLeftLidar() - getRightLidar());
		}
	}

	public double getLeftLidar() {
		return leftLidar.getAverageValue();
	}

	public double getRightLidar() {
		return rightLidar.getAverageValue();
	}

	public double getLeftLidarInches() {
		return 439583/getLeftLidar()-219.66; // Measured
	}
	
	public double getRightLidarInches() {
		return 439583/getRightLidar()-219.66;
	}
	
	private double getCVOffsetX() {
		return table.getNumber("offset_x", 0.0);
	}
	
	public double getAngleToVisionTarget() {
		// Gets the angle between the vision target and a plane parallel to the wall
		//double alpha = Math.atan(())
		return 0;
	}
	
	public double getAngleToVisionTarg() {
		return Math.atan(getCVOffsetX() / CAMERA_PROJ_PLANE_DISTANCE);
	}
	
	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DisplaySensors());
	}
}

