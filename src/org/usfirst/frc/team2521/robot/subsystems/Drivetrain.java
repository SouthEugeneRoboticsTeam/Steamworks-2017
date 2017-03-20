package org.usfirst.frc.team2521.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import org.usfirst.frc.team2521.robot.OI;
import org.usfirst.frc.team2521.robot.RobotMap;
import org.usfirst.frc.team2521.robot.commands.base.TeleopDrivetrain;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Drivetrain is the subsystem for everything that relates to the robot's drivetrain. It consists of
 * various methods to set the speed of certain motors and control the drivetrain with operator
 * input.
 */
public class Drivetrain extends Subsystem {
	/** Speed to set drivetrain to when we want to move at a slow, constant speed */
	public static final double SLOW_SPEED = 0.4;

	private RobotDrive frontDrive;
	private RobotDrive rearDrive;
	private CANTalon frontLeft, frontRight, rearLeft, rearRight;

	public Drivetrain() {
		frontLeft = new CANTalon(RobotMap.FRONT_LEFT_MOTOR);
		frontRight = new CANTalon(RobotMap.FRONT_RIGHT_MOTOR);
		rearLeft = new CANTalon(RobotMap.REAR_LEFT_MOTOR);
		rearRight = new CANTalon(RobotMap.REAR_RIGHT_MOTOR);

		frontLeft.enableBrakeMode(true);
		frontRight.enableBrakeMode(true);
		rearLeft.enableBrakeMode(true);
		rearRight.enableBrakeMode(true);

		frontDrive = new RobotDrive(frontLeft, frontRight);
		rearDrive = new RobotDrive(rearLeft, rearRight);
	}

	/**
	 * Enables arcade driving using the left joysticks.
	 *
	 * @see OI#getLeftStick()
	 */
	private void arcadeDrive() {
		double move = -OI.getInstance().getLeftStick().getY();
		double rotate = -OI.getInstance().getLeftStick().getX();

		frontDrive.arcadeDrive(move, rotate);
		rearDrive.arcadeDrive(move, rotate);
	}

	/**
	 * Runs drivetrain with joystick input
	 */
	public void teleoperatedDrive() {
		frontLeft.changeControlMode(TalonControlMode.PercentVbus);
		frontRight.changeControlMode(TalonControlMode.PercentVbus);
		rearLeft.changeControlMode(TalonControlMode.PercentVbus);
		rearRight.changeControlMode(TalonControlMode.PercentVbus);

		arcadeDrive();
	}

	/**
	 * Sets the left-side motors to a certain speed.
	 *
	 * @param value speed to set the left-side motors to
	 */
	public void setLeft(double value) {
		frontLeft.changeControlMode(TalonControlMode.PercentVbus);
		frontLeft.set(value);

		rearLeft.changeControlMode(TalonControlMode.Follower);
		rearLeft.set(RobotMap.FRONT_LEFT_MOTOR);
	}

	/**
	 * Sets the right-side motors to a certain speed.
	 *
	 * @param value speed to set the right-side motors to
	 */
	public void setRight(double value) {
		frontRight.changeControlMode(TalonControlMode.PercentVbus);
		frontRight.set(-value);

		rearRight.changeControlMode(TalonControlMode.Follower);
		rearRight.set(RobotMap.FRONT_RIGHT_MOTOR);
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new TeleopDrivetrain());
	}
}
