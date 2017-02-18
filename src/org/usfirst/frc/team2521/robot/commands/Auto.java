package org.usfirst.frc.team2521.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Auto extends CommandGroup {
	public Auto() {
		addSequential(new RunDrivetrain(), 2);
		addSequential(new DriveToGear(false));
	}
}
