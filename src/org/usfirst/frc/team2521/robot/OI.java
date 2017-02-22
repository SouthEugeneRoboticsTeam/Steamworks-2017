package org.usfirst.frc.team2521.robot;

import org.usfirst.frc.team2521.robot.commands.automation.DriveToGear;
import org.usfirst.frc.team2521.robot.commands.base.RunAgitator;
import org.usfirst.frc.team2521.robot.commands.groups.AlignShooter;
import org.usfirst.frc.team2521.robot.commands.groups.RunShooterSubsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class binds the operator controls to commands and command groups on the robot, allowing the
 * driver to control the robot.
 */
public class OI {
	private final Joystick left;
	private final Joystick right;
	private final Joystick secondary;
	private final Joystick custom;
	
	private final int[] customButton = {2,3,4,5};

	private JoystickButton alignShooterButton;
	private JoystickButton driveToGearLeftButton;
	private JoystickButton driveToGearRightButton;
	private JoystickButton runShooterSubsystemsButton;
	private JoystickButton runAgitatorBackwardButton;

	private OI() {
		left = new Joystick(RobotMap.LEFT_STICK_PORT);
		right = new Joystick(RobotMap.RIGHT_STICK_PORT);
		secondary = new Joystick(RobotMap.SECONDARY_STICK_PORT);
		custom = new Joystick(RobotMap.CUSTOM_STICK_PORT);

		// Right joystick buttons
		alignShooterButton = new JoystickButton(left, RobotMap.ALIGN_SHOOTER_BUTTON_PORT);
		driveToGearLeftButton = new JoystickButton(left, RobotMap.DRIVE_TO_GEAR_LEFT_PORT);
		driveToGearRightButton = new JoystickButton(left, RobotMap.DRIVE_TO_GEAR_RIGHT_PORT);

		// Secondary joystick buttons
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
		int autoMode = 0;
		for (int i = 0; i < customButton.length; i++) {
			if (custom.getRawButton(customButton[i])) {
				autoMode += Math.pow(2, i);
			}
		}
		return autoMode;
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

	/**
	 * @return the secondary joystick
	 */
	public Joystick getSecondaryStick() {
		return secondary;
	}

	private void setButtonListeners() {
		alignShooterButton.toggleWhenActive(new AlignShooter());
		driveToGearLeftButton.toggleWhenActive(new DriveToGear(true));
		driveToGearRightButton.toggleWhenActive(new DriveToGear(false));
		runShooterSubsystemsButton.toggleWhenActive(new RunShooterSubsystems());
		runAgitatorBackwardButton.toggleWhenActive(new RunAgitator(false));
	}

	private static final class Holder {
		public static final OI INSTANCE = new OI();
	}
	
	public final static class AutoModes {
		public final static int nothing = 0;
		public final static int crossBaseLine = 6;
		public final static int ballThenGear = 15;
		public final static int gearLeft = 8;
		public final static int gearMiddle = 4;
		public final static int gearRight = 2;
		public final static int ballsOnly = 1;
	}
}
