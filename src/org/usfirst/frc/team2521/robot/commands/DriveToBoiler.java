package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveToBoiler extends CommandGroup {
	public DriveToBoiler() {
		addSequential(new DriveToUltra(-1, true) {
			/** How far from the right side of the boiler the robot's center */
			private static final double X_SETPOINT = 21;
			private static final double ROBOT_HALF_WIDTH = 15.75;
			/** How far the side ultrasonic is from front of the robot */
			private static final double SIDE_ULTRA_OFFSET = 19.5;
			private static final double ROBOT_LENGTH = 26;

			@Override
			protected void initialize() {
				setpoint = Robot.sensors.getSideLidarInches() + X_SETPOINT * Math.sqrt(2) + ROBOT_HALF_WIDTH
						- SIDE_ULTRA_OFFSET - 0.5 * ROBOT_LENGTH;
			}
		});
		addSequential(new DriveToAngle(-45));
		addSequential(new DriveToUltra(0, true));
	}
}
