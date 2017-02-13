package org.usfirst.frc.team2521.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class QuickSpin extends CommandGroup {
	public QuickSpin() {
		addSequential(new DriveToUltra(10, false, true));
		addSequential(new TurnToAngle(180));
		//addSequential(new DriveToUltra(0, false, false));
	}
}
