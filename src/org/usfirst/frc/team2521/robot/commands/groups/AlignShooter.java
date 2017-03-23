package org.usfirst.frc.team2521.robot.commands.groups;

import org.usfirst.frc.team2521.robot.commands.automation.DriveToBoiler;
import org.usfirst.frc.team2521.robot.commands.automation.TurnToBoiler;
import org.usfirst.frc.team2521.robot.commands.base.RunShooter;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 * This command turns until the boiler is centered for the camera,
 * drives to a specified distance, and then runs the shooter.
 */
public class AlignShooter extends CommandGroup {
	public AlignShooter() {
		addParallel(new RunShooter());
		addSequential(new TurnToBoiler());
		addSequential(new DriveToBoiler());
		addParallel(new TurnToBoiler() {
			@Override
			protected boolean isFinished() {
				return false;
			}
		});
		addSequential(new TimedCommand(1));
		addParallel(new RunShooterSubsystems());
	}
}
