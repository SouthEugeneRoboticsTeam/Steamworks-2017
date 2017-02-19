package org.usfirst.frc.team2521.robot.commands.automation;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This command backs away from the intake, spins the robot 180 degrees,
 * and drives back.
 */
public class Spintake extends CommandGroup {
	public Spintake() {
		addSequential(new RearDriveToUltra(10));
		addSequential(new DriveToAngle(180));
		addSequential(new RearDriveToUltra(0));
	}

	private static class RearDriveToUltra extends DriveToUltra {
		public RearDriveToUltra(double setpoint) {
			super(setpoint, false);
		}

		@Override
		protected void initialize() {
			useRearUltra = Robot.sensors.getFrontUltraInches() > Robot.sensors.getRearUltraInches();
		}
	}
}
