package org.usfirst.frc.team2521.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2521.robot.RobotMap;

/**
 *
 */
public class Lights extends Subsystem {	
	private DigitalOutput isShooting;
	private DigitalOutput isRedAlliance;
	private DigitalOutput isBlueAlliance;
	
	public Lights(){
		isShooting = new DigitalOutput(RobotMap.IS_SHOOTING_PIN);
		isRedAlliance = new DigitalOutput(RobotMap.IS_RED_ALLIANCE_PIN);
		isBlueAlliance = new DigitalOutput(RobotMap.IS_BLUE_ALLIANCE_PIN);
	}
	
	public void setIsShooting(boolean value){
		isShooting.set(value);
	}
	
	public void setRedAlliance(boolean value){
		isRedAlliance.set(value);
	}
	
	public void setBlueAlliance(boolean value){
		isBlueAlliance.set(value);
	}
	
	@Override
    public void initDefaultCommand() {}
}

