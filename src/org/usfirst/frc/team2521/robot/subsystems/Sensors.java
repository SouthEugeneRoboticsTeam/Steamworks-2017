package org.usfirst.frc.team2521.robot.subsystems;

import org.usfirst.frc.team2521.robot.Robot;
import org.usfirst.frc.team2521.robot.RobotMap;
import org.usfirst.frc.team2521.robot.commands.DisplaySensors;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;

public class Sensors extends Subsystem {
	private AnalogInput leftLidar;
	private AnalogInput rightLidar;
	private NetworkTable table;
	private AHRS ahrs;
	
	private final double CAMERA_PROJ_PLANE_DISTANCE = 216.226;
	/* Distance in pixels to imaginary camera projection plane
	Calculated from camera FOV (61.39) and half image width (380 pixels): (380/2)/tan(61.39/2) */
	private final double LIDAR_WIDTH = 25.6; //in
	
	// Lidar equation form:  distance = m/lidarValue^2 + b
	private double MED_LIDAR_M = 1.964 * Math.pow(10, 7);
	private double MED_LIDAR_B = -1.045;

	public Sensors() {
		leftLidar = new AnalogInput(RobotMap.LEFT_LIDAR_PORT);
		rightLidar = new AnalogInput(RobotMap.RIGHT_LIDAR_PORT);
		leftLidar.setAverageBits(5);
		rightLidar.setAverageBits(5);
		table = NetworkTable.getTable("Vision");
		ahrs = new AHRS(SPI.Port.kMXP);
		ahrs.reset();
	}

	public void display() {
		if (Robot.DEBUG) {
			System.out.println(getCVOffsetX());
			SmartDashboard.putNumber("NT x offset", getCVOffsetX());
			SmartDashboard.putNumber("Navx angle", getNavxAngle());
			SmartDashboard.putBoolean("Blob found?", getBlobFound());
		}
	}

	public double getLeftLidar() {
		return leftLidar.getAverageValue();
	}

	public double getRightLidar() {
		return rightLidar.getAverageValue();
	}

	public double getLeftLidarInches() {
		return MED_LIDAR_M/Math.pow(getLeftLidar(), 2) + MED_LIDAR_B;
	}
	
	public double getRightLidarInches() {
		return MED_LIDAR_M/Math.pow(getRightLidar(), 2) + MED_LIDAR_B;
	}
	
	public double getCVOffsetX() {
		return table.getNumber("offset_x", 0.0);
	}
	
	public double getAngleFromCamToVisionTarget() {
		// Gets the angle between the line of sight of the camera and the vision target
		// Beta in whiteboard drawings
		return Math.toDegrees(Math.atan(getCVOffsetX() / CAMERA_PROJ_PLANE_DISTANCE));
	}
	
	public boolean getBlobFound() {
		return table.getBoolean("blob_found", false);
	}
	
	public double getNavxAngle() {
		return ahrs.getAngle();
	}
	
	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DisplaySensors());
	}
}

