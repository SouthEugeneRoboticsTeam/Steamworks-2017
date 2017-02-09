package org.usfirst.frc.team2521.robot.subsystems;

import com.ctre.CANTalon;

import org.usfirst.frc.team2521.robot.RobotMap;
import org.usfirst.frc.team2521.robot.commands.SpinShooter;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Shooter is the subsystem dedicated to the high-goal shooter. It does not
 * include the feeder.
 */
public class Shooter extends Subsystem {
	private CANTalon flyWheel;
	private Encoder enc;

	public Shooter() {
		flyWheel = new CANTalon(RobotMap.FLY_WHEEL_MOTOR);
		enc = new Encoder(0, 1);
	}

	/**
	 * Sets the shooter flywheel to a certain value.
	 *
	 * @param value  an absolute URL giving the base location of the image
	 */
	public void setMotor(double value) {
		flyWheel.set(-value);
	}

	/**
	 * Returns the encoder velocity of the Shooter flywheel.
	 *
	 * @return the current encoder velocity
	 */
	public double getEncVelocity() {
		return enc.getRate();
	}

	@Override
	public void initDefaultCommand() {
	}
}
