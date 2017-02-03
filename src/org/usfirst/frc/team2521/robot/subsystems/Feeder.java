package org.usfirst.frc.team2521.robot.subsystems;
import edu.wpi.first.wpilibj.command.Subsystem;


import org.usfirst.frc.team2521.robot.OI;
import org.usfirst.frc.team2521.robot.RobotMap;

import org.usfirst.frc.team2521.robot.commands.TeleopFeeder;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;


public class Feeder extends Subsystem {

	private CANTalon wheel ;
	
		public Feeder(){
			wheel = new CANTalon(RobotMap.FEEDER_WHEEL_MOTOR);
		}
			
		
		public void feederWheelSpin() {
			double speed = OI.getInstance().getRightStick().getY();
			
			wheel.changeControlMode(TalonControlMode.PercentVbus);
			wheel.set(speed);

		}
		

    public void initDefaultCommand() {
    	setDefaultCommand(new TeleopFeeder());
    }
}

