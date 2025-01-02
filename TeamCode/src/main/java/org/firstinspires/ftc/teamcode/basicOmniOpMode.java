package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp
public class basicOmniOpMode extends LinearOpMode {

    public void runOpMode() throws InterruptedException {

        DcMotor frontLeft = hardwareMap.dcMotor.get("leftFront");
        DcMotor backLeft = hardwareMap.dcMotor.get("leftBack");
        DcMotor frontRight = hardwareMap.dcMotor.get("rightFront");
        DcMotor backRight = hardwareMap.dcMotor.get("rightBack");
        DcMotor armRotation = hardwareMap.get(DcMotorEx.class, "armRotation");
        DcMotor armExtend = hardwareMap.get(DcMotorEx.class, "armExtend");
        Servo wrist = hardwareMap.get(Servo.class, "wrist");
        Servo claw = hardwareMap.get(Servo.class, "claw");
        Servo fishingArm = hardwareMap.get(Servo.class, "fishingArm");
        Servo rotation = hardwareMap.get(Servo.class, "rotation");

        NormalizedColorSensor colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");

        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight)colorSensor).enableLight(true);
        }

        waitForStart();

        if (isStopRequested()) return;

        armRotation.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        armRotation.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armExtend.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (opModeIsActive() && !isStopRequested()) {

            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 0.85;
            double rx = gamepad1.right_stick_x * 0.85;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            frontLeft.setPower(frontLeftPower);
            backLeft.setPower(backLeftPower);
            frontRight.setPower(frontRightPower);
            backRight.setPower(backRightPower);

            if (gamepad2.a) {

                armRotation.setTargetPosition(-10);
                armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                if (armRotation.getTargetPosition() > armRotation.getCurrentPosition()) {
                    armRotation.setPower(0.25);
                    //wrist.setPosition(1);
                    if (armRotation.getCurrentPosition() >= armRotation.getTargetPosition()) {
                        armRotation.setPower(0);
                    }
                } else if (armRotation.getTargetPosition() < armRotation.getCurrentPosition()) {
                    armRotation.setPower(-0.25);
                    //wrist.setPosition(1);
                    if (armRotation.getCurrentPosition() <= armRotation.getTargetPosition()) {
                        armRotation.setPower(0);
                    }
                }

            }

            if (gamepad2.b) {

                armRotation.setTargetPosition(-100);
                armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                if (armRotation.getTargetPosition() > armRotation.getCurrentPosition()) {
                    armRotation.setPower(0.25);
                    //wrist.setPosition(0.9);
                    if (armRotation.getCurrentPosition() >= armRotation.getTargetPosition()) {
                        armRotation.setPower(0);
                    }
                } else if (armRotation.getTargetPosition() < armRotation.getCurrentPosition()) {
                    armRotation.setPower(-0.25);
                    //wrist.setPosition(0.9);
                    if (armRotation.getCurrentPosition() <= armRotation.getTargetPosition()) {
                        armRotation.setPower(0);
                    }
                }
            }

            if (gamepad2.y){

                armRotation.setTargetPosition(-340);
                armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                if (armRotation.getTargetPosition() > armRotation.getCurrentPosition()) {
                    armRotation.setPower(0.25);
                    //wrist.setPosition(0.8);
                    if (armRotation.getCurrentPosition() >= armRotation.getTargetPosition()) {
                        armRotation.setPower(0);
                    }
                } else if (armRotation.getTargetPosition() < armRotation.getCurrentPosition()) {
                    armRotation.setPower(-0.25);
                    //wrist.setPosition(0.8);
                    if (armRotation.getCurrentPosition() <= armRotation.getTargetPosition()) {
                        armRotation.setPower(0);
                    }
                }
            }

            if (gamepad2.x){

                armRotation.setTargetPosition(-510);
                armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                if (armRotation.getTargetPosition() > armRotation.getCurrentPosition()) {
                    armRotation.setPower(0.25);
                    //wrist.setPosition(0.2);
                    if (armRotation.getCurrentPosition() >= armRotation.getTargetPosition()) {
                        armRotation.setPower(0);
                    }
                } else if (armRotation.getTargetPosition() < armRotation.getCurrentPosition()) {
                    armRotation.setPower(-0.25);
                    //wrist.setPosition(0.2);
                    if (armRotation.getCurrentPosition() <= armRotation.getTargetPosition()) {
                        armRotation.setPower(0);
                    }
                }
            }

            if (gamepad2.right_trigger > 0) {
                claw.setPosition(0.75);
            }

            if (gamepad2.left_trigger > 0 ) {
                claw.setPosition(1);
            }

            if (gamepad2.dpad_down) {
                wrist.setPosition(1);
            }
            if (gamepad2.dpad_up) {
                wrist.setPosition(0.35);
            }
//            if (gamepad2.dpad_left) {
//                rotation.setPosition(0);
//            }
//            if (gamepad2.dpad_right) {
//                rotation.setPosition(0.5);
//            }

            armExtend.setPower(-gamepad2.left_stick_y * 0.5);

            if (gamepad1.left_trigger > 0.1) {
                fishingArm.setPosition(1);
            } else if (gamepad1.right_trigger > 0.1) {
                fishingArm.setPosition(0);
            } else {
                fishingArm.setPosition(0.5);
            }

            NormalizedRGBA colors = colorSensor.getNormalizedColors();

            telemetry.addData("Arm angle", armRotation.getCurrentPosition());
            telemetry.addData("Arm Extension", -armExtend.getCurrentPosition());
            //telemetry.addData("Wrist Angle", wrist.getPosition());

            telemetry.addLine()
                    .addData("Red", "%.3f", colors.red)
                    .addData("Green", "%.3f", colors.green)
                    .addData("Blue", "%.3f", colors.blue);
            telemetry.addData("Alpha", "%.3f", colors.alpha);

            if (colorSensor instanceof DistanceSensor) {
                telemetry.addData("Distance (cm)", "%.3f", ((DistanceSensor) colorSensor).getDistance(DistanceUnit.CM));
            }

            if (colors.blue > colors.red && colors.blue > colors.green) {
                telemetry.addLine("Blue Found");
            }

            telemetry.update();

        }

    }

}
