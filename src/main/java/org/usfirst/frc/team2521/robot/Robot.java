package org.usfirst.frc.team2521.robot;

import org.usfirst.frc.team2521.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2521.robot.subsystems.Sensors;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Robot extends IterativeRobot {
    public static final boolean DEBUG = false;

    public static Drivetrain drivetrain;
    public static Sensors sensors;

    public static OI oi;

    @Override
    public void robotInit() {
        drivetrain = new Drivetrain();
        sensors = new Sensors();

        oi = OI.getInstance();
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
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void testPeriodic() {
        LiveWindow.run();
    }
}
