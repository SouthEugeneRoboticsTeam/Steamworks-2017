package org.usfirst.frc.team2521.robot.commands.automation.camera;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team2521.robot.Robot;
import org.usfirst.frc.team2521.robot.subsystems.Sensors;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import edu.wpi.first.wpilibj.Preferences;

import static org.usfirst.frc.team2521.robot.subsystems.Sensors.Camera;

public final class Looper implements Runnable {
	private static final Looper INSTANCE = new Looper();
	private static final Object LOCK = new Object();
	private static final ExecutorService SERVICE = Executors.newCachedThreadPool();
	private static final int MAX_TASKS = 3;

	private static final int CENTER_X = Camera.WIDTH / 2;

	private static final int MIN_AREA = 50;
	private static final int MAX_AREA = 10000;

	private final List<Rect> latestRects = new ArrayList<>();
	private final List<Future> tasks = new ArrayList<>();

	private Looper() {
		// Singleton
	}

	public static Looper getInstance() {
		return INSTANCE;
	}

	public void loop() {
		synchronized (tasks) {
			clearCompletedTasks();
			if (tasks.size() < MAX_TASKS) tasks.add(SERVICE.submit(this));
		}
	}

	private void clearCompletedTasks() {
		for (Future task : new ArrayList<>(tasks)) if (task.isDone()) tasks.remove(task);
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

	@Override
	public void run() {
		Sensors.Camera.Type camera = Robot.sensors.getCamera();
		Mat inputImage = new Mat();
		synchronized (LOCK) {
			if (camera.getSink().grabFrame(inputImage) == 0) return;
		}

		List<MatOfPoint> contours = new ArrayList<>();

		Preferences prefs = Preferences.getInstance();

		Mat greenMask = new Mat();
		Scalar lowerThreshold = new Scalar(prefs.getInt("lower_b", 0),
										   prefs.getInt("lower_g", 20),
										   prefs.getInt("lower_r", 0));
		Scalar upperThreshold = new Scalar(prefs.getInt("upper_b", 75),
										   prefs.getInt("upper_g", 255),
										   prefs.getInt("upper_r", 20));
		Core.inRange(inputImage, lowerThreshold, upperThreshold, greenMask);
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

		synchronized (latestRects) {
			latestRects.clear();
			latestRects.addAll(rects);

			if (Robot.DEBUG) {
				Pair<Rect, Rect> blobs = getLargestBlobs();
				if (blobs != null) {
					Rect largest = blobs.first;
					Imgproc.rectangle(inputImage, largest.tl(), largest.br(), upperThreshold);
					Rect secondLargest = blobs.second;
					Imgproc.rectangle(inputImage,
									  secondLargest.tl(),
									  secondLargest.br(),
									  upperThreshold);
					camera.getSource().putFrame(inputImage);
				}
			}
		}

		inputImage.release();
		greenMask.release();
		hierarchy.release();
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
		synchronized (latestRects) {
			if (latestRects.size() < 2) return null;
			else return Pair.create(latestRects.get(latestRects.size() - 1),
									latestRects.get(latestRects.size() - 2));
		}
	}
}
