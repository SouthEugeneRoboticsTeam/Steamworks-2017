package org.usfirst.frc.team2521.robot.commands;

import edu.wpi.first.wpilibj.Preferences;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetShooter extends Command {
	double lowSpeed = 1000000;
	double highSpeed = 0;
	boolean upToSpeed = false;
	boolean timeMeasured = false;
	int counter = 0;

	
    public SetShooter() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.shooter.setMotor(Preferences.getInstance().getDouble("speed", .45));
    	
    	double vel = Robot.shooter.getEncVelocity();
    	if(vel > 25000) {
    		upToSpeed = true;
    	}
    	if(upToSpeed) {
    		lowSpeed = Math.min(lowSpeed, vel);
    		highSpeed = Math.max(highSpeed, vel);
    	}
    	if (lowSpeed == vel) {
    		SmartDashboard.putNumber("Low speed counter", counter);
    	}
    	if (highSpeed == vel) {
    		SmartDashboard.putNumber("Low speed counter", counter);
    	}
    	
    	SmartDashboard.putNumber("Encoder velocity", vel);
    	SmartDashboard.putNumber("Low speed", lowSpeed);
    	SmartDashboard.putNumber("High speed", highSpeed);
    	SmartDashboard.putNumber("Delta speed", highSpeed - lowSpeed);
    	counter++;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
