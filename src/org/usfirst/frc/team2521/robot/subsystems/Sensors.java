package org.usfirst.frc.team2521.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import org.usfirst.frc.team2521.robot.OI;
import org.usfirst.frc.team2521.robot.Robot;
import org.usfirst.frc.team2521.robot.RobotMap;
import org.usfirst.frc.team2521.robot.commands.base.DisplaySensors;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Sensors is the subsystem for easily managing all sensor values. In addition, it provides simple
 * methods to show sensor data on the SmartDashboard.
 */
public class Sensors extends Subsystem {
	private AnalogInput frontUltra;
	private AnalogInput rearUltra;
	private AnalogInput leftLidar;
	private AnalogInput rightLidar;

	private NetworkTable table;

	private AHRS ahrs;

	/** Lidar distance equation: `distance = m/lidar^2 + b` */
	private double MED_LIDAR_M = 1.964 * Math.pow(10, 7);
	private double MED_LIDAR_B = -1.045;

	public Sensors() {
		frontUltra = new AnalogInput(RobotMap.FRONT_ULTRA_PORT);
		rearUltra = new AnalogInput(RobotMap.REAR_ULTRA_PORT);

		leftLidar = new AnalogInput(RobotMap.LEFT_LIDAR_PORT);
		rightLidar = new AnalogInput(RobotMap.RIGHT_LIDAR_PORT);

		table = NetworkTable.getTable("Vision");
		
		ahrs = new AHRS(SPI.Port.kMXP);
		ahrs.reset();
		
		setCVThresholds();
	}

	/**
	 * Displays sensor data on the SmartDashboard.
	 */
	public void display() {
		if (Robot.DEBUG) {
			SmartDashboard.putNumber("Rear ultra", getRearUltraInches());
			SmartDashboard.putNumber("CV offset", getCVOffsetX());
			SmartDashboard.putBoolean("Blob found", getBlobFound());
			
			SmartDashboard.putNumber("Switches", OI.getInstance().getAutoMode());
		}
	}

	/**
	 * @return the distance in inches from the front (gear side) ultrasonic sensor
	 */
	public double getFrontUltraInches() {
		return frontUltra.getVoltage() * 1000 / 9.8;
	}

	/**
	 * @return the distance in inches from the rear (shooter side) ultrasonic sensor
	 */
	public double getRearUltraInches() {
		return rearUltra.getVoltage() * 1000 / 9.8;
	}

	/**
	 * @return the raw value from the side lidar
	 */
	private double getLeftLidarRaw() {
		return leftLidar.getValue();
	}

	/**
	 * @return the distance in inches from the side lidar
	 */
	public double getLeftLidarInches() {
		return MED_LIDAR_M / Math.pow(getLeftLidarRaw(), 2) + MED_LIDAR_B;
	}

	/**
	 * @return the raw value from the side lidar
	 */
	private double getRightLidarRaw() {
		return rightLidar.getValue();
	}

	/**
	 * @return the distance in inches from the side lidar
	 */
	public double getRightLidarInches() {
		return MED_LIDAR_M / Math.pow(getRightLidarRaw(), 2) + MED_LIDAR_B;
	}

	/**
	 * Returns the target's offset (in pixels) from the center of the screen on the X-axis. This
	 * value is only updated if getBlobFound() is `true`.
	 *
	 * @return the target's offset (in pixels) from the center of the screen
	 * @see Sensors#getBlobFound()
	 */
	public double getCVOffsetX() {
		return table.getNumber("offset_x", 0.0);
	}

	/**
	 * @return whether a blob is currently being tracked in computer vision
	 * @see Sensors#getCVOffsetX()
	 */
	public boolean getBlobFound() {
		return table.getBoolean("blob_found", false);
	}

	/**
	 * Sets which camera (front or back) vision code on the minnow
	 * board should use.
	 * 
	 * @param cameraType desired camera to use
	 */
	public void setCVCamera(Camera cameraType) {
		table.putBoolean("front_camera", cameraType == Camera.FRONT);
	}

	/**
	 * Sets the minnow board's blob thresholds based on SmartDashboard
	 * preferences.
	 */
	private void setCVThresholds() {
		double frontLowerRed = Preferences.getInstance().getDouble("front_lower_red", 0);
		double frontLowerGreen = Preferences.getInstance().getDouble("front_lower_green", 55);
		double frontLowerBlue = Preferences.getInstance().getDouble("front_lower_blue", 0);
		double frontUpperRed = Preferences.getInstance().getDouble("front_upper_red", 50);
		double frontUpperGreen = Preferences.getInstance().getDouble("front_upper_green", 175);
		double frontUpperBlue = Preferences.getInstance().getDouble("front_upper_blue", 50);
		double rearLowerRed = Preferences.getInstance().getDouble("rear_lower_red", 0);
		double rearLowerGreen = Preferences.getInstance().getDouble("rear_lower_green", 55);
		double rearLowerBlue = Preferences.getInstance().getDouble("rear_lower_blue", 0);
		double rearUpperRed = Preferences.getInstance().getDouble("rear_upper_red", 50);
		double rearUpperGreen = Preferences.getInstance().getDouble("rear_upper_green", 175);
		double rearUpperBlue = Preferences.getInstance().getDouble("rear_upper_blue", 50);
		
		table.putNumber("front_lower_red", frontLowerRed);
		table.putNumber("front_lower_green", frontLowerGreen);
		table.putNumber("front_lower_blue", frontLowerBlue);
		table.putNumber("front_upper_red", frontUpperRed);
		table.putNumber("front_upper_green", frontUpperGreen);
		table.putNumber("front_upper_blue", frontUpperBlue);
		table.putNumber("rear_lower_red", rearLowerRed);
		table.putNumber("rear_lower_green", rearLowerGreen);
		table.putNumber("rear_lower_blue", rearLowerBlue);
		table.putNumber("rear_upper_red", rearUpperRed);
		table.putNumber("rear_upper_green", rearUpperGreen);
		table.putNumber("rear_upper_blue", rearUpperBlue);
	}
	
	/**
	 * @return the Navx's current angle measurement
	 */
	public double getNavxAngle() {
		return ahrs.getYaw();
	}

	/**
	 * Resets the gyro for yaw, setting the navX angle to 0
	 */
	public void resetNavxAngle() {
		ahrs.reset();
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DisplaySensors());
	}

	public enum Camera {
		FRONT, REAR
	}
}

