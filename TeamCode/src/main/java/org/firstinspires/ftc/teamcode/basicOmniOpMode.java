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
        Servo wrist = hardwareMap.get(Servo.class, "wrist");
        Servo leftClaw = hardwareMap.get(Servo.class, "leftClaw");
        Servo rightClaw = hardwareMap.get(Servo.class, "rightClaw");

        waitForStart();

        if (isStopRequested()) return;

        armRotation.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armRotation.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRotation.setTargetPosition(0);
        armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftClaw.setPosition(0.45);
        rightClaw.setPosition(0.55);

        while (opModeIsActive() && !isStopRequested()) {

            double y = gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 1.05;
            double rx = gamepad1.right_stick_x * 0.85;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            frontLeft.setPower(-frontLeftPower);
            backLeft.setPower(-backLeftPower);
            frontRight.setPower(frontRightPower);
            backRight.setPower(-backRightPower);

            if (gamepad1.a) {

                armRotation.setTargetPosition(0);

                armRotation.setPower(-0.4);

                armRotation.isBusy();

                armRotation.setPower(0);

            } else if (gamepad1.b) {

                armRotation.setTargetPosition(-100);

                armRotation.setPower(0.4);

                armRotation.isBusy();

                armRotation.setPower(0);

            }



            if (gamepad1.left_trigger > 0) {

                leftClaw.setPosition(0.45);

            }

            if (gamepad1.left_bumper) {

                leftClaw.setPosition(0);

            }

            if (gamepad1.right_trigger > 0) {

                rightClaw.setPosition(0.55);

            }

            if (gamepad1.right_bumper) {

                rightClaw.setPosition(1);

            }

            telemetry.addData("Arm:", armRotation.getCurrentPosition());
            telemetry.addData("leftClaw", leftClaw.getPosition());
            telemetry.addData("rightClaw", rightClaw.getPosition());
            telemetry.update();

        }

    }

}
