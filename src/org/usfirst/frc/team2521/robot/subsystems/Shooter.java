package org.usfirst.frc.team2521.robot.subsystems;

import com.ctre.CANTalon;

import org.usfirst.frc.team2521.robot.RobotMap;
import org.usfirst.frc.team2521.robot.commands.PIDShoot;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem {

	private CANTalon left;
	private CANTalon right;

	public Shooter() {
		left = new CANTalon(RobotMap.LEFT_SHOOT_MOTOR);
		right = new CANTalon(RobotMap.RIGHT_SHOOT_MOTOR);
	}

	public void setMotor(double value) {
		left.set(value);
		right.set(-value);
	}

	public double getEncVelocity() {
		return left.getEncVelocity();
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new PIDShoot());
	}

}
