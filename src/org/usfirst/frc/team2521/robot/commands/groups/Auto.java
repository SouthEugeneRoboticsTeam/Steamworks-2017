package org.usfirst.frc.team2521.robot.commands.groups;

import org.usfirst.frc.team2521.robot.OI;
import org.usfirst.frc.team2521.robot.Robot;
import org.usfirst.frc.team2521.robot.RobotMap;
import org.usfirst.frc.team2521.robot.commands.automation.DriveToAngle;
import org.usfirst.frc.team2521.robot.commands.automation.DriveToGear;
import org.usfirst.frc.team2521.robot.commands.base.RunDrivetrain;
import org.usfirst.frc.team2521.robot.subsystems.Sensors;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Auto extends CommandGroup {
	public Auto() {
		/*switch (OI.getInstance().getAutoMode()) {
			case RobotMap.AutoModes.NOTHING:
				SmartDashboard.putString("Auto mode", "Nothing");
				break;
			case RobotMap.AutoModes.CROSS_BASELINE:
				SmartDashboard.putString("Auto mode", "Cross baseline");
				addSequential(new RunDrivetrain(), 2);
				break;
			case RobotMap.AutoModes.BALLS_ONLY:
				SmartDashboard.putString("Auto mode", "Balls only");
				addSequential(new RunDrivetrain(), 1);
				addSequential(new AlignShooter());
				break;
			case RobotMap.AutoModes.GEAR_THEN_BALL:
				SmartDashboard.putString("Auto mode", "Ball then gear");
				Robot.sensors.setCVCamera(Sensors.Camera.FRONT);
				addSequential(new RunDrivetrain(), 2);
				addSequential(new DriveToGear());
				addSequential(new RunDrivetrain(), 0.25);
				addSequential(new TimedCommand(1));
				addSequential(new AlignShooter());
				break;
			case RobotMap.AutoModes.GEAR_LEFT:*/
				SmartDashboard.putString("Auto mode", "Gear left");
				addSequential(new RunDrivetrain(), .75);
				addSequential(new TimedCommand(0.25));
				addSequential(new DriveToAngle(25));
				addSequential(new DriveToGear());
				/*break;
			case RobotMap.AutoModes.GEAR_MIDDLE:
				Robot.sensors.setCVCamera(Sensors.Camera.FRONT);
				SmartDashboard.putString("Auto mode", "Gear middle");
				addSequential(new DriveToGear());
				//addSequential(new RunDrivetrain(), 2);
				break;
			case RobotMap.AutoModes.GEAR_RIGHT:
				SmartDashboard.putString("Auto mode", "Gear left");
				addSequential(new RunDrivetrain(), .75);
				addSequential(new TimedCommand(0.25));
				addSequential(new DriveToAngle(25));
				addSequential(new DriveToGear());
					/*break;
			default:
				SmartDashboard.putString("Auto mode", "Cross baseline");
				addSequential(new RunDrivetrain(), 2);
				break;
		}*/
	}
}
