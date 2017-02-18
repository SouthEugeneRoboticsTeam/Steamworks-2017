package org.usfirst.frc.team2521.robot;

import org.usfirst.frc.team2521.robot.commands.DriveToBoiler;
import org.usfirst.frc.team2521.robot.commands.DriveToGear;
import org.usfirst.frc.team2521.robot.commands.RunAgitator;
import org.usfirst.frc.team2521.robot.commands.RunFeeder;
import org.usfirst.frc.team2521.robot.commands.RunShooter;
import org.usfirst.frc.team2521.robot.commands.Spintake;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class binds the operator controls to commands and command groups on the robot, allowing the
 * driver to control the robot.
 */
public class OI {
	private static OI instance;

	private final Joystick left;
	private final Joystick right;
	private final Joystick secondary;

	private JoystickButton driveToBoilerButton;
	private JoystickButton driveToGearLeftButton;
	private JoystickButton driveToGearRightButton;
	private JoystickButton runShooterButton;
	private JoystickButton runFeederButton;
	private JoystickButton spintakeButton;
	private JoystickButton runAgitatorForwardButton;
	private JoystickButton runAgitatorBackwardButton;

	private OI() {
		left = new Joystick(RobotMap.LEFT_STICK_PORT);
		right = new Joystick(RobotMap.RIGHT_STICK_PORT);
		secondary = new Joystick(RobotMap.SECONDARY_STICK_PORT);

		// Right joystick buttons
		driveToBoilerButton = new JoystickButton(right, RobotMap.DRIVE_TO_BOILER_BUTTON_PORT);
		driveToGearLeftButton = new JoystickButton(right, RobotMap.DRIVE_TO_GEAR_LEFT_PORT);
		driveToGearRightButton = new JoystickButton(right, RobotMap.DRIVE_TO_GEAR_RIGHT_PORT);
		spintakeButton = new JoystickButton(right, RobotMap.SPINTAKE_BUTTON_PORT);

		// Secondary joystick buttons
		runShooterButton = new JoystickButton(secondary, RobotMap.RUN_SHOOTER_BUTTON_PORT);
		runFeederButton = new JoystickButton(secondary, RobotMap.RUN_FEEDER_BUTTON_PORT);
		runAgitatorForwardButton = new JoystickButton(secondary, RobotMap.RUN_AGITATOR_FORWARD_BUTTON_PORT);
		runAgitatorBackwardButton = new JoystickButton(secondary, RobotMap.RUN_AGITATOR_BACKWARD_BUTTON_PORT);
		
		setButtonListeners();
	}

	/**
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
		driveToBoilerButton.toggleWhenActive(new DriveToBoiler());
		driveToGearLeftButton.toggleWhenActive(new DriveToGear(true));
		driveToGearRightButton.toggleWhenActive(new DriveToGear(false));
		runShooterButton.toggleWhenActive(new RunShooter());
		runFeederButton.whileActive(new RunFeeder());
		spintakeButton.toggleWhenActive(new Spintake());
		runAgitatorForwardButton.toggleWhenActive(new RunAgitator(true));
		runAgitatorBackwardButton.toggleWhenActive(new RunAgitator(false));
	}
}
