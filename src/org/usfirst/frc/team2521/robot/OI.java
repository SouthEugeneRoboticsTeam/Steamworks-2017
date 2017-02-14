package org.usfirst.frc.team2521.robot;

import org.usfirst.frc.team2521.robot.commands.DriveToBoiler;
import org.usfirst.frc.team2521.robot.commands.DriveToGear;
import org.usfirst.frc.team2521.robot.commands.Spintake;
import org.usfirst.frc.team2521.robot.commands.RunFeeder;
import org.usfirst.frc.team2521.robot.commands.RunShooter;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class binds the operator controls to commands and command groups on the
 * robot, allowing the driver to control the robot.
 */
public class OI {
	private static OI instance;

	private final Joystick left;
	private final Joystick right;
	private final Joystick secondary;

	private JoystickButton shooterAlign;
	private JoystickButton driveToGearLeftButton;
	private JoystickButton driveToGearRightButton;
	private JoystickButton spinFlywheelButton;
	private JoystickButton spinFeederButton;
	private JoystickButton quickSpinButton;

	private OI() {
		left = new Joystick(RobotMap.LEFT_STICK_PORT);
		right = new Joystick(RobotMap.RIGHT_STICK_PORT);
		secondary = new Joystick(RobotMap.SECONDARY_STICK_PORT);

		shooterAlign = new JoystickButton(right, RobotMap.ALIGN_SHOOTER_BUTTON);
		driveToGearLeftButton = new JoystickButton(right, RobotMap.DRIVE_TO_GEAR_LEFT_PORT);
		driveToGearRightButton = new JoystickButton(right, RobotMap.DRIVE_TO_GEAR_RIGHT_PORT);
		spinFlywheelButton = new JoystickButton(right, RobotMap.SPIN_FLYWHEEL_BUTTON_PORT);
		spinFeederButton = new JoystickButton(right, RobotMap.SPIN_FEEDER_BUTTON_PORT);
		quickSpinButton = new JoystickButton(right, RobotMap.QUICK_SPIN_BUTTON_PORT);

		setButtonListeners();
	}

	/**
	 * Returns an instance of the operator interface.
	 * 
	 * @return an instance of the OI
	 */
	public static OI getInstance() {
		if (instance == null) {
			synchronized (OI.class) {
				if (instance == null) {
					instance = new OI();
				}
			}
		}

		return instance;
	}

	/**
	 * Returns the left joystick.
	 * 
	 * @return the left joystick
	 */
	public Joystick getLeftStick() {
		return left;
	}

	/**
	 * Returns the right joystick.
	 * 
	 * @return the right joystick
	 */
	public Joystick getRightStick() {
		return right;
	}

	/**
	 * Returns the secondary joystick.
	 * 
	 * @return the secondary joystick
	 */
	public Joystick getSecondaryStick() {
		return secondary;
	}

	private void setButtonListeners() {
		shooterAlign.toggleWhenActive(new DriveToBoiler());
		driveToGearLeftButton.toggleWhenActive(new DriveToGear(true));
		driveToGearRightButton.toggleWhenActive(new DriveToGear(false));
		spinFlywheelButton.toggleWhenActive(new RunShooter());
		spinFeederButton.whileActive(new RunFeeder());
		quickSpinButton.toggleWhenActive(new Spintake());
	}
}
