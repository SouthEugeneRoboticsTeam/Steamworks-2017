package org.usfirst.frc.team2521.robot;

import org.usfirst.frc.team2521.robot.commands.AutoAlign;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private static OI instance;

	public Preferences prefs;

	private final Joystick left;
	private final Joystick right;

	private JoystickButton autoAlignButton;

	private OI() {
		left = new Joystick(RobotMap.LEFT_STICK_PORT);
		right = new Joystick(RobotMap.RIGHT_STICK_PORT);

		autoAlignButton = new JoystickButton(right, RobotMap.AUTO_ALIGN_BUTTON_PORT);

		setButtonListeners();
	}

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

	public Joystick getLeftStick() {
		return left;
	}

	public Joystick getRightStick() {
		return right;
	}

	private void setButtonListeners() {
		autoAlignButton.whenPressed(new AutoAlign());
	}
}
