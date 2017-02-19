package org.usfirst.frc.team2521.robot.commands.automation;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Starting from any point on the wall next to the boiler, this command drives to a specific point
 * on the boiler wall from where we can shoot accurately.
 */
public class DriveToBoiler extends CommandGroup {
	public DriveToBoiler() {
		addSequential(new DriveToUltra(-1, true) {
			/** How far from the right side of the boiler the robot's center */
			private static final double X_SETPOINT = 21;
			private static final double ROBOT_HALF_WIDTH = 15.75;
			/** How far the side ultrasonic is from front of the robot */
			private static final double SIDE_LIDAR_OFFSET = 19.5;
			private static final double ROBOT_LENGTH = 26;

			@Override
			protected void initialize() {
				setpoint = Robot.sensors.getSideLidarInches() + X_SETPOINT * Math.sqrt(2) + ROBOT_HALF_WIDTH
						- SIDE_LIDAR_OFFSET - 0.5 * ROBOT_LENGTH;
			}
		});
		addSequential(new DriveToAngle(-45));
		addSequential(new DriveToUltra(0, true));
	}
}
