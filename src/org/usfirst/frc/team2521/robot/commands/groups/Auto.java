package org.usfirst.frc.team2521.robot.commands.groups;

import org.usfirst.frc.team2521.robot.OI;
import org.usfirst.frc.team2521.robot.RobotMap;
import org.usfirst.frc.team2521.robot.commands.automation.DriveToGear;
import org.usfirst.frc.team2521.robot.commands.base.RunDrivetrain;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Auto extends CommandGroup {
	private static final int RED_ANGLE = -42;
	private static final int BLUE_ANGLE = 45;
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
				SmartDashboard.putString(KEY_NAME, "Gear then ball");
				addSequential(new GearAutoBoiler());
				addSequential(new TimedCommand(1));
				addSequential(new RunDrivetrain(true), .75);
				addSequential(new AlignShooter());
				break;
			case RobotMap.AutoModes.GEAR_FEEDER:
				SmartDashboard.putString(KEY_NAME, "Gear feeder");
				addSequential(new GearAutoFeeder());
				break;
			case RobotMap.AutoModes.GEAR_BOILER:
				SmartDashboard.putString(KEY_NAME, "Gear boiler");
				addSequential(new GearAutoBoiler());
				break;
			case RobotMap.AutoModes.GEAR_MIDDLE:
				SmartDashboard.putString(KEY_NAME, "Gear middle");
				addSequential(new DriveToGear());
				break;
			case RobotMap.AutoModes.BALLS_STATIONARY:
				SmartDashboard.putString(KEY_NAME, "Balls Stationary");
				addSequential(new RunShooterSubsystems());
				break;
			default:
				SmartDashboard.putString(KEY_NAME, "Cross baseline");
				addSequential(new RunDrivetrain(), 2);
		}
	}

	private static final class GearAutoBoiler extends GearAutoBase {
		@Override
		protected int getAngle(DriverStation.Alliance alliance) {
			return alliance == DriverStation.Alliance.Red ? RED_ANGLE : BLUE_ANGLE;
		}
	}

	private static final class GearAutoFeeder extends GearAutoBase {
		@Override
		protected int getAngle(DriverStation.Alliance alliance) {
			return alliance == DriverStation.Alliance.Red ? BLUE_ANGLE : RED_ANGLE;
		}
	}
}
