package org.usfirst.frc.team2521.robot.subsystems;

import com.ctre.CANTalon;

import org.usfirst.frc.team2521.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Agitator extends Subsystem {
	private CANTalon agitator;

	public Agitator() {
		agitator = new CANTalon(RobotMap.AGITATOR_MOTOR);
	}

	public void setMotor(double value) {
		agitator.set(value);
	}

	@Override
	public void initDefaultCommand() {}
}
