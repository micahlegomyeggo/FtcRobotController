package org.firstinspires.ftc.teamcode.auto;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.SortOrder;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor;
import org.firstinspires.ftc.vision.opencv.ColorRange;
import org.firstinspires.ftc.vision.opencv.ImageRegion;
import org.opencv.core.RotatedRect;

import java.util.List;

public abstract class functions extends LinearOpMode {

    public final static ElapsedTime fishingTime = new ElapsedTime();

    Servo fishingArm = hardwareMap.get(Servo.class, "fishingArm");

    //DcMotor fishingArm;

    public int fishPower = 1;
//    public boolean possession = false;
    public boolean found = false;
//    public boolean grabbed = false;
//    public boolean clipped = false;
//    public boolean hanged = false;



    NormalizedColorSensor colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");

//    ColorBlobLocatorProcessor colorLocator;
//
//    @SuppressLint("DefaultLocale")
//    public void cameraTelemetry() {
//
//        telemetry.addData("preview on/off", "... Camera Stream\n");
//        try {
//            List<ColorBlobLocatorProcessor.Blob> blobs = colorLocator.getBlobs();
//
//            ColorBlobLocatorProcessor.Util.filterByArea(50, 20000, blobs);  // filter out very small blobs.
//
//            ColorBlobLocatorProcessor.Util.sortByDensity(SortOrder.DESCENDING, blobs);
//
//            telemetry.addLine(" Area Density Aspect  Center");
//
//            // Display the size (area) and center location for each Blob.
//            for(ColorBlobLocatorProcessor.Blob b : blobs)
//            {
//                RotatedRect boxFit = b.getBoxFit();
//                telemetry.addLine(String.format("%5d  %4.2f   %5.2f  (%3d,%3d)",
//                        b.getContourArea(), b.getDensity(), b.getAspectRatio(), (int) boxFit.center.x, (int) boxFit.center.y));
//            }
//
//            telemetry.update();
//            sleep(50);
//
//        } catch (NullPointerException e) {
//            telemetry.addLine("No Blobs Seen");
//            telemetry.update();
//        }
//
//    }

    public void colorSetup() {

        float gain = 2;

        final float[] hsvValues = new float[3];



        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight) colorSensor).enableLight(true);
        }

    }

    public void color() {

        //float[] hsvValues;

        NormalizedRGBA colors = colorSensor.getNormalizedColors();

        //Color.colorToHSV(colors.toColor(), hsvValues);

        telemetry.addLine()
                .addData("Red", "%.3f", colors.red)
                .addData("Green", "%.3f", colors.green)
                .addData("Blue", "%.3f", colors.blue);
//        telemetry.addLine()
//                .addData("Hue", "%.3f", hsvValues[0])
//                .addData("Saturation", "%.3f", hsvValues[1])
//                .addData("Value", "%.3f", hsvValues[2]);
        telemetry.addData("Alpha", "%.3f", colors.alpha);

        if (colorSensor instanceof DistanceSensor) {
            telemetry.addData("Distance (cm)", "%.3f", ((DistanceSensor) colorSensor).getDistance(DistanceUnit.CM));
        }

        telemetry.update();

    }

    public void check() {

        NormalizedRGBA colors = colorSensor.getNormalizedColors();

        if (colors.blue > colors.red && colors.blue > colors.green) {
            found = true;
            telemetry.addLine("Blue Found");
            telemetry.update();
        }

    }

    public void fishing() {

        fishingTime.reset();

        while (fishingTime.seconds() < 3) {
            fishingArm.setPosition(fishPower);
        }

        if (fishPower == 1) {
            fishPower = 0;
        }
        if (fishPower == 0) {
            fishPower = 1;
        }

    }

}
