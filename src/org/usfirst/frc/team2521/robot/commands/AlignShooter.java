package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AlignShooter extends CommandGroup {
	private static final double X_SETPOINT = 21;
	private static final double ROBOT_HALF_WIDTH = 15.75;
	private static final double SIDE_ULTRA_OFFSET = 19.5;
	private static final double ROBOT_LENGTH = 26;

	public AlignShooter() {
		double dist = Robot.sensors.getSideUltraInches();
		SmartDashboard.putNumber("Raw distance",dist);
		dist = X_SETPOINT*Math.sqrt(2) + dist + ROBOT_HALF_WIDTH - SIDE_ULTRA_OFFSET - 0.5*ROBOT_LENGTH;
		SmartDashboard.putNumber("Setpoint",dist);
		addSequential(new DriveToUltra(dist));
		addSequential(new TurnToAngle(-45));
		addSequential(new DriveToUltra(0));
	}
}
