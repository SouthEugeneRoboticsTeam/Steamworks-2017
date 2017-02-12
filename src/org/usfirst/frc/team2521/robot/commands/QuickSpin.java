package org.usfirst.frc.team2521.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class QuickSpin extends CommandGroup {
	public QuickSpin() {
		addSequential(new DriveToEncoder(500));
		addSequential(new TurnToAngle(180));
		addSequential(new DriveToEncoder(500));
	}
}
