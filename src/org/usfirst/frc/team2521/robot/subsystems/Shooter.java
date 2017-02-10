package org.usfirst.frc.team2521.robot.subsystems;

import org.usfirst.frc.team2521.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Shooter is the subsystem dedicated to the high-goal shooter. It does not
 * include the feeder.
 */
public class Shooter extends Subsystem {
	private CANTalon flyWheel;

	public Shooter() {
		flyWheel = new CANTalon(RobotMap.FLY_WHEEL_MOTOR);
	}

	/**
	 * Sets the shooter flywheel to a certain value.
	 *
	 * @param value  an absolute URL giving the base location of the image
	 */
	public void setMotor(double value) {
		flyWheel.set(value);
	}

	/**
	 * Returns the encoder velocity of the Shooter flywheel.
	 *
	 * @return the current encoder velocity
	 */
	public double getEncVelocity() {
		return flyWheel.getEncVelocity();
	}

	@Override
	public void initDefaultCommand() {
	}
}
