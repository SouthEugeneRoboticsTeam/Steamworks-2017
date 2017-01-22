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
	
	@Override
	public void robotInit() {
		drivetrain = new Drivetrain();
		shooter = new Shooter();
		oi = new OI();
	}
	
	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}
	
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}
	
	@Override
	public void teleopInit() {
		SmartDashboard.putNumber("Shooter Speed", 0.0);
	}
	
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}
	
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
}
