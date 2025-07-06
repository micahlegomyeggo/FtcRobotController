package org.firstinspires.ftc.teamcode.auto;


import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.SortOrder;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.autoL;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor;
import org.firstinspires.ftc.vision.opencv.ColorRange;
import org.firstinspires.ftc.vision.opencv.ImageRegion;
import org.opencv.core.RotatedRect;

import java.util.List;
@Disabled
@Autonomous
public class fishing extends autoL {

    //this program is preliminary code for a two arm auto

    public boolean possession = false;
    public boolean found = false;
    public boolean grabbed = false;

    ColorBlobLocatorProcessor colorLocator;

    public void cameraTelemetry() {

        telemetry.addData("preview on/off", "... Camera Stream\n");

        List<ColorBlobLocatorProcessor.Blob> blobs = colorLocator.getBlobs();

        ColorBlobLocatorProcessor.Util.filterByArea(50, 20000, blobs);  // filter out very small blobs.

        ColorBlobLocatorProcessor.Util.sortByDensity(SortOrder.DESCENDING, blobs);

        telemetry.addLine(" Area Density Aspect  Center");

        // Display the size (area) and center location for each Blob.
        for(ColorBlobLocatorProcessor.Blob b : blobs)
        {
            RotatedRect boxFit = b.getBoxFit();
            telemetry.addLine(String.format("%5d  %4.2f   %5.2f  (%3d,%3d)",
                    b.getContourArea(), b.getDensity(), b.getAspectRatio(), (int) boxFit.center.x, (int) boxFit.center.y));
        }

        telemetry.update();
        sleep(50);

    }

    public void scan() {

    }

    public void grab() {
        //grab sample

        //use color sensor to check if right color sample
    }

    public void runOpMode() {

        //setup camera to look for samples
        ColorBlobLocatorProcessor colorLocator = new ColorBlobLocatorProcessor.Builder()
                .setTargetColorRange(ColorRange.BLUE)         // use a predefined color match
                .setContourMode(ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY)    // exclude blobs inside blobs
                .setRoi(ImageRegion.asUnityCenterCoordinates(-1, 1, 1, -1))  // search central 1/4 of camera view
                .setDrawContours(true)                        // Show contours on the Stream Preview
                .setBlurSize(5)                               // Smooth the transitions between different colors in image
                .build();

        VisionPortal portal = new VisionPortal.Builder()
                .addProcessor(colorLocator)
                .setCameraResolution(new Size(320, 240))
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .build();

        telemetry.setMsTransmissionInterval(50);   // Speed up telemetry updates, Just use for debugging.
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);

        //setup

        //camera looks for clumps of blue or red samples for highest chance of getting one on first try

        //if biggest clump is on the other half go to next biggest clump

        waitForStart();

        while (opModeInInit()) {

            cameraTelemetry();

        }

        while (opModeIsActive()) {

            //main loop

            //camera looks for clumps of blue or red samples for highest chance of getting one first try

            //extend arm once sample has been removed

            //color sensor looks for blue or red sample below grabber

            //if one isn't directly below keep looping (while)

            //grab sample once confirmed and robot does not have another

            //return arm

            //notify hanging

            //immediately start looking for another sample

            cameraTelemetry();

        }

    }

}