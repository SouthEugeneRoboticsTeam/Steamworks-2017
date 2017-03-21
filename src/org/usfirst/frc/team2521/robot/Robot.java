package org.usfirst.frc.team2521.robot;

import org.usfirst.frc.team2521.robot.commands.automation.camera.CameraLooper;
import org.usfirst.frc.team2521.robot.commands.groups.Auto;
import org.usfirst.frc.team2521.robot.subsystems.Agitator;
import org.usfirst.frc.team2521.robot.subsystems.Climber;
import org.usfirst.frc.team2521.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2521.robot.subsystems.Feeder;
import org.usfirst.frc.team2521.robot.subsystems.Sensors;
import org.usfirst.frc.team2521.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import static org.usfirst.frc.team2521.robot.subsystems.Sensors.Camera;

/**
 * This is the main robot class which calls various methods depending on the current game stage.
 */
public class Robot extends IterativeRobot {
	public static final boolean DEBUG = true;

	public static Drivetrain drivetrain;
	public static Sensors sensors;
	public static Shooter shooter;
	public static Feeder feeder;
	public static Climber climber;
	public static Agitator agitator;

	private Command auto;

	@Override
	public void robotInit() {
		drivetrain = new Drivetrain();
		sensors = new Sensors();
		climber = new Climber();
		shooter = new Shooter();
		feeder = new Feeder();
		agitator = new Agitator();

		runCameraLooper();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		sensors.setCVCamera(Camera.Type.FRONT);

		runCameraLooper();
		auto = new Auto();
		auto.start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		if (auto != null) auto.cancel();
		sensors.setCVCamera(Camera.Type.REAR);

		runCameraLooper();
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}

	private void runCameraLooper() {
		new CameraLooper().start();
	}
}
