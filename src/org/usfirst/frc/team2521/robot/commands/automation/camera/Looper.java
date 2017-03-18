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
	private static final int CAMERA_ID0 = 0;

	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	private static final int CENTER_X = WIDTH / 2;
	private static final int FPS = 30;

	private static final int MIN_AREA = 50;
	private static final int MAX_AREA = 10000;
	private final List<Rect> mLatestRects = new ArrayList<>();
	private boolean mIsStarted;
	private CvSink mImageSink;
	private CvSource mImageSource;
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

	public boolean hasFoundBlob() {
		Pair<Rect, Rect> blobs = getLargestBlobs();
		if (blobs == null) return false;
		double largestArea = blobs.first.area();
		double secondLargestArea = blobs.second.area();
		return isAreaInBounds(largestArea) && isAreaInBounds(secondLargestArea);
	}

	public double getCVOffsetX() {
		if (!hasFoundBlob()) {
			throw new IllegalStateException("Cannot get CV offset if no blobs have been found");
		}
		return getCenterOfBlobsX(getLargestBlobs()) - CENTER_X;
	}

	private void initialize() {
		mIsStarted = true;

		MjpegServer inputStream = new MjpegServer("MJPEG Server", INPUT_STREAM_PORT);

		UsbCamera camera = setUsbCamera(CAMERA_ID0, inputStream);
		camera.setResolution(WIDTH, HEIGHT);

		mImageSink = new CvSink("CV Image Grabber");
		mImageSink.setSource(camera);

		mImageSource = new CvSource(
				"CV Image Source", VideoMode.PixelFormat.kMJPEG, WIDTH, HEIGHT, FPS);
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
		Scalar lowerBounds = new Scalar(prefs.getInt("lower_b", 0),
										prefs.getInt("lower_g", 0),
										prefs.getInt("lower_r", 0));
		Scalar upperBounds = new Scalar(prefs.getInt("upper_b", 255),
										prefs.getInt("upper_g", 255),
										prefs.getInt("upper_r", 255));
		Core.inRange(inputImage, lowerBounds, upperBounds, greenMask);
		Mat hierarchy = new Mat();
		Imgproc.findContours(greenMask,
							 contours,
							 hierarchy,
							 Imgproc.RETR_TREE,
							 Imgproc.CHAIN_APPROX_SIMPLE);

		List<Rect> rects = new ArrayList<>(contours.size());
		for (MatOfPoint point : contours) {
			rects.add(Imgproc.boundingRect(point));
		}
		rects.sort(Comparator.comparingDouble(Rect::area));

		synchronized (mLatestRects) {
			mLatestRects.clear();
			mLatestRects.addAll(rects);

			if (Robot.DEBUG) {
				Pair<Rect, Rect> blobs = getLargestBlobs();
				if (blobs != null) {
					Rect largest = blobs.first;
					Imgproc.rectangle(inputImage, largest.tl(), largest.br(), upperBounds);
					mImageSource.putFrame(inputImage);
				}
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

	private boolean isAreaInBounds(double area) {
		return area >= MIN_AREA && area <= MAX_AREA;
	}

	private int getCenterOfBlobsX(@NonNull Pair<Rect, Rect> blobs) {
		return (getCenterX(blobs.first) + getCenterX(blobs.second)) / 2;
	}

	private int getCenterX(Rect blob) {
		return blob.x + (blob.width / 2);
	}

	@Nullable
	private Pair<Rect, Rect> getLargestBlobs() {
		synchronized (mLatestRects) {
			if (mLatestRects.size() < 2) return null;
			else return Pair.create(mLatestRects.get(mLatestRects.size() - 1),
									mLatestRects.get(mLatestRects.size() - 2));
		}
	}
}
