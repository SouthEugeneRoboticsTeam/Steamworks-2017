package org.usfirst.frc.team2521.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AlignShooter extends CommandGroup {
	public AlignShooter() {
		addSequential(new AlignShooterDrive());
		addSequential(new TurnToAngle(-45));
		addSequential(new DriveToUltra(0, true));
	}
}
