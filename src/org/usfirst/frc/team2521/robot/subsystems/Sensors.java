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
	private AnalogInput frontUltra;
	private AnalogInput sideUltra;
	private NetworkTable table;
	private AHRS ahrs;

	public Sensors() {
		frontUltra = new AnalogInput(RobotMap.FRONT_ULTRA_PORT);
		sideUltra = new AnalogInput(RobotMap.SIDE_ULTRA_PORT);
		table = NetworkTable.getTable("Vision");
		ahrs = new AHRS(SPI.Port.kMXP);
		ahrs.reset();
	}

	public void display() {
		if (Robot.DEBUG) {
			SmartDashboard.putNumber("Side raw", getSideUltraRaw());
			SmartDashboard.putNumber("Front raw", getFrontUltraRaw());
			SmartDashboard.putNumber("Side inches", getSideUltraInches());
			SmartDashboard.putNumber("Front inches", getFrontUltraInches());
		}
	}
	
	public double getSideUltraRaw() {
		return sideUltra.getVoltage();
	}
	
	public double getSideUltraInches() {
		return sideUltra.getVoltage() * 1000 / 9.8;
	}
	
	public double getFrontUltraRaw() {
		return frontUltra.getVoltage();
	}
	
	public double getFrontUltraInches() {
		return frontUltra.getVoltage() * 1000 / 9.8;
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

