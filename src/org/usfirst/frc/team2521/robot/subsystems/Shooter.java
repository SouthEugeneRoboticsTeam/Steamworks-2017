package org.usfirst.frc.team2521.robot.subsystems;

import com.ctre.CANTalon;

import org.usfirst.frc.team2521.robot.RobotMap;
import org.usfirst.frc.team2521.robot.commands.PIDShoot;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Shooter is the subsystem dedicated to the high-goal shooter. It does not
 * include the feeder.
 */
public class Shooter extends Subsystem {
	private CANTalon left;
	private CANTalon right;

	public Shooter() {
		left = new CANTalon(RobotMap.LEFT_SHOOT_MOTOR);
		right = new CANTalon(RobotMap.RIGHT_SHOOT_MOTOR);
	}

	/**
	 * Sets the shooter flywheel to a certain value.
	 *
	 * @param value the speed of the motor (between -1 and 1)
	 */
	public void setMotor(double value) {
		left.set(value);
		right.set(-value);
	}

	/**
	 * Returns the encoder velocity of the Shooter flywheel.
	 *
	 * @return the current encoder velocity
	 */
	public double getEncVelocity() {
		return left.getEncVelocity();
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new PIDShoot());
	}
}
