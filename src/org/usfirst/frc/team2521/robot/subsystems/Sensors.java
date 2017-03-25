package org.usfirst.frc.team2521.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import org.usfirst.frc.team2521.robot.Robot;
import org.usfirst.frc.team2521.robot.RobotMap;
import org.usfirst.frc.team2521.robot.commands.automation.camera.Looper;
import org.usfirst.frc.team2521.robot.utils.Nullable;
import org.usfirst.frc.team2521.robot.commands.base.DisplaySensors;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Sensors is the subsystem for easily managing all sensor values. In addition, it provides simple
 * methods to show sensor data on the SmartDashboard.
 */
public class Sensors extends Subsystem {
	public static final double DEFAULT_CV_OFFSET = 0;

	private AnalogInput rearUltra;
	private AHRS ahrs;
	private Camera.Type cameraType;

	public Sensors() {
		rearUltra = new AnalogInput(RobotMap.REAR_ULTRA_PORT);

		ahrs = new AHRS(SPI.Port.kMXP);
		ahrs.reset();

		setCVCamera(Camera.Type.FRONT);
	}

	/**
	 * Displays sensor data on the SmartDashboard.
	 */
	public void display() {
		SmartDashboard.putNumber("Rear ultra distance", getRearUltraInches());
		SmartDashboard.putBoolean("Blob found", hasFoundBlob());

		if (Robot.DEBUG) {
			SmartDashboard.putNumber("Navx angle", getNavxAngle());

			Double offsetX = getCVOffsetX();
			if (offsetX != null) SmartDashboard.putNumber("CV offset", offsetX);
		}
	}

	/**
	 * @return the distance in inches from the rear (shooter side) ultrasonic sensor
	 */
	public double getRearUltraInches() {
		return rearUltra.getVoltage() * 1000 / 9.8;
	}

	/**
	 * Returns the target's offset (in pixels) from the center of the screen on the X-axis. This
	 * value is only updated if hasFoundBlob() is `true`.
	 *
	 * @return the target's offset (in pixels) from the center of the screen
	 * @see Sensors#hasFoundBlob()
	 */
	@Nullable
	public Double getCVOffsetX() {
		return Looper.getInstance().getCVOffsetX();
	}

	/**
	 * @return whether a blob is currently being tracked in computer vision
	 * @see Sensors#getCVOffsetX()
	 */
	public boolean hasFoundBlob() {
		return Looper.getInstance().hasFoundBlob();
	}

	/**
	 * Sets which camera (front or back) vision code on the minnow
	 * board should use.
	 *
	 * @param cameraType desired camera to use
	 */
	public void setCVCamera(Camera.Type cameraType) {
		this.cameraType = cameraType;
	}

	public Camera.Type getCamera() {
		return cameraType;
	}

	/**
	 * @return the Navx's current angle measurement
	 */
	public double getNavxAngle() {
		return ahrs.getYaw();
	}

	public void resetNavxAngle() {
		ahrs.reset();
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DisplaySensors());
	}

	public static final class Camera {
		public static final int WIDTH = 640;
		public static final int HEIGHT = 480;

		private static final int FRONT_INPUT_STREAM_PORT = 1185;
		private static final int FRONT_CV_STREAM_PORT = 1186;
		private static final int FRONT_CAMERA_ID = 0;

		private static final int REAR_INPUT_STREAM_PORT = 1187;
		private static final int REAR_CV_STREAM_PORT = 1188;
		private static final int REAR_CAMERA_ID = 1;

		private static final int FPS = 30;
		private static final int BRIGHTNESS = 18;
		private static final int EXPOSURE = 0;
		private static final int WHITE_BALANCE_TEMP = 4500;

		private static final CvSink FRONT_IMAGE_SINK;
		private static final CvSource FRONT_IMAGE_SOURCE;

		private static final CvSink REAR_IMAGE_SINK;
		private static final CvSource REAR_IMAGE_SOURCE;

		static {
			/** Setup front image source. */
			FRONT_IMAGE_SINK = new CvSink("Front CV Image Grabber");
			FRONT_IMAGE_SOURCE = new CvSource("CV Image Source",
											  VideoMode.PixelFormat.kMJPEG,
											  WIDTH,
											  HEIGHT,
											  FPS);
			FRONT_IMAGE_SINK.setSource(
					getUsbCamera(FRONT_CAMERA_ID,
								 new MjpegServer("Front MJPEG Server", FRONT_INPUT_STREAM_PORT)));

			new MjpegServer("Front CV Image Stream", FRONT_CV_STREAM_PORT).setSource(
					FRONT_IMAGE_SOURCE);

			/** Setup rear image source. */
			REAR_IMAGE_SINK = new CvSink("Rear CV Image Grabber");
			REAR_IMAGE_SOURCE = new CvSource("CV Image Source",
											 VideoMode.PixelFormat.kMJPEG,
											 WIDTH,
											 HEIGHT,
											 FPS);
			REAR_IMAGE_SINK.setSource(
					getUsbCamera(REAR_CAMERA_ID,
								 new MjpegServer("Rear MJPEG Server", REAR_INPUT_STREAM_PORT)));

			new MjpegServer("Rear CV Image Stream",
							REAR_CV_STREAM_PORT).setSource(REAR_IMAGE_SOURCE);
		}

		private Camera() {
			throw new AssertionError("No instance for you!");
		}

		private static UsbCamera getUsbCamera(int cameraId, MjpegServer server) {
			UsbCamera camera = new UsbCamera("Camera", cameraId);

			camera.setResolution(WIDTH, HEIGHT);
			camera.setBrightness(BRIGHTNESS);
			camera.setExposureManual(EXPOSURE);
			camera.setWhiteBalanceManual(WHITE_BALANCE_TEMP);

			server.setSource(camera);

			return camera;
		}

		public enum Type {
			/** Used for auto gear. */
			FRONT(FRONT_IMAGE_SINK, FRONT_IMAGE_SOURCE),
			/** Used for auto shooting. */
			REAR(REAR_IMAGE_SINK, REAR_IMAGE_SOURCE);

			private final CvSink sink;
			private final CvSource source;

			Type(CvSink sink, CvSource source) {
				this.sink = sink;
				this.source = source;
			}

			public CvSink getSink() {
				return sink;
			}

			public CvSource getSource() {
				return source;
			}
		}
	}
}
