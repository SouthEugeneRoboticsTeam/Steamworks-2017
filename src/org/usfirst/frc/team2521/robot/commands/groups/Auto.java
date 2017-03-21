package org.usfirst.frc.team2521.robot.commands.groups;

import org.usfirst.frc.team2521.robot.OI;
import org.usfirst.frc.team2521.robot.Robot;
import org.usfirst.frc.team2521.robot.RobotMap;
import org.usfirst.frc.team2521.robot.commands.automation.DriveToAngle;
import org.usfirst.frc.team2521.robot.commands.automation.DriveToGear;
import org.usfirst.frc.team2521.robot.commands.base.RunDrivetrain;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static org.usfirst.frc.team2521.robot.subsystems.Sensors.Camera;

public class Auto extends CommandGroup {
	private static final String KEY_NAME = "Auto mode";

	public Auto() {
		switch (OI.getInstance().getAutoMode()) {
			case RobotMap.AutoModes.NOTHING:
				SmartDashboard.putString(KEY_NAME, "Nothing");
				break;
			case RobotMap.AutoModes.CROSS_BASELINE:
				SmartDashboard.putString(KEY_NAME, "Cross baseline");
				addSequential(new RunDrivetrain(), 2);
				break;
			case RobotMap.AutoModes.BALLS_ONLY:
				SmartDashboard.putString(KEY_NAME, "Balls only");
				addSequential(new RunDrivetrain(), 1);
				addSequential(new AlignShooter());
				break;
			case RobotMap.AutoModes.GEAR_THEN_BALL:
				SmartDashboard.putString(KEY_NAME, "Ball then gear");
				Robot.sensors.setCVCamera(Camera.Type.FRONT);
				addSequential(new RunDrivetrain(), 2);
				addSequential(new DriveToGear());
				addSequential(new RunDrivetrain(), 0.25);
				addSequential(new TimedCommand(1));
				addSequential(new AlignShooter());
				break;
			case RobotMap.AutoModes.GEAR_LEFT:
				SmartDashboard.putString(KEY_NAME, "Gear left");
				addSequential(new RunDrivetrain(), .75);
				addSequential(new TimedCommand(0.25));
				addSequential(new DriveToAngle(60));
				addSequential(new DriveToGear());
				addSequential(new RunDrivetrain(), .1);
				break;
			case RobotMap.AutoModes.GEAR_MIDDLE:
				SmartDashboard.putString(KEY_NAME, "Gear middle");
				addSequential(new DriveToGear());
				break;
			case RobotMap.AutoModes.GEAR_RIGHT:
				SmartDashboard.putString(KEY_NAME, "Gear right");
				addSequential(new RunDrivetrain(), .75);
				addSequential(new TimedCommand(0.25));
				addSequential(new DriveToAngle(-55));
				addSequential(new DriveToGear());
				addSequential(new RunDrivetrain(), .1);
				break;
			default:
				SmartDashboard.putString(KEY_NAME, "Cross baseline");
				addSequential(new RunDrivetrain(), 2);
		}
	}
}
