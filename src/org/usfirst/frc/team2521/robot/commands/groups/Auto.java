package org.usfirst.frc.team2521.robot.commands.groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class Auto extends CommandGroup {
	public Auto() {
		addSequential(new DeliverGear());
		addSequential(new TimedCommand(2));
		addSequential(new AlignShooter());
	}
}
