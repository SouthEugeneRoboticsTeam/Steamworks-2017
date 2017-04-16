package org.usfirst.frc.team2521.robot;

/**
 * This class contains the constants for robot ports.
 */
public class RobotMap {
	public static final int LEFT_STICK_PORT = 0;
	public static final int SECONDARY_STICK_PORT = 1;
	public static final int CUSTOM_STICK_PORT = 2;

	// Left joystick buttons
	public static final int ALIGN_SHOOTER_BUTTON_PORT = 7;
	public static final int DRIVE_TO_GEAR_LEFT_PORT = 8;

	// Secondary joystick buttons
	public static final int RUN_SHOOTER_SUBSYSTEMS_BUTTON_PORT = 1;
	public static final int RUN_AGITATOR_BACKWARD_BUTTON_PORT = 2;

	public static final int FRONT_RIGHT_MOTOR = 10;
	public static final int FRONT_LEFT_MOTOR = 14;
	public static final int REAR_RIGHT_MOTOR = 11;
	public static final int REAR_LEFT_MOTOR = 15;

	public static final int CLIMBER_MASTER_MOTOR = 16;
	public static final int CLIMBER_SLAVE_MOTOR = 17;

	public static final int AGITATOR_MOTOR = 19;

	public static final int SHOOTER_MOTOR = 13;
	public static final int FEEDER_MOTOR = 12;

	public static final int REAR_ULTRA_PORT = 1;

  public static final int IS_SHOOTING_PIN = 1;
	public static final int IS_BLUE_ALLIANCE_PIN = 2;
	public static final int IS_RED_ALLIANCE_PIN = 3;

	public final static class AutoModes {
		public final static int NOTHING = 3;
		public final static int CROSS_BASELINE = 6;
		public final static int GEAR_THEN_BALL = 15;
		public final static int GEAR_MIDDLE = 4;
		public final static int GEAR_FEEDER = 2;
		public final static int GEAR_BOILER = 8;
		public final static int BALLS_ONLY = 1;
		public final static int BALLS_STATIONARY = 5;
	}
}
