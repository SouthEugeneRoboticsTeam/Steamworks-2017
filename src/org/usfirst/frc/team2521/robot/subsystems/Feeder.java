package org.usfirst.frc.team2521.robot.subsystems;

import org.usfirst.frc.team2521.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Feeder extends Subsystem {
	private CANTalon feeder;

	public Feeder() {
		feeder = new CANTalon(RobotMap.FEEDER_MOTOR);
	}

	public void setMotor(double value) {
		feeder.set(-value);
		
		System.out.println(feeder.getEncVelocity());
	}

	@Override
	public void initDefaultCommand() {
	}
}
