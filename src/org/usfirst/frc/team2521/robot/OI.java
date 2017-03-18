package org.usfirst.frc.team2521.robot;

import org.usfirst.frc.team2521.robot.commands.automation.DriveToGear;
import org.usfirst.frc.team2521.robot.commands.base.RunAgitator;
import org.usfirst.frc.team2521.robot.commands.base.RunClimber;
import org.usfirst.frc.team2521.robot.commands.groups.AlignShooter;
import org.usfirst.frc.team2521.robot.commands.groups.RunShooterSubsystems;

import java.util.stream.IntStream;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class binds the operator controls to commands and command groups on the robot, allowing the
 * driver to control the robot.
 */
public class OI {
	private static final int[] CUSTOM_BUTTONS = {2, 3, 4, 5};

	private final Joystick left;
	private final Joystick right;
	private final Joystick custom;

	private JoystickButton alignShooterButton;
	private JoystickButton driveToGearLeftButton;
	private JoystickButton driveToGearRightButton;
	private JoystickButton runShooterSubsystemsButton;
	private JoystickButton runAgitatorBackwardButton;
	private JoystickButton runClimberButton;

	private OI() {
		left = new Joystick(RobotMap.LEFT_STICK_PORT);
		right = new Joystick(RobotMap.RIGHT_STICK_PORT);
		custom = new Joystick(RobotMap.CUSTOM_STICK_PORT);

		// Right joystick buttons
		alignShooterButton = new JoystickButton(left, RobotMap.ALIGN_SHOOTER_BUTTON_PORT);
		driveToGearLeftButton = new JoystickButton(left, RobotMap.DRIVE_TO_GEAR_LEFT_PORT);
		driveToGearRightButton = new JoystickButton(left, RobotMap.DRIVE_TO_GEAR_RIGHT_PORT);
		runClimberButton = new JoystickButton(left, RobotMap.RUN_CLIMBER_PORT);

		// Secondary joystick buttons
		Joystick secondary = new Joystick(RobotMap.SECONDARY_STICK_PORT);
		runShooterSubsystemsButton = new JoystickButton(secondary,
														RobotMap.RUN_SHOOTER_SUBSYSTEMS_BUTTON_PORT);
		runAgitatorBackwardButton = new JoystickButton(secondary,
													   RobotMap.RUN_AGITATOR_BACKWARD_BUTTON_PORT);

		setButtonListeners();
	}

	/**
	 * @return the singleton instance of the OI
	 */
	public static OI getInstance() {
		return Holder.INSTANCE;
	}

	public int getAutoMode() {
		return IntStream.range(0, CUSTOM_BUTTONS.length)
				.filter(i -> custom.getRawButton(CUSTOM_BUTTONS[i]))
				.map(i -> (int) Math.pow(2, i))
				.sum();
	}

	/**
	 * @return the left joystick
	 */
	public Joystick getLeftStick() {
		return left;
	}

	/**
	 * @return the right joystick
	 */
	public Joystick getRightStick() {
		return right;
	}

	private void setButtonListeners() {
		alignShooterButton.toggleWhenActive(new AlignShooter());
		driveToGearLeftButton.toggleWhenActive(new DriveToGear());
		driveToGearRightButton.toggleWhenActive(new DriveToGear());
		runShooterSubsystemsButton.toggleWhenActive(new RunShooterSubsystems());
		runAgitatorBackwardButton.toggleWhenActive(new RunAgitator(false));
		runClimberButton.whileHeld(new RunClimber());
	}

	private static final class Holder {
		public static final OI INSTANCE = new OI();
	}
}
