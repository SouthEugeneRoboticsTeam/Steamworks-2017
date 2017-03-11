package org.usfirst.frc.team2521.robot.subsystems;

import org.usfirst.frc.team2521.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Climber is the subsystem dedicated to the robot's rope climber.
 */
public class Climber extends Subsystem {
	private CANTalon master;

	private static final double CLIMBER_SPEED = 1;
	
	public Climber() {
		master = new CANTalon(RobotMap.CLIMBER_MOTOR);
		
		master.changeControlMode(TalonControlMode.PercentVbus);
		master.enableBrakeMode(true);
	}

	/**
	 * Runs the climber at a constant speed.
	 */
	public void runClimber() {
		master.set(CLIMBER_SPEED);
	}
	
	/**
	 * Stops the climber.
	 */
	public void stopClimber() {
		master.set(0);
	}

	@Override
	public void initDefaultCommand() {}
}
