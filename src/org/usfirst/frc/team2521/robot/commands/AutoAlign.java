package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This command automatically aligns the drivetrain to be straight relative to
 * a wall using lidar.
 */
public class AutoAlign extends PIDCommand {
	private static final double P = 0.005;
	private static final double I = 0;
	private static final double D = 0;

	private static final double ERROR_THRESHOLD = 5;
	private static final double LIDAR_MAX_DISTANCE = 1100;

	private boolean overShot = false;

	public AutoAlign() {
		super(P, I, D);

		requires(Robot.drivetrain);
	}

	@Override
	protected void initialize() {
		SmartDashboard.putString("Auto align status", "No errors");
	}

	@Override
	protected boolean isFinished() {
		// Check if we're too far away
		/*if (Robot.sensors.getLeftLidar() < LIDAR_MAX_DISTANCE || Robot.sensors.getRightLidar() < LIDAR_MAX_DISTANCE) {
			SmartDashboard.putString("Auto align status", "Error: Lidar pointing off too far");
			overShot = true;
			return true;
		}*/

		// Finish the command if we're within our error threshold
		return false; //return Math.abs(Robot.sensors.getLeftLidar() - Robot.sensors.getRightLidar()) < ERROR_THRESHOLD;
	}

	@Override
	protected void end() {
		if (!overShot)
			SmartDashboard.putString("Auto align status", "Done");
	}

	@Override
	protected double returnPIDInput() {
		return 0; //Robot.sensors.getLeftLidar() - Robot.sensors.getRightLidar();
	}

	@Override
	protected void usePIDOutput(double output) {
		if (Robot.DEBUG)
			SmartDashboard.putNumber("Auto align output", output);

		Robot.drivetrain.setLeft(output);
		Robot.drivetrain.setRight(-output);
	}
}
