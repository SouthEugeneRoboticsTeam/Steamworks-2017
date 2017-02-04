package org.usfirst.frc.team2521.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import org.usfirst.frc.team2521.robot.OI;
import org.usfirst.frc.team2521.robot.RobotMap;
import org.usfirst.frc.team2521.robot.commands.TeleopDrivetrain;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drivetrain is the subsystem for everything that relates to the robot's
 * drivetrain. It consists of various methods to set the speed of certain
 * motors and control the drivetrain with operator input.
 */
public class Drivetrain extends Subsystem {
	private RobotDrive frontDrive;
	private RobotDrive rearDrive;

	private CANTalon frontLeft, frontRight, rearLeft, rearRight;
	
	private final double LEFT_P = 0;
	private final double LEFT_I = 0;
	private final double LEFT_D = 0;
	
	private final double RIGHT_P = 0;
	private final double RIGHT_I = 0;
	private final double RIGHT_D = 0;
	
	private final double ENC_TICKS_PER_ROTATION = 256;
	
	private final double WHEEL_CIRCUMFERENCE = 6*Math.PI; // inches

	public Drivetrain() {
		frontLeft = new CANTalon(RobotMap.FRONT_LEFT_MOTOR);
		frontRight = new CANTalon(RobotMap.FRONT_RIGHT_MOTOR);
		rearLeft = new CANTalon(RobotMap.REAR_LEFT_MOTOR);
		rearRight = new CANTalon(RobotMap.REAR_RIGHT_MOTOR);
		
		frontDrive = new RobotDrive(frontLeft, frontRight);
		rearDrive = new RobotDrive(rearLeft, rearRight);
		
		frontLeft.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		rearRight.setFeedbackDevice(FeedbackDevice.QuadEncoder);
	}
	
	public void driveToDistanceInches(double distance) {
		distance *= (ENC_TICKS_PER_ROTATION)/WHEEL_CIRCUMFERENCE;
		
		driveToDistanceRaw(distance);
	}
	
	public void driveToDistanceRaw(double position) {
		frontLeft.changeControlMode(TalonControlMode.Position);
		rearLeft.changeControlMode(TalonControlMode.Follower);
		frontRight.changeControlMode(TalonControlMode.Follower);
		rearRight.changeControlMode(TalonControlMode.Position);
		
		frontLeft.setPID(LEFT_P, LEFT_I, LEFT_D);
		rearRight.setPID(RIGHT_P, RIGHT_I, RIGHT_D);
		
		frontLeft.set(position);
		rearRight.set(position);
	}
	

	/**
	 * Enables tank driving using the left and right joysticks.
	 * 
	 * @see OI#getLeftStick()
	 * @see OI#getRightStick()
	 */
	public void tankDrive() {
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
		SmartDashboard.putString("Teleop drive", "teleop drive");
		frontLeft.changeControlMode(TalonControlMode.PercentVbus);
		frontRight.changeControlMode(TalonControlMode.PercentVbus);
		rearLeft.changeControlMode(TalonControlMode.PercentVbus);
		rearRight.changeControlMode(TalonControlMode.PercentVbus);

		tankDrive();
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
	
	public double getLeftEnc() {
		return rearLeft.getEncPosition();
	}

	public double getRightEnc() {
		return rearRight.getEncPosition();
	}
	
	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new TeleopDrivetrain());
	}
}

