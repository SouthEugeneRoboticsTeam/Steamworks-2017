package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AlignShooter extends CommandGroup {

	public AlignShooter() {
		System.out.println("Align shooter ran");
		double dist = Robot.sensors.getSideLidarInches();
		SmartDashboard.putNumber("Raw distance",dist);
		addSequential(new DriveToUltra());
		addSequential(new TurnToAngle(-45));
		addSequential(new DriveToUltra(0));
	}
}
