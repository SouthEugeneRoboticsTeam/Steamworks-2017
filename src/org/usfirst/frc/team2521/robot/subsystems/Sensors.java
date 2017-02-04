package org.usfirst.frc.team2521.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import org.usfirst.frc.team2521.robot.Robot;
import org.usfirst.frc.team2521.robot.RobotMap;
import org.usfirst.frc.team2521.robot.commands.DisplaySensors;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Sensors extends Subsystem {
	private AnalogInput leftLidar;
	private AnalogInput rightLidar;
	
	private NetworkTable table;
	
	private AHRS ahrs;

	// Lidar distance equation: `distance = m/lidar^2 + b`
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
		return MED_LIDAR_M / Math.pow(getLeftLidar(), 2) + MED_LIDAR_B;
	}

	public double getRightLidarInches() {
		return MED_LIDAR_M / Math.pow(getRightLidar(), 2) + MED_LIDAR_B;
	}

	public double getCVOffsetX() {
		return table.getNumber("offset_x", 0.0);
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

