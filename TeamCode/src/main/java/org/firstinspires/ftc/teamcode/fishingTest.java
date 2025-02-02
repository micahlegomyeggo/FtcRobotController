package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.DistanceSensor;

@Disabled
@TeleOp
public class fishingTest extends LinearOpMode {

    public int count = 0;

    public void runOpMode() throws InterruptedException {

        Servo fishingArm = hardwareMap.get(Servo.class, "fishingExtend");
        Servo wrist1 = hardwareMap.get(Servo.class, "wrist1");
        Servo wrist2 = hardwareMap.get(Servo.class, "wrist2");
        Servo wrist3 = hardwareMap.get(Servo.class, "wrist3");
        Servo fishingClaw = hardwareMap.get(Servo.class, "fishingClaw");

        NormalizedColorSensor colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");

        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight)colorSensor).enableLight(true);
        }

        boolean driveMode = true;
        boolean found = false;

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive() && !isStopRequested()) {

            while (!isStopRequested()) {

                if (gamepad1.dpad_up) {
                    wrist1.setPosition(0.85);
                }
                if (gamepad1.dpad_down) {
                    wrist1.setPosition(0.03);
                }
                if (gamepad1.dpad_right) {
                    wrist2.setPosition(1); // wrist2
                }
                if (gamepad1.dpad_left) {
                    wrist2.setPosition(0.2);
                }
                if (gamepad1.b) {
                    wrist3.setPosition(0.99);
                }
                if (gamepad1.a) {
                    wrist3.setPosition(0.25);
                }
                if (gamepad1.left_trigger > 0.1) {
                    fishingClaw.setPosition(0.5);
                }
                if (gamepad1.right_trigger > 0.1) {
                    fishingClaw.setPosition(1);
                }
                if (gamepad1.left_stick_y > 0.3) {
                    fishingArm.setPosition(0.65);
                }else if (gamepad1.left_stick_y < -0.3) {
                    fishingArm.setPosition(0.35);
                } else {
                    fishingArm.setPosition(0.5);
                }

                if (gamepad1.x) {

                    double angle = 1;
//                    found = false;

                    NormalizedRGBA colors = colorSensor.getNormalizedColors();

                    wrist1.setPosition(0.85);

                    wrist2.setPosition(1);
                    wrist3.setPosition(0.99);
                    fishingClaw.setPosition(0.5);

                    sleep(250);

                    while (!gamepad1.x) {
                        fishingArm.setPosition(0.425);
                        telemetry.addData("Waiting for response", "");
                    }

                    fishingArm.setPosition(0.5);

                    wrist1.setPosition(0.15);
                    fishingClaw.setPosition(0.5);

                    sleep(400);

                    while (!found && !isStopRequested() && count <= 925) {//|| fishingArm.getPosition() >= 40) { // change # to actual max distance

                        fishingArm.setPosition(0.38);

                        colors = colorSensor.getNormalizedColors();

                        count += 1;

                        telemetry.addData("Extend Pos", fishingArm.getPosition());
                        telemetry.addData("count", count);
                        telemetry.update();

                        if (colors.blue > 0.032 && colorSensor instanceof DistanceSensor && ((DistanceSensor) colorSensor).getDistance(DistanceUnit.CM) < 2) {
                            sleep(400);
                            found = true;
                            fishingArm.setPosition(0.5);
                            break;
                        }
                    }

                    fishingArm.setPosition(0.5);

//                    while (!isStopRequested()) {
//                        colors = colorSensor.getNormalizedColors();
//                        wrist2.setPosition(angle);
//                        if (angle > 0.2) {
//                            angle -= 0.05;
//                        }
//                        if (!(colors.blue > colors.red) && !(colors.blue > colors.green)) {
//                            wrist2.setPosition(wrist2.getPosition() - 0.2);
//                            fishingClaw.setPosition(1);
//                            break;
//                        }
//                    }

                    fishingClaw.setPosition(1);
                    sleep(250);
                    wrist2.setPosition(1);
                    wrist1.setPosition(0.85);
                    sleep(250);
                    wrist3.setPosition(0.25);

//                    while (count > 0 && !isStopRequested()) {
//                        fishingArm.setPosition(0.65);
//                        count -= 1;
//                        telemetry.addData("count", count);
//                        telemetry.update();
//                    }

//                    fishingClaw.setPosition(0.5);

                }

                telemetry.addData("Mode:", "Manual");

                telemetry.addData("Extend Pos", fishingArm.getPosition());

                NormalizedRGBA colors = colorSensor.getNormalizedColors();

                telemetry.addLine()
                        .addData("Red", "%.3f", colors.red)
                        .addData("Green", "%.3f", colors.green)
                        .addData("Blue", "%.3f", colors.blue);
                telemetry.addData("Alpha", "%.3f", colors.alpha);

                if (colorSensor instanceof DistanceSensor) {
                    telemetry.addData("Distance (cm)", "%.3f", ((DistanceSensor) colorSensor).getDistance(DistanceUnit.CM));
                }

                if (colors.blue > colors.red && colors.blue > colors.green) {
                    telemetry.addData("Blue Found", "");
                }
                telemetry.update();

            }
        }
    }
}

// this is the final auto fish from the opMode

//                if (gamepad2.start && !isStopRequested()) {
//
//                    double angle = 1;
//
//                    NormalizedRGBA colors = colorSensor.getNormalizedColors();
//
//                    wrist1.setPosition(0.85);
//
//                    wrist2.setPosition(1);
//                    wrist3.setPosition(0.99);
//                    fishingClaw.setPosition(0.5);
//
//                    sleep(250);
//
//                    while (!gamepad2.start) {
//                        fishingArm.setPosition(0.41);
//                        telemetry.addData("Waiting for response", "");
//                    }
//
//                    fishingArm.setPosition(0.5);
//
//                    wrist1.setPosition(0.15);
//                    fishingClaw.setPosition(0.5);
//
//                    sleep(500);
//
//                    while (!found && !isStopRequested() && count <= 925) {//|| fishingArm.getPosition() >= 40) { // change # to actual max distance
//
//                        fishingArm.setPosition(0.39);
//
//                        colors = colorSensor.getNormalizedColors();
//
//                        count += 1;
//
//                        telemetry.addData("Extend Pos", fishingArm.getPosition());
//                        telemetry.addData("count", count);
//                        telemetry.addLine()
//                                .addData("Red", "%.3f", colors.red)
//                                .addData("Green", "%.3f", colors.green)
//                                .addData("Blue", "%.3f", colors.blue);
//                        telemetry.addData("Alpha", "%.3f", colors.alpha);
//                        telemetry.update();
//
//                        if (colors.blue > 0.015 && colorSensor instanceof DistanceSensor && ((DistanceSensor) colorSensor).getDistance(DistanceUnit.CM) < 2) {
//                            sleep(200);
//                            found = true;
//                            fishingArm.setPosition(0.5);
//                            break;
//                        }
//                    }
//
//                    fishingArm.setPosition(0.5);
//
//                    fishingClaw.setPosition(1);
//                    sleep(250);
//                    wrist2.setPosition(1);
//                    wrist1.setPosition(0.85);
//                    sleep(250);
//                    wrist3.setPosition(0.25);
//
//                }
