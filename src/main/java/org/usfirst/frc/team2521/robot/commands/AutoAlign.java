package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoAlign extends PIDCommand {
    // Command to automatically align drivetrain to be straight relative to a wall using lidar

    private static final double P = 0.005;
    private static final double I = 0;
    private static final double D = 0;

    private static final double ERROR_THRESHOLD = 5;
    private static final double LIDAR_MAX_DISTANCE = 1100; // Is a minimum value (since lidar get smaller as distance gets larger)

    private boolean overShot = false; // Is true if the lidar is pointed off too far away (e.g., off from the airship)

    public AutoAlign() {
        super(P, I, D);
        requires(Robot.drivetrain);
    }

    protected void initialize() {
        SmartDashboard.putString("Auto align status", "No errors");
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        if (Robot.sensors.getLeftLidar() < LIDAR_MAX_DISTANCE || Robot.sensors.getRightLidar() < LIDAR_MAX_DISTANCE) {
            SmartDashboard.putString("Auto align status", "Error: Lidar pointing off too far");
            overShot = true;
            return true;
        }
        return Math.abs(Robot.sensors.getLeftLidar() - Robot.sensors.getRightLidar()) < ERROR_THRESHOLD;
    }

    protected void end() {
        if (!overShot) {
            SmartDashboard.putString("Auto align status", "Done");
        }
    }

    protected void interrupted() {
    }

    protected double returnPIDInput() {
        // Return whatever PID input should be used for the loop
        return Robot.sensors.getLeftLidar() - Robot.sensors.getRightLidar();
    }

    protected void usePIDOutput(double output) {
        // Accepts whatever PID output should be used for the loop
        if (Robot.DEBUG) {
            SmartDashboard.putNumber("Auto align output", output);
        }
        Robot.drivetrain.setLeft(output);
        Robot.drivetrain.setRight(-output);
    }
}
