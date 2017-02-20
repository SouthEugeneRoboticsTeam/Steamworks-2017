package org.usfirst.frc.team2521.robot.commands.groups;

import org.usfirst.frc.team2521.robot.commands.automation.DriveToUltra;
import org.usfirst.frc.team2521.robot.commands.automation.TurnToBoiler;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 * This command turns until the boiler is centered for the camera,
 * drives to a specified distance, and then runs the shooter.
 *
 */
public class AlignShooter extends CommandGroup {
	public AlignShooter() {
		addSequential(new TurnToBoiler());
		addSequential(new DriveToUltra(35.25, true));
		//addSequential(new RunShooterSubsystems());
	}
}
