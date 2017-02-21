package org.usfirst.frc.team2521.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import org.usfirst.frc.team2521.robot.OI;
import org.usfirst.frc.team2521.robot.RobotMap;
import org.usfirst.frc.team2521.robot.commands.base.TeleopDrivetrain;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drivetrain is the subsystem for everything that relates to the robot's drivetrain. It consists of
 * various methods to set the speed of certain motors and control the drivetrain with operator
 * input.
 */
public class Drivetrain extends Subsystem {
	/** Speed to set drivetrain to when we want to move at a slow, constant speed */
	public static final double SLOW_SPEED = 0.3;

	private final double P = 0.01;
	private final double I = 0;
	private final double D = 0;

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
	 * Enables tank driving using the left and right joysticks.
	 *
	 * @see OI#getLeftStick()
	 * @see OI#getRightStick()
	 */
	private void tankDrive() {
		double left = -OI.getInstance().getLeftStick().getY();
		double right = -OI.getInstance().getRightStick().getY();

		frontDrive.tankDrive(left, right);
		rearDrive.tankDrive(left, right);
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

		if (Preferences.getInstance().getBoolean("is_arcade_mode", true)) {
			arcadeDrive();
		} else {
			tankDrive();
		}
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

		SmartDashboard.putNumber("Left output", value);
	}

	/**
	 * Sets the right-side motors to a certain speed.
	 *
	 * @param value speed to set the right-side motors to
	 */
	public void setRight(double value) {
		frontRight.changeControlMode(TalonControlMode.PercentVbus);
		frontRight.set(value);

		rearRight.changeControlMode(TalonControlMode.Follower);
		rearRight.set(RobotMap.FRONT_RIGHT_MOTOR);

		SmartDashboard.putNumber("Right output", value);
	}

	/**
	 * Sets the right rear motor using encoder position, and sets the
	 * remaining motors to follow it.
	 *
	 * @param position target for encoder
	 */
	public void setPosition(double position) {
		rearRight.reverseSensor(true);
		frontRight.changeControlMode(TalonControlMode.Follower);
		rearRight.changeControlMode(TalonControlMode.Position);
		rearRight.setPID(P, I, D);

		rearRight.set(position);
		frontRight.set(RobotMap.REAR_RIGHT_MOTOR);

		rearLeft.reverseOutput(true);
		frontLeft.reverseOutput(true);
		rearLeft.changeControlMode(TalonControlMode.Follower);
		frontLeft.changeControlMode(TalonControlMode.Follower);
		rearLeft.set(RobotMap.REAR_RIGHT_MOTOR);
		frontLeft.set(RobotMap.REAR_RIGHT_MOTOR);
	}

	/**
	 * @return error from rear right encoder closed loop control
	 */
	public double getRightEncoderError() {
		return rearRight.getError();
	}

	public double getLeftPosition() {
		return rearLeft.getEncPosition();
	}

	/**
	 * Sets the left rear motor using encoder position, and sets the
	 * other motor to follow it.
	 *
	 * @param position target for encoder
	 */
	public void setLeftPosition(double position) {
		frontLeft.changeControlMode(TalonControlMode.Follower);
		rearLeft.changeControlMode(TalonControlMode.Position);
		rearLeft.setPID(P, I, D);

		rearLeft.set(position);
		frontLeft.set(RobotMap.REAR_LEFT_MOTOR);
	}

	public double getRightPosition() {
		return rearRight.getEncPosition();
	}

	/**
	 * Sets the right rear motor using encoder position, and sets the
	 * other motor to follow it.
	 *
	 * @param position target for encoder
	 */
	public void setRightPosition(double position) {
		frontRight.changeControlMode(TalonControlMode.Follower);
		rearRight.changeControlMode(TalonControlMode.Position);
		rearRight.setPID(P, I, D);
		rearRight.reverseSensor(true);

		rearRight.set(position);
		frontRight.set(RobotMap.REAR_RIGHT_MOTOR);
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new TeleopDrivetrain());
	}
}
