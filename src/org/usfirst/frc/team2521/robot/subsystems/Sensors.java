package org.usfirst.frc.team2521.robot.subsystems;

import org.usfirst.frc.team2521.robot.Robot;
import org.usfirst.frc.team2521.robot.RobotMap;
import org.usfirst.frc.team2521.robot.commands.DisplaySensors;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;

public class Sensors extends Subsystem {
	private AnalogInput leftLidar;
	private AnalogInput rightLidar;
	private NetworkTable table;
	private AHRS ahrs; // Navx
	
	private final double CAMERA_PROJ_PLANE_DISTANCE = 216.226;
	/* Distance in pixels to imaginary camera projection plane
	Calculated from camera FOV (61.39) and half image width (380 pixels): (380/2)/tan(61.39/2) */
	private final double LIDAR_WIDTH = 25.6; //in

	public Sensors() {
		leftLidar = new AnalogInput(RobotMap.LEFT_LIDAR_PORT);
		rightLidar = new AnalogInput(RobotMap.RIGHT_LIDAR_PORT);
		leftLidar.setAverageBits(3);
		rightLidar.setAverageBits(3);
		table = NetworkTable.getTable("Vision");
	}

	public void display() {
		if (Robot.DEBUG) {
			SmartDashboard.putNumber("Left lidar", getLeftLidarInches());
			SmartDashboard.putNumber("Right lidar", getRightLidarInches());
			SmartDashboard.putNumber("Left lidar raw", getLeftLidar());
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
		return (226423.53/getLeftLidar()-77.11); // Measured
	}
	
	public double getRightLidarInches() {
		return (226423.53/getRightLidar()-77.11); // Measured
	}
	
	private double getCVOffsetX() {
		return table.getNumber("offset_x", 0.0);
	}
	
	public double getAngleFromCamToVisionTarget() {
		// Gets the angle between the line of sight of the camera and the vision target
		// Beta in whiteboard drawings
		return Math.toDegrees(Math.atan(getCVOffsetX()/CAMERA_PROJ_PLANE_DISTANCE));
	}
	
	public double getAngleFromCamToWallPlane() {
		// Gets the angle between camera's line of sight and a plane parallel to the wall
		// Alpha in whiteboard drawings
		return Math.toDegrees(Math.atan((LIDAR_WIDTH/(getRightLidarInches() - getLeftLidarInches()))));
	}
	
	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DisplaySensors());
	}
}

