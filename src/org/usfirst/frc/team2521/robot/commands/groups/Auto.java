package org.usfirst.frc.team2521.robot.commands.groups;

import org.usfirst.frc.team2521.robot.commands.automation.DriveToGear;
import org.usfirst.frc.team2521.robot.commands.base.RunDrivetrain;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class Auto extends CommandGroup {
	public Auto() {
		addSequential(new RunDrivetrain(), 1.5);
		addSequential(new DriveToGear(false));
		addSequential(new TimedCommand(1));
		addSequential(new AlignShooter());
	}
}
