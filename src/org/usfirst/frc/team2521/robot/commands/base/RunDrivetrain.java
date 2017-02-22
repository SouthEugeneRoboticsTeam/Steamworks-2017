package org.usfirst.frc.team2521.robot.commands.base;

import org.usfirst.frc.team2521.robot.Robot;
import org.usfirst.frc.team2521.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class RunDrivetrain extends Command {
	boolean forward;
	
	public RunDrivetrain(boolean forward) {
		requires(Robot.drivetrain);
		
		this.forward = forward;
	}

	protected void execute() {
		if (forward) {
			Robot.drivetrain.setLeft(Drivetrain.SLOW_SPEED);
			Robot.drivetrain.setRight(Drivetrain.SLOW_SPEED);
		} else {
			Robot.drivetrain.setLeft(-Drivetrain.SLOW_SPEED);
			Robot.drivetrain.setRight(-Drivetrain.SLOW_SPEED);
		}
		
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
