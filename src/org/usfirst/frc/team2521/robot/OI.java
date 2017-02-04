package org.usfirst.frc.team2521.robot;

import org.usfirst.frc.team2521.robot.commands.AutoAlign;
import org.usfirst.frc.team2521.robot.commands.DriveToGear;

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

	private JoystickButton autoAlignButton;
	private JoystickButton driveToGearLeftButton;
	private JoystickButton driveToGearRightButton;

	private OI() {
		left = new Joystick(RobotMap.LEFT_STICK_PORT);
		right = new Joystick(RobotMap.RIGHT_STICK_PORT);
		secondary = new Joystick(RobotMap.SECONDARY_STICK_PORT);

		autoAlignButton = new JoystickButton(right, RobotMap.AUTO_ALIGN_BUTTON_PORT);
		driveToGearLeftButton = new JoystickButton(right, RobotMap.DRIVE_TO_GEAR_LEFT_PORT);
		driveToGearRightButton = new JoystickButton(right, RobotMap.DRIVE_TO_GEAR_RIGHT_PORT);

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
	 * @return the left joystick
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
		autoAlignButton.whenPressed(new AutoAlign());
		driveToGearLeftButton.toggleWhenActive(new DriveToGear(true));
		driveToGearRightButton.toggleWhenActive(new DriveToGear(false));
	}
}
