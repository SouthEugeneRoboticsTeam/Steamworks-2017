package org.usfirst.frc.team2521.robot.commands.groups;

import org.usfirst.frc.team2521.robot.commands.automation.DriveToAngle;
import org.usfirst.frc.team2521.robot.commands.automation.DriveToGear;
import org.usfirst.frc.team2521.robot.commands.base.RunDrivetrain;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public abstract class GearAutoBase extends CommandGroup {
	public GearAutoBase() {
		addSequential(new RunDrivetrain(), .75);
		addSequential(new TimedCommand(0.25));
		addSequential(new DriveToAngle(getAngle(DriverStation.getInstance().getAlliance())));
		addSequential(new TimedCommand(0.75));
		addSequential(new DriveToGear());
		addSequential(new RunDrivetrain(), .25);
	}

	protected abstract int getAngle(DriverStation.Alliance alliance);
}
