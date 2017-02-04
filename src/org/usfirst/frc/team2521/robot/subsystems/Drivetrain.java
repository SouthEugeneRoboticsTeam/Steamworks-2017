package org.usfirst.frc.team2521.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import org.usfirst.frc.team2521.robot.OI;
import org.usfirst.frc.team2521.robot.RobotMap;
import org.usfirst.frc.team2521.robot.commands.TeleopDrivetrain;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Drivetrain is the subsystem for everything that relates to the robot's
 * drivetrain. It consists of various methods to set the speed of certain
 * motors and control the drivetrain with operator input.
 */
public class Drivetrain extends Subsystem {
	private RobotDrive frontDrive;
	private RobotDrive rearDrive;

	private CANTalon frontLeft, frontRight, rearLeft, rearRight;

	public Drivetrain() {
		frontLeft = new CANTalon(RobotMap.FRONT_LEFT_MOTOR);
		frontRight = new CANTalon(RobotMap.FRONT_RIGHT_MOTOR);
		rearLeft = new CANTalon(RobotMap.REAR_LEFT_MOTOR);
		rearRight = new CANTalon(RobotMap.REAR_RIGHT_MOTOR);

		frontDrive = new RobotDrive(frontLeft, frontRight);
		rearDrive = new RobotDrive(rearLeft, rearRight);
	}

	/**
	 * Enables tank driving using the left and right joysticks.
	 * 
	 * @see OI#getLeftStick()
	 * @see OI#getRightStick()
	 */
	public void tankDrive() {
		double left = OI.getInstance().getLeftStick().getY();
		double right = OI.getInstance().getRightStick().getY();

		frontDrive.tankDrive(right, left);
		rearDrive.tankDrive(right, left);
	}

	/**
	 * Changes all talon control modes to PercentVbus, then enables tank drive.
	 * 
	 * @see Drivetrain#tankDrive()
	 */
	public void teleoperatedDrive() {
		frontLeft.changeControlMode(TalonControlMode.PercentVbus);
		frontRight.changeControlMode(TalonControlMode.PercentVbus);
		rearLeft.changeControlMode(TalonControlMode.PercentVbus);
		rearRight.changeControlMode(TalonControlMode.PercentVbus);

		tankDrive();
	}

	/**
	 * Sets the left-side motors to a certain speed.
	 * 
	 * @param value  set the left-side motors to speed
	 */
	public void setLeft(double value) {
		frontLeft.set(value);

		rearLeft.changeControlMode(TalonControlMode.Follower);
		rearLeft.set(RobotMap.FRONT_LEFT_MOTOR);
	}

	/**
	 * Sets the right-side motors to a certain speed.
	 * 
	 * @param value  set the right-side motors to speed
	 */
	public void setRight(double value) {
		frontRight.set(value);

		rearRight.changeControlMode(TalonControlMode.Follower);
		rearRight.set(RobotMap.FRONT_RIGHT_MOTOR);
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new TeleopDrivetrain());
	}
}

