package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class RunAgitator extends Command {

	public RunAgitator() {
		requires(Robot.agitator);
	}
		
	@Override
	protected void execute() {
		Robot.agitator.setMotor(-1);
	}
	
	@Override
	protected void end() {
		Robot.agitator.setMotor(0);
	}
		
	@Override
	protected boolean isFinished() {
		return false;
	}

}
