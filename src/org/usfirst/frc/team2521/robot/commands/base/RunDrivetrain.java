package org.usfirst.frc.team2521.robot.commands.base;

import org.usfirst.frc.team2521.robot.Robot;
import org.usfirst.frc.team2521.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RunDrivetrain extends Command {
	public RunDrivetrain() {
		requires(Robot.drivetrain);
	}

	protected void execute() {
		if (Robot.DEBUG) {
			SmartDashboard.putString("Auto place", "RunDrivetrain");
		}
		Robot.drivetrain.setLeft(Drivetrain.SLOW_SPEED);
		Robot.drivetrain.setRight(Drivetrain.SLOW_SPEED);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
