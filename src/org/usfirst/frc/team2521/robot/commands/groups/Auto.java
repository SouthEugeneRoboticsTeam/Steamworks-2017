package org.usfirst.frc.team2521.robot.commands.groups;

import org.usfirst.frc.team2521.robot.OI;
import org.usfirst.frc.team2521.robot.OI.AutoModes;
import org.usfirst.frc.team2521.robot.Robot;
import org.usfirst.frc.team2521.robot.commands.automation.DriveToGear;
import org.usfirst.frc.team2521.robot.commands.base.RunDrivetrain;
import org.usfirst.frc.team2521.robot.subsystems.Sensors;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class Auto extends CommandGroup {
	public Auto() {
		switch (OI.getInstance().getAutoMode()) {
			case OI.AutoModes.nothing: 
				break;
			case OI.AutoModes.crossBaseLine:
				addSequential(new RunDrivetrain(true), 2);
				break;
			case OI.AutoModes.ballsOnly:
				addSequential(new RunDrivetrain(true), 1);
				addSequential(new AlignShooter());
				break;
			case OI.AutoModes.ballThenGear:
				Robot.sensors.setCVCamera(Sensors.Camera.FRONT);
				addSequential(new RunDrivetrain(true), 1);
				addSequential(new DriveToGear(false));
				addSequential(new RunDrivetrain(true), 0.25);
				addSequential(new TimedCommand(1));
				addSequential(new AlignShooter());
				break;
			case OI.AutoModes.gearLeft:
				addSequential(new RunDrivetrain(true), 1);
				addSequential(new DriveToGear(false));
				break;
			case OI.AutoModes.gearMiddle:
				addSequential(new DriveToGear(false));
				break;
			case OI.AutoModes.gearRight:
				addSequential(new RunDrivetrain(true), 1);
				addSequential(new DriveToGear(true));
				break;
		}
	}
}
