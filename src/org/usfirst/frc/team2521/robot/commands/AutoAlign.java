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
	
	private static final double errorThreshold = 5;
	private static final double lidarMaxDistance = 1100; // Is a minimum value (since lidar get smaller as distance gets larger)
	
	private boolean overShot = false; // Is true if the lidar is pointed off too far away (e.g., off from the airship)

    public AutoAlign() {
    	super(P, I, D);
    	requires(Robot.drivetrain);
    }

    protected void initialize() {
    	SmartDashboard.putString("Auto align status", "No errors");
    }

    protected void execute() {
    	if(Robot.DEBUG) {
    		SmartDashboard.putNumber("Setpoint", getSetpoint());
    		SmartDashboard.putNumber("Error", super.getPIDController().getError());
    	}
    }

    protected boolean isFinished() {
        if (Robot.DEBUG) {
        	SmartDashboard.putNumber("Error threshold error", Math.abs(Robot.sensors.getLeftLidar()-Robot.sensors.getRightLidar()));
        }
        if (Robot.sensors.getLeftLidar() < lidarMaxDistance || Robot.sensors.getRightLidar() < lidarMaxDistance) {
        	SmartDashboard.putString("Auto align status", "Error: Lidar pointing off too far");
        	overShot = true;
        	return true;
        }
    	return Math.abs(Robot.sensors.getLeftLidar()-Robot.sensors.getRightLidar()) < errorThreshold;
    }

    protected void end() {
    	if (!overShot) {
    		SmartDashboard.putString("Auto align status", "Done");
    	}
    }

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
