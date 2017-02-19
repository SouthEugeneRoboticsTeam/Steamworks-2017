package org.usfirst.frc.team2521.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import org.usfirst.frc.team2521.robot.OI;
import org.usfirst.frc.team2521.robot.RobotMap;
import org.usfirst.frc.team2521.robot.commands.base.TeleopClimber;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Climber is the subsystem dedicated to the robot's rope climber.
 */
public class Climber extends Subsystem {
	private CANTalon master;
	private CANTalon slave;

	public Climber() {
		master = new CANTalon(RobotMap.CLIMBER_WHEEL_MASTER_MOTOR);
		slave = new CANTalon(RobotMap.CLIMBER_WHEEL_SLAVE_MOTOR);
	}

	/**
	 * Enables operation of the climber.
	 */
	public void teleoperatedClimb() {
		double speed = -OI.getInstance().getSecondaryStick().getY();

		master.changeControlMode(TalonControlMode.PercentVbus);
		slave.changeControlMode(TalonControlMode.Follower);
		master.set(speed);
		slave.set(RobotMap.CLIMBER_WHEEL_MASTER_MOTOR);
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new TeleopClimber());
	}
}
