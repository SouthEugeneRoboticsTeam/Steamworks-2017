
package org.usfirst.frc.team2521.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team2521.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2521.robot.subsystems.Shooter;

public class Robot extends IterativeRobot {

	public static Drivetrain drivetrain;
	public static Shooter shooter;
	
	public static OI oi;

	/**
	 * This function is run when the robot is first started up
	 */
	@Override
	public void robotInit() {
		drivetrain = new Drivetrain();
		shooter = new Shooter();
		oi = new OI();
	}

	/**
	 * This function is called when robot becomes disabled
	 */
	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called when the robot starts up in autonomous mode
	 */
	@Override
	public void autonomousInit() {
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called when the robot starts up in teleop mode
	 */
	@Override
	public void teleopInit() {
		SmartDashboard.putNumber("Shooter Speed", 0.5);
		SmartDashboard.putBoolean("Thing is going", false);
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
