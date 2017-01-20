package org.usfirst.frc.team2521.robot;

import org.usfirst.frc.team2521.robot.commands.ToggleShooter;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public Preferences prefs;
	
	private Joystick left;
	private Joystick right;
	
	private JoystickButton smartDashboardShooter;
	
	private static OI instance;
	
	public OI() {
		left = new Joystick(RobotMap.LEFT_STICK_PORT);
		right = new Joystick(RobotMap.RIGHT_STICK_PORT);
		
		initButtons();
	}
	
	public static OI getInstance() {
		if (instance == null) {
			instance = new OI();
		}
		
		return instance;
	}
	
	public Joystick getLeftStick() {
		return left;
	}
	
	public Joystick getRightStick() {
		return right;
	}
	
	public void initButtons() {
		smartDashboardShooter = new JoystickButton(left, 0);
		tieButtons();
	}
	
	public void tieButtons() {
		smartDashboardShooter.whenPressed(new ToggleShooter());
	}
	
}
