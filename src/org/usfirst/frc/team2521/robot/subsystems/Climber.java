package org.usfirst.frc.team2521.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import org.usfirst.frc.team2521.robot.OI;
import org.usfirst.frc.team2521.robot.RobotMap;
import org.usfirst.frc.team2521.robot.commands.base.RunClimber;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Climber is the subsystem dedicated to the robot's rope climber.
 */
public class Climber extends Subsystem {
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
		master.set(-Math.abs(OI.getInstance().getRightStick().getY()));
		slave.set(RobotMap.CLIMBER_MASTER_MOTOR);
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new RunClimber());
	}
}
