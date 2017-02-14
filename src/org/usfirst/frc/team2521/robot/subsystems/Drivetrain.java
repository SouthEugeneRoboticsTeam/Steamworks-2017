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
	
	/** {@code true} if we're driving in arcade in teleop */
	public boolean isArcade = false;

	public Drivetrain() {
		frontLeft = new CANTalon(RobotMap.FRONT_LEFT_MOTOR);
		frontRight = new CANTalon(RobotMap.FRONT_RIGHT_MOTOR);
		rearLeft = new CANTalon(RobotMap.REAR_LEFT_MOTOR);
		rearRight = new CANTalon(RobotMap.REAR_RIGHT_MOTOR);

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
		
		if (isArcade) {
			arcadeDrive();
		} else {
			tankDrive();
		}
		
	}

	/**
	 * Sets the left-side motors to a certain speed.
	 * 
	 * @param value  speed to set the left-side motors to
	 */
	public void setLeft(double value) {
		frontLeft.set(value);

		rearLeft.changeControlMode(TalonControlMode.Follower);
		rearLeft.set(RobotMap.FRONT_LEFT_MOTOR);
	}

	/**
	 * Sets the right-side motors to a certain speed.
	 * 
	 * @param value  speed to set the right-side motors to
	 */
	public void setRight(double value) {
		frontRight.set(value);

		rearRight.changeControlMode(TalonControlMode.Follower);
		rearRight.set(RobotMap.FRONT_RIGHT_MOTOR);
	}

	public int getLeftEnc() {
		return frontLeft.getEncPosition();
	}

	public int getRightEnc() {
		return frontRight.getEncPosition();
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new TeleopDrivetrain());
	}
}
