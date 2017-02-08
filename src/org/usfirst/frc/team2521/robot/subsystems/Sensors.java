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

