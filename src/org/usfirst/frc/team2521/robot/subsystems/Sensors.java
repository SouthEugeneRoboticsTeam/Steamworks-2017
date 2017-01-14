package org.usfirst.frc.team2521.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2521.robot.commands.DisplaySensors;

public class Sensors extends Subsystem {

	public void display() {}

    public void initDefaultCommand() {
        setDefaultCommand(new DisplaySensors());
    }
}

