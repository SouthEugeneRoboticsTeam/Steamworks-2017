package org.usfirst.frc.team2521.robot.subsystems;

import com.ctre.CANTalon;

import org.usfirst.frc.team2521.robot.RobotMap;
import org.usfirst.frc.team2521.robot.commands.SpinShooter;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Feeder extends Subsystem {
	private CANTalon feeder;

	public Feeder() {
		feeder = new CANTalon(RobotMap.FEEDER_MOTOR);
	}

	public void setMotor(double value) {
		feeder.set(value);
	}

	@Override
	public void initDefaultCommand() {
		//setDefaultCommand(new SpinShooter());
	}
}
