package org.firstinspires.ftc.teamcode.auto;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class autonomous extends functions {

    public final static ElapsedTime runtime = new ElapsedTime();

    Servo fishingArm = hardwareMap.get(Servo.class, "fishingArm");

    public void runOpMode() {

        //setup camera to look for samples
//        ColorBlobLocatorProcessor colorLocator = new ColorBlobLocatorProcessor.Builder()
//                .setTargetColorRange(ColorRange.BLUE)         // use a predefined color match
//                .setContourMode(ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY)    // exclude blobs inside blobs
//                .setRoi(ImageRegion.asUnityCenterCoordinates(-1, 1, 1, -1))  // search central 1/4 of camera view
//                .setDrawContours(true)                        // Show contours on the Stream Preview
//                .setBlurSize(5)                               // Smooth the transitions between different colors in image
//                .build();
//
//        VisionPortal portal = new VisionPortal.Builder()
//                .addProcessor(colorLocator)
//                .setCameraResolution(new Size(320, 240))
//                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
//                .build();

//        telemetry.setMsTransmissionInterval(50);   // Speed up telemetry updates, Just use for debugging.debugging
//        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);

        colorSetup();

        NormalizedRGBA colors = colorSensor.getNormalizedColors();

        //while (opModeInInit()) {

            //cameraTelemetry();

        //}

        waitForStart();

        runtime.startTime();

        while (opModeIsActive() && runtime.seconds() <= 26) {

            //fish and hang

            while (!found) {

                color();

                fishing();

                check();

            }



            //cameraTelemetry();
        }

        //drive to park

    }

}
