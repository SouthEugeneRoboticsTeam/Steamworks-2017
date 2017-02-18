package org.usfirst.frc.team2521.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RunShooterAndAgitator extends CommandGroup {
	public RunShooterAndAgitator() {
		addParallel(new RunAgitator(true));
		addParallel(new RunShooter());
	}
}
