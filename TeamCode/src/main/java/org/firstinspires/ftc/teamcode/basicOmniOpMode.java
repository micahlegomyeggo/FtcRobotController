package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class basicOmniOpMode extends LinearOpMode {

    public void runOpMode() throws InterruptedException {

        DcMotor frontLeft = hardwareMap.dcMotor.get("frontLeft");
        DcMotor backLeft = hardwareMap.dcMotor.get("backLeft");
        DcMotor frontRight = hardwareMap.dcMotor.get("frontRight");
        DcMotor backRight = hardwareMap.dcMotor.get("backRight");
        DcMotor armRotation = hardwareMap.get(DcMotorEx.class, "armRotation");
        DcMotor armExtend = hardwareMap.get(DcMotorEx.class, "armExtend");
        Servo wrist = hardwareMap.get(Servo.class, "wrist");
        Servo intake = hardwareMap.get(Servo.class, "intake");

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

                armRotation.setTargetPosition(10);
                armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                if (armRotation.getTargetPosition() > armRotation.getCurrentPosition()) {
                    armRotation.setPower(0.08);
                    wrist.setPosition(1);
                    if (armRotation.getCurrentPosition() >= armRotation.getTargetPosition()) {
                        armRotation.setPower(0);
                    }
                } else if (armRotation.getTargetPosition() < armRotation.getCurrentPosition()) {
                    armRotation.setPower(-0.08);
                    wrist.setPosition(1);
                    if (armRotation.getCurrentPosition() <= armRotation.getTargetPosition()) {
                        armRotation.setPower(0);
                    }
                }

            }

            if (gamepad2.b) {

                armRotation.setTargetPosition(100);
                armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                if (armRotation.getTargetPosition() > armRotation.getCurrentPosition()) {
                    armRotation.setPower(0.08);
                    wrist.setPosition(0.9);
                    if (armRotation.getCurrentPosition() >= armRotation.getTargetPosition()) {
                        armRotation.setPower(0);
                    }
                } else if (armRotation.getTargetPosition() < armRotation.getCurrentPosition()) {
                    armRotation.setPower(-0.08);
                    wrist.setPosition(0.9);
                    if (armRotation.getCurrentPosition() <= armRotation.getTargetPosition()) {
                        armRotation.setPower(0);
                    }
                }
            }

            if (gamepad2.y){

                armRotation.setTargetPosition(200);
                armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                if (armRotation.getTargetPosition() > armRotation.getCurrentPosition()) {
                    armRotation.setPower(0.08);
                    wrist.setPosition(0.8);
                    if (armRotation.getCurrentPosition() >= armRotation.getTargetPosition()) {
                        armRotation.setPower(0);
                    }
                } else if (armRotation.getTargetPosition() < armRotation.getCurrentPosition()) {
                    armRotation.setPower(-0.08);
                    wrist.setPosition(0.8);
                    if (armRotation.getCurrentPosition() <= armRotation.getTargetPosition()) {
                        armRotation.setPower(0);
                    }
                }
            }

            if (gamepad2.x){

                armRotation.setTargetPosition(600);
                armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                if (armRotation.getTargetPosition() > armRotation.getCurrentPosition()) {
                    armRotation.setPower(0.08);
                    wrist.setPosition(0.2);
                    if (armRotation.getCurrentPosition() >= armRotation.getTargetPosition()) {
                        armRotation.setPower(0);
                    }
                } else if (armRotation.getTargetPosition() < armRotation.getCurrentPosition()) {
                    armRotation.setPower(-0.08);
                    wrist.setPosition(0.2);
                    if (armRotation.getCurrentPosition() <= armRotation.getTargetPosition()) {
                        armRotation.setPower(0);
                    }
                }
            }

            if (gamepad2.right_trigger > 0) {
                wrist.setPosition(0.7);
            }

            if (gamepad2.left_trigger > 0 ) {
                wrist.setPosition(0.9);
            }

            armExtend.setPower(-gamepad2.left_stick_y);

            if (gamepad2.right_stick_y > 0.1) {
                intake.setPosition(1);
            } else if (gamepad2.right_stick_y < -0.1) {
                intake.setPosition(0);
            } else {
                intake.setPosition(0.5);
            }

            telemetry.addData("Arm angle:", armRotation.getCurrentPosition());
            telemetry.addData("Arm Extension", -armExtend.getCurrentPosition());
            telemetry.addData("Wrist Angle", wrist.getPosition());

            telemetry.update();

        }

    }

}
