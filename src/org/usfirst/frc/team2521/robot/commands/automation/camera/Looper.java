package org.usfirst.frc.team2521.robot.commands.automation.camera;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team2521.robot.Robot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.wpilibj.Preferences;

public final class Looper implements Runnable {
	private static final Looper INSTANCE = new Looper();

	private static final int INPUT_STREAM_PORT = 1185;
	private static final int CV_STREAM_PORT = 1186;

	private boolean mIsStarted;

	private CvSink mImageSink;
	private CvSource mImageSource;

	private Rect mLatestRect;

	private Thread mThread;

	private Looper() {
		mThread = new Thread(this);
		mThread.start();
	}

	public static Looper getInstance() {
		return INSTANCE;
	}

	private static UsbCamera setUsbCamera(int cameraId, MjpegServer server) {
		UsbCamera camera = new UsbCamera("CoprocessorCamera", cameraId);
		server.setSource(camera);

		return camera;
	}

	public void loop() {
		mThread.interrupt();
	}

	private void initialize() {
		mIsStarted = true;

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
	}

	@Override
	public void run() {
		if (!mIsStarted) initialize();

		Mat inputImage = new Mat();
		long frameTime = mImageSink.grabFrame(inputImage);
		if (frameTime == 0) {
			waitForInterrupt();
			return;
		}

		List<MatOfPoint> contours = new ArrayList<>();

		Preferences prefs = Preferences.getInstance();

		Mat greenMask = new Mat();
		Scalar upperb = new Scalar(prefs.getInt("upper_b", 254),
								   prefs.getInt("upper_g", 254),
								   prefs.getInt("upper_r", 254));
		Core.inRange(inputImage,
					 new Scalar(prefs.getInt("lower_b", 0),
								prefs.getInt("lower_g", 0),
								prefs.getInt("lower_r", 0)),
					 upperb,
					 greenMask);
		Mat hierarchy = new Mat();
		Imgproc.findContours(greenMask,
							 contours,
							 hierarchy,
							 Imgproc.RETR_TREE,
							 Imgproc.CHAIN_APPROX_SIMPLE);

		List<Rect> rects = new ArrayList<>();
		for (MatOfPoint point : contours) {
			rects.add(Imgproc.boundingRect(point));
		}
		rects.sort(Comparator.comparingDouble(Rect::area));

		if (!rects.isEmpty()) {
			mLatestRect = rects.get(rects.size() - 1);

			if (Robot.DEBUG) {
				Imgproc.rectangle(inputImage, mLatestRect.tl(), mLatestRect.br(), upperb);
				mImageSource.putFrame(inputImage);
			}
		}

		inputImage.release();
		greenMask.release();
		hierarchy.release();

		waitForInterrupt();
	}

	private void waitForInterrupt() {
		try {
			Thread.sleep(TimeUnit.HOURS.toMillis(1));
		} catch (InterruptedException e) {
			mThread.run();
		}
	}

	public boolean getBlobFound() {
		if (mLatestRect == null) return false;
		double area = mLatestRect.area();
		return area >= 50 && area <= 10000;
	}
}
