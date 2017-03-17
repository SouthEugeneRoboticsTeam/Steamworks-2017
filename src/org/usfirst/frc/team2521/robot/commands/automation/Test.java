package org.usfirst.frc.team2521.robot.commands.automation;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

public class Test extends Command {
	private static final int INPUT_STREAM_PORT = 1185;
	private static final int CV_STREAM_PORT = 1186;

	private CvSink mImageSink;
	private Mat mInputImage;
	private Mat mGreenMask;
	private CvSource mImageSource;
	private Mat mOutputImage;

	private static UsbCamera setUsbCamera(int cameraId, MjpegServer server) {
		UsbCamera camera = new UsbCamera("CoprocessorCamera", cameraId);
		server.setSource(camera);

		return camera;
	}

	@Override
	protected void initialize() {
		MjpegServer inputStream = new MjpegServer("MJPEG Server", INPUT_STREAM_PORT);

		UsbCamera camera = setUsbCamera(0, inputStream);
		camera.setResolution(640, 480);

		mImageSink = new CvSink("CV Image Grabber");
		mImageSink.setSource(camera);

		mImageSource = new CvSource("CV Image Source",
									VideoMode.PixelFormat.kMJPEG,
									640,
									480,
									30);
		MjpegServer cvStream = new MjpegServer("CV Image Stream", CV_STREAM_PORT);
		cvStream.setSource(mImageSource);

		mGreenMask = new Mat();
		mInputImage = new Mat();
		mOutputImage = new Mat();
	}

	@Override
	protected void execute() {
		long frameTime = mImageSink.grabFrame(mInputImage);
		if (frameTime == 0) return;

		List<MatOfPoint> contours = new ArrayList<>();

		Preferences prefs = Preferences.getInstance();

		Core.inRange(mInputImage,
					 new Scalar(prefs.getInt("1", 0), prefs.getInt("2", 0), prefs.getInt("3", 0)),
					 new Scalar(prefs.getInt("4", 0), prefs.getInt("5", 0), prefs.getInt("6", 0)),
					 mGreenMask);
		Mat hierarchy = new Mat();
		Imgproc.findContours(mGreenMask,
							 contours,
							 hierarchy,
							 Imgproc.RETR_TREE,
							 Imgproc.CHAIN_APPROX_SIMPLE);

		mImageSource.putFrame(mGreenMask);
		hierarchy.release();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
