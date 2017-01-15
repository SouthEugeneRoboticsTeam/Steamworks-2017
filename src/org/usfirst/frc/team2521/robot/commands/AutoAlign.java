package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;
import org.usfirst.frc.team2521.robot.subsystems.Sensors;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoAlign extends PIDCommand {
	// Command to automatically align drivetrain to be straight relative to a wall using lidar
	
	private static final double P = 0.005;
	private static final double I = 0;
	private static final double D = 0;

    public AutoAlign() {
    	super(P, I, D);
    	requires(Robot.drivetrain);
    }

    protected void initialize() {}

    protected void execute() {
    	if(Robot.DEBUG) {
    		SmartDashboard.putNumber("Setpoint", getSetpoint());
    	}
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {}

    protected void interrupted() {}
    
    protected double returnPIDInput() {
    	// Return whatever PID input should be used for the loop
    	return Robot.sensors.getLeftLidar()-Robot.sensors.getRightLidar();
    }
    
    protected void usePIDOutput(double output) {
    	// Accepts whatever PID output should be used for the loop
    	if (Robot.DEBUG) {
    		SmartDashboard.putNumber("Output", output);
    	}
    	Robot.drivetrain.setLeft(output);
    	Robot.drivetrain.setRight(-output);
    }
}
