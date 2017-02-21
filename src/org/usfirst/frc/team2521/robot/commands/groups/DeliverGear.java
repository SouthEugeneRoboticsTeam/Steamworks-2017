package org.usfirst.frc.team2521.robot.commands.groups;

import org.usfirst.frc.team2521.robot.commands.automation.DriveToGear;
import org.usfirst.frc.team2521.robot.commands.base.RunDrivetrain;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DeliverGear extends CommandGroup {
	public DeliverGear() {
		addSequential(new DriveToGear(false));
		addSequential(new RunDrivetrain(), 1);
	}
}
