package org.usfirst.frc.team2521.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import org.usfirst.frc.team2521.robot.Robot;
import org.usfirst.frc.team2521.robot.RobotMap;
import org.usfirst.frc.team2521.robot.commands.base.DisplaySensors;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Sensors is the subsystem for easily managing all sensor values. In addition, it provides simple
 * methods to show sensor data on the SmartDashboard.
 */
public class Sensors extends Subsystem {
	private AnalogInput rearUltra;

	private NetworkTable table;

	private AHRS ahrs;

	public Sensors() {
		rearUltra = new AnalogInput(RobotMap.REAR_ULTRA_PORT);

		table = NetworkTable.getTable("Vision");

		ahrs = new AHRS(SPI.Port.kMXP);
		ahrs.reset();
	}

	/**
	 * Displays sensor data on the SmartDashboard.
	 */
	public void display() {
		SmartDashboard.putNumber("Rear ultra distance", getRearUltraInches());
		SmartDashboard.putBoolean("Blob found", hasFoundBlob());

		if (Robot.DEBUG) {
			SmartDashboard.putNumber("CV offset", getCVOffsetX());
		}
	}

	/**
	 * @return the distance in inches from the rear (shooter side) ultrasonic sensor
	 */
	public double getRearUltraInches() {
		return rearUltra.getVoltage() * 1000 / 9.8;
	}

	/**
	 * Returns the target's offset (in pixels) from the center of the screen on the X-axis. This
	 * value is only updated if hasFoundBlob() is `true`.
	 *
	 * @return the target's offset (in pixels) from the center of the screen
	 * @see Sensors#hasFoundBlob()
	 */
	public double getCVOffsetX() {
		return table.getNumber("offset_x", 0.0);
	}

	/**
	 * @return whether a blob is currently being tracked in computer vision
	 * @see Sensors#getCVOffsetX()
	 */
	public boolean hasFoundBlob() {
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
	 * @return the Navx's current angle measurement
	 */
	public double getNavxAngle() {
		return ahrs.getYaw();
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DisplaySensors());
	}

	public enum Camera {
		FRONT, REAR
	}
}
