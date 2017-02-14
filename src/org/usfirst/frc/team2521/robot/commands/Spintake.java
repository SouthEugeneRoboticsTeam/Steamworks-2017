package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This command backs away from the intake, spins the robot 180 degrees,
 * and drives back.
 */
public class Spintake extends CommandGroup {
	public Spintake() {
		addSequential(new DriveToUltra(10, false) {
			@Override 
			protected void initialize() {
				useRearUltra = Robot.sensors.getFrontUltraInches() > Robot.sensors.getRearUltraInches();
			}
		});
		addSequential(new DriveToAngle(180));
		addSequential(new DriveToUltra(0, false) {
			protected void initialize() {
				useRearUltra = Robot.sensors.getFrontUltraInches() > Robot.sensors.getRearUltraInches();
			}
		});
	}
}
