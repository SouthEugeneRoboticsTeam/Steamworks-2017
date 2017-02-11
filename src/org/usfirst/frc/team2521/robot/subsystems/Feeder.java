package org.usfirst.frc.team2521.robot.subsystems;

import org.usfirst.frc.team2521.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Feeder is the subsystem dedicated to the shooter's feeding system which
 * delivers balls from the indexer to the fly wheel.
 */
public class Feeder extends Subsystem {
	private CANTalon feeder;

	public Feeder() {
		feeder = new CANTalon(RobotMap.FEEDER_MOTOR);
	}

	/**
	 * Sets the feeder wheel to a certain value.
	 *
	 * @param value  the value at which to set the feeder wheel
	 */
	public void setMotor(double value) {
		feeder.set(-value);
	}

	@Override
	public void initDefaultCommand() {}
}
