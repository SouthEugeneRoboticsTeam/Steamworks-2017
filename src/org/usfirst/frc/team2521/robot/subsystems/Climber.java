package org.usfirst.frc.team2521.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import org.usfirst.frc.team2521.robot.OI;
import org.usfirst.frc.team2521.robot.RobotMap;
import org.usfirst.frc.team2521.robot.commands.TeleopClimber;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Climber is the subsystem dedicated to the robot's rope climber.
 */
public class Climber extends Subsystem {
	private CANTalon wheel;

	public Climber() {
		wheel = new CANTalon(RobotMap.CLIMBER_WHEEL_MOTOR);
	}

	/**
	 * Enables operation of the climber.
	 */
	public void teleoperatedClimb() {
		double speed = OI.getInstance().getSecondaryStick().getY();

		wheel.changeControlMode(TalonControlMode.PercentVbus);
		wheel.set(speed);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new TeleopClimber());
	}
}
