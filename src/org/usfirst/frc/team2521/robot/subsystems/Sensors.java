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

	/**
	 * Displays sensor data on the SmartDashboard.
	 */
	public void display() {
		if (Robot.DEBUG) {
			SmartDashboard.putNumber("NT x offset", getCVOffsetX());
			SmartDashboard.putNumber("Navx angle", getNavxAngle());
			SmartDashboard.putBoolean("Blob found?", getBlobFound());
			
			SmartDashboard.putNumber("Left enc val", Robot.drivetrain.getLeftEnc());
			SmartDashboard.putNumber("Right enc val", Robot.drivetrain.getRightEnc());
		}
	}

	/**
	 * Returns the average value of the left lidar.
	 * 
	 * @return the average value of the left lidar
	 * @see Sensors#getRightLidar() getRightLidar
	 * @see Sensors#getLeftLidarInches() getLeftLidarInches
	 * @see Sensors#getRightLidarInches() getRightLidarInches
	 */
	public double getLeftLidar() {
		return leftLidar.getAverageValue();
	}

	/**
	 * Returns the average value of the right lidar.
	 * 
	 * @return the average value of the right lidar
	 * @see Sensors#getLeftLidar()
	 * @see Sensors#getLeftLidarInches()
	 * @see Sensors#getRightLidarInches()
	 */
	public double getRightLidar() {
		return rightLidar.getAverageValue();
	}

	/**
	 * Returns the value (in inches) of the left lidar.
	 * 
	 * @return the value (in inches) of the left lidar
	 * @see Sensors#getLeftLidar()
	 * @see Sensors#getRightLidar()
	 * @see Sensors#getRightLidarInches()
	 */
	public double getLeftLidarInches() {
		return MED_LIDAR_M / Math.pow(getLeftLidar(), 2) + MED_LIDAR_B;
	}

	/**
	 * Returns the value (in inches) of the right lidar.
	 * 
	 * @return the value (in inches) of the right lidar
	 * @see Sensors#getLeftLidar()
	 * @see Sensors#getRightLidar()
	 * @see Sensors#getLeftLidarInches()
	 */
	public double getRightLidarInches() {
		return MED_LIDAR_M / Math.pow(getRightLidar(), 2) + MED_LIDAR_B;
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

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DisplaySensors());
	}
}

