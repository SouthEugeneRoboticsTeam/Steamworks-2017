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

/**
 * Sensors is the subsystem for easily managing all sensor values. In addition,
 * it provides simple methods to show sensor data on the SmartDashboard.
 */
public class Sensors extends Subsystem {
	private AnalogInput frontUltra;
	private AnalogInput sideUltra;

	private NetworkTable table;
	
	private AHRS ahrs;
	
	// Lidar distance equation: `distance = m/lidar^2 + b`
	private double MED_LIDAR_M = 1.964 * Math.pow(10, 7);
	private double MED_LIDAR_B = -1.045;

	public Sensors() {
		frontUltra = new AnalogInput(RobotMap.FRONT_ULTRA_PORT);
		sideUltra = new AnalogInput(RobotMap.SIDE_ULTRA_PORT);

		table = NetworkTable.getTable("Vision");
		
		ahrs = new AHRS(SPI.Port.kMXP);
		ahrs.reset();
	}

	/**
	 * Displays sensor data on the SmartDashboard.
	 */
	public void display() {
		if (Robot.DEBUG) {
			SmartDashboard.putNumber("Side raw", getSideUltraRaw());
			SmartDashboard.putNumber("Front raw", getFrontUltraRaw());
			SmartDashboard.putNumber("Side inches", getSideUltraInches());
			SmartDashboard.putNumber("Front inches", getFrontUltraInches());
			
			SmartDashboard.putNumber("Angle", getNavxAngle());
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

	/**
	 * Returns the target's offset (in pixels) from the center of the screen on
	 * the X-axis. This value is only updated if getBlobFound() is `true`.
	 * 
	 * @return the target's offset (in pixels) from the center of the screen
	 * @see Sensors#getBlobFound()
	 */
	public double getCVOffsetX() {
		return table.getNumber("offset_x", 0.0);
	}

	/**
	 * Returns whether a blob is currently being tracked in computer vision.
	 * 
	 * @return whether a blob is currently being tracked in computer vision
	 * @see Sensors#getCVOffsetX()
	 */
	public boolean getBlobFound() {
		return table.getBoolean("blob_found", false);
	}

	/**
	 * Returns the Navx's current angle measurement.
	 * 
	 * @return the Navx's current angle measurement
	 */
	public double getNavxAngle() {
		return ahrs.getAngle();
	}
	
	public void resetNavxAngle() {
		ahrs.reset();
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DisplaySensors());
	}
}

