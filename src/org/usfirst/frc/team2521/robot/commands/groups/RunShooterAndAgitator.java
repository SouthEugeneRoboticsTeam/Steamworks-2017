package org.usfirst.frc.team2521.robot.commands.groups;

import org.usfirst.frc.team2521.robot.commands.base.RunAgitator;
import org.usfirst.frc.team2521.robot.commands.base.RunShooter;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RunShooterAndAgitator extends CommandGroup {
	public RunShooterAndAgitator() {
		addParallel(new RunAgitator(true));
		addParallel(new RunShooter());
	}
}
