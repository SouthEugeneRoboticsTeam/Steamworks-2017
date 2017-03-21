package org.usfirst.frc.team2521.robot.subsystems;

import org.usfirst.frc.team2521.robot.OI;
import org.usfirst.frc.team2521.robot.RobotMap;
import org.usfirst.frc.team2521.robot.commands.base.RunClimber;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Climber is the subsystem dedicated to the robot's rope climber.
 */
public class Climber extends Subsystem {
	private static final double CLIMBER_SPEED = 1;

	private CANTalon master;
	private CANTalon slave;

	public Climber() {
		master = new CANTalon(RobotMap.CLIMBER_MASTER_MOTOR);
		slave = new CANTalon(RobotMap.CLIMBER_SLAVE_MOTOR);

		master.changeControlMode(TalonControlMode.PercentVbus);
		slave.changeControlMode(TalonControlMode.Follower);
	}

	/**
	 * Runs the climber at a constant speed.
	 */
	public void runClimber() {
		master.set(CLIMBER_SPEED);
		slave.set(RobotMap.CLIMBER_MASTER_MOTOR);
	}

	/**
	 * Stops the climber.
	 */
	public void stopClimber() {
		master.set(0);
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new RunClimber());
	}
}
