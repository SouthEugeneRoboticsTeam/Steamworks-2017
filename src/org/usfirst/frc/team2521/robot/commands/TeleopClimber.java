package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TeleopClimber extends Command {
    public TeleopClimber() {
        requires(Robot.climber);
    }

    protected void execute() {
    	Robot.climber.teleoperatedClimb();
    }

    protected boolean isFinished() {
        return false;
    }
}
