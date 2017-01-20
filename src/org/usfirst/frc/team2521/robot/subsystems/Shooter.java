package org.usfirst.frc.team2521.robot.subsystems;

import org.usfirst.frc.team2521.robot.RobotMap;
import org.usfirst.frc.team2521.robot.commands.SetShooter;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private CANTalon left;
	private CANTalon right;
	public boolean fetch = false;
	
	public Shooter() {
		left = new CANTalon(RobotMap.LEFT_SHOOT_MOTOR);
		right = new CANTalon(RobotMap.RIGHT_SHOOT_MOTOR);
		
		right.changeControlMode(CANTalon.TalonControlMode.Follower);
	}
	
	public void setMotor(double value) {
		//if (fetch == true) {
			left.set(value);
			right.set(RobotMap.LEFT_SHOOT_MOTOR);
		//}
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new SetShooter());
    }
}

