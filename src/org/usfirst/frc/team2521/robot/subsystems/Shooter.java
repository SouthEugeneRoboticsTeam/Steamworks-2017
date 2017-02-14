package org.usfirst.frc.team2521.robot.subsystems;

import com.ctre.CANTalon;

import org.usfirst.frc.team2521.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Shooter is the subsystem dedicated to the high-goal shooter. It does not include the feeder.
 */
public class Shooter extends Subsystem {
	private CANTalon shooter;

	public Shooter() {
		shooter = new CANTalon(RobotMap.SHOOTER_MOTOR);
	}

	/**
	 * Sets the shooter flywheel to a certain value.
	 *
	 * @param value the speed of the motor (between -1 and 1)
	 */
	public void setMotor(double value) {
		shooter.set(value);
	}

	/**
	 * @return the current encoder velocity of the Shooter flywheel
	 */
	public double getEncVelocity() {
		return shooter.getEncVelocity();
	}

	@Override
	public void initDefaultCommand() {}
}
