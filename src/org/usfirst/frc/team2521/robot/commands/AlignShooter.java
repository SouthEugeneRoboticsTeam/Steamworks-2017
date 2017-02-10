package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AlignShooter extends CommandGroup {

	public AlignShooter() {
		double dist = Robot.sensors.getSideUltraInches();
		dist += 15.75;
		dist -= 19;
		addSequential(new DriveToUltra(dist));
		addSequential(new TurnToAngle(-45));
		addSequential(new DriveToUltra(0));
	}
}
