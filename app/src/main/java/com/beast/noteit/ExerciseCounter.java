package com.beast.noteit;

import android.content.Context;
import android.util.Log;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.pose.Pose;
import com.google.mlkit.vision.pose.PoseDetection;
import com.google.mlkit.vision.pose.PoseDetector;
import com.google.mlkit.vision.pose.PoseLandmark;
import com.google.mlkit.vision.pose.accurate.AccuratePoseDetectorOptions;

public class ExerciseCounter {

    private final PoseDetector poseDetector;
    private int pushupCount = 0;
    private int squatCount = 0;
    private int situpCount = 0;
    private boolean isPushupDown = false;
    private boolean isSquatDown = false;
    private boolean isSitupUp = false;

    public ExerciseCounter(Context context) {
        AccuratePoseDetectorOptions options =
                new AccuratePoseDetectorOptions.Builder()
                        .setDetectorMode(AccuratePoseDetectorOptions.STREAM_MODE)
                        .build();

        poseDetector = PoseDetection.getClient(options);
    }

    public void processImage(InputImage image) {
        poseDetector.process(image)
                .addOnSuccessListener(pose -> {
                    detectPose(pose);
                })
                .addOnFailureListener(e -> {
                    Log.e("ExerciseCounter", "Pose detection failed", e);
                });
    }

    private void detectPose(Pose pose) {
        PoseLandmark leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER);
        PoseLandmark rightShoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER);
        PoseLandmark leftHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP);
        PoseLandmark rightHip = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP);
        PoseLandmark leftKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE);
        PoseLandmark rightKnee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE);

        if (leftShoulder != null && rightShoulder != null && leftHip != null && rightHip != null && leftKnee != null && rightKnee != null) {
            countPushups(leftShoulder, rightShoulder, leftHip, rightHip);
            countSquats(leftHip, rightHip, leftKnee, rightKnee);
            countSitups(leftShoulder, rightShoulder, leftHip, rightHip);
        }
    }

    private void countPushups(PoseLandmark leftShoulder, PoseLandmark rightShoulder, PoseLandmark leftHip, PoseLandmark rightHip) {
        float shoulderY = (leftShoulder.getPosition().y + rightShoulder.getPosition().y) / 2;
        float hipY = (leftHip.getPosition().y + rightHip.getPosition().y) / 2;

        if (shoulderY > hipY && !isPushupDown) {
            isPushupDown = true;
        } else if (shoulderY < hipY && isPushupDown) {
            pushupCount++;
            isPushupDown = false;
            Log.d("ExerciseCounter", "Pushup count: " + pushupCount);
        }
    }

    private void countSquats(PoseLandmark leftHip, PoseLandmark rightHip, PoseLandmark leftKnee, PoseLandmark rightKnee) {
        float hipY = (leftHip.getPosition().y + rightHip.getPosition().y) / 2;
        float kneeY = (leftKnee.getPosition().y + rightKnee.getPosition().y) / 2;

        if (hipY > kneeY && !isSquatDown) {
            isSquatDown = true;
        } else if (hipY < kneeY && isSquatDown) {
            squatCount++;
            isSquatDown = false;
            Log.d("ExerciseCounter", "Squat count: " + squatCount);
        }
    }

    private void countSitups(PoseLandmark leftShoulder, PoseLandmark rightShoulder, PoseLandmark leftHip, PoseLandmark rightHip) {
        float shoulderY = (leftShoulder.getPosition().y + rightShoulder.getPosition().y) / 2;
        float hipY = (leftHip.getPosition().y + rightHip.getPosition().y) / 2;

        if (shoulderY < hipY && !isSitupUp) {
            isSitupUp = true;
        } else if (shoulderY > hipY && isSitupUp) {
            situpCount++;
            isSitupUp = false;
            Log.d("ExerciseCounter", "Situp count: " + situpCount);
        }
    }

    public int getPushupCount() {
        return pushupCount;
    }

    public int getSquatCount() {
        return squatCount;
    }

    public int getSitupCount() {
        return situpCount;
    }
}
