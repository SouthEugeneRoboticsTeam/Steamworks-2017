package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
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
