package org.usfirst.frc.team2521.robot.commands.groups;

import org.usfirst.frc.team2521.robot.OI;
import org.usfirst.frc.team2521.robot.Robot;
import org.usfirst.frc.team2521.robot.RobotMap;
import org.usfirst.frc.team2521.robot.commands.automation.DriveToGear;
import org.usfirst.frc.team2521.robot.commands.base.RunDrivetrain;
import org.usfirst.frc.team2521.robot.subsystems.Sensors;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class Auto extends CommandGroup {
	public Auto() {
		switch (OI.getInstance().getAutoMode()) {
			case RobotMap.AutoModes.NOTHING:
				break;
			case RobotMap.AutoModes.CROSS_BASE_LINE:
				addSequential(new RunDrivetrain(), 2);
				break;
			case RobotMap.AutoModes.BALLS_ONLY:
				addSequential(new RunDrivetrain(), 1);
				addSequential(new AlignShooter());
				break;
			case RobotMap.AutoModes.BALL_THEN_GEAR:
				Robot.sensors.setCVCamera(Sensors.Camera.FRONT);
				addSequential(new RunDrivetrain(), 1);
				addSequential(new DriveToGear(false));
				addSequential(new RunDrivetrain(), 0.25);
				addSequential(new TimedCommand(1));
				addSequential(new AlignShooter());
				break;
			case RobotMap.AutoModes.GEAR_LEFT:
				addSequential(new RunDrivetrain(), 1);
				addSequential(new DriveToGear(false));
				break;
			case RobotMap.AutoModes.GEAR_MIDDLE:
				addSequential(new DriveToGear(false));
				break;
			case RobotMap.AutoModes.GEAR_RIGHT:
				addSequential(new RunDrivetrain(), 1);
				addSequential(new DriveToGear(true));
				break;
		}
	}
}
