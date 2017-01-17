package org.usfirst.frc.team2521.robot.subsystems;

import org.usfirst.frc.team2521.robot.OI;
import org.usfirst.frc.team2521.robot.RobotMap;
import org.usfirst.frc.team2521.robot.commands.TeleopClimber;
import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {
	private CANTalon wheel;

	public Climber() {
		wheel = new CANTalon(RobotMap.CLIMBER_WHEEL_MOTER);
	}
	
	public void teleoperatedClimb() {
		double gunner = OI.getInstance().getGunnerStick().getY();
		
		wheel.changeControlMode(TalonControlMode.PercentVbus);
		wheel.set(gunner);
	}
	
    public void initDefaultCommand() {
        setDefaultCommand(new TeleopClimber());
    }
}

