package org.usfirst.frc.team2521.robot;

<<<<<<< e5db6ff3ae0ece47ec16f147b92a2e06a27ae1b7
import org.usfirst.frc.team2521.robot.commands.DriveToGear;
import org.usfirst.frc.team2521.robot.subsystems.Climber;
=======
>>>>>>> Move drive to gear to a button
import org.usfirst.frc.team2521.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2521.robot.subsystems.Sensors;
import org.usfirst.frc.team2521.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Robot extends IterativeRobot {
	public static final boolean DEBUG = false;

	public static Drivetrain drivetrain;
	public static Sensors sensors;
	public static Shooter shooter;
	public static Climber climber;

	public Command auto;

	@Override
	public void robotInit() {
		drivetrain = new Drivetrain();
		sensors = new Sensors();
		climber = new Climber();

<<<<<<< e5db6ff3ae0ece47ec16f147b92a2e06a27ae1b7
		auto = new DriveToGear();
=======
		oi = OI.getInstance();
>>>>>>> Move drive to gear to a button
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		auto.start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
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
