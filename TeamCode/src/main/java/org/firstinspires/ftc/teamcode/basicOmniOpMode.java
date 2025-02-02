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

    public int count = 0;

    int bPos = 110;

    public void runOpMode() throws InterruptedException {

        DcMotor frontLeft = hardwareMap.dcMotor.get("leftFront");
        DcMotor backLeft = hardwareMap.dcMotor.get("leftBack");
        DcMotor frontRight = hardwareMap.dcMotor.get("rightFront");
        DcMotor backRight = hardwareMap.dcMotor.get("rightBack");
        DcMotor armRotation = hardwareMap.get(DcMotorEx.class, "armRotation");
        DcMotor armExtend = hardwareMap.get(DcMotorEx.class, "armExtend");
        DcMotor hang = hardwareMap.get(DcMotorEx.class, "hang");
        Servo wrist = hardwareMap.get(Servo.class, "wrist");
        Servo claw = hardwareMap.get(Servo.class, "claw");
        Servo fishingArm = hardwareMap.get(Servo.class, "fishingExtend");
        Servo rotation = hardwareMap.get(Servo.class, "rotation");
        Servo wrist1 = hardwareMap.get(Servo.class, "wrist1");
        Servo wrist2 = hardwareMap.get(Servo.class, "wrist2");
        Servo wrist3 = hardwareMap.get(Servo.class, "wrist3");
        Servo fishingClaw = hardwareMap.get(Servo.class, "fishingClaw");

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

        boolean hangY = true;
        boolean driveMode = true;
        boolean manualFishMode = false;
        boolean found = false;

        while (opModeIsActive() && !isStopRequested()) {

            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 0.85;
            double rx = gamepad1.right_stick_x * 0.85;
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            if (gamepad1.a && driveMode) {
                driveMode = false;
                sleep(250);
            }
            if (gamepad1.a && !driveMode) {
                driveMode = true;
                sleep(250);
            }
            if (driveMode) {
                frontLeft.setPower(frontLeftPower);// * 0.75);  // change back to 75 later for ben
                backLeft.setPower(backLeftPower);// * 0.75);
                frontRight.setPower(frontRightPower);// * 0.75);
                backRight.setPower(backRightPower);// * 0.75);
            }
            if (!driveMode) {
                frontLeft.setPower(frontLeftPower * 0.35); // slow mode
                backLeft.setPower(backLeftPower * 0.35);
                frontRight.setPower(frontRightPower * 0.35);
                backRight.setPower(backRightPower * 0.35);
            }

            if (!manualFishMode) {
                if (gamepad2.a) {
                    armRotation.setPower(0.4);
                    armRotation.setTargetPosition(10);
                    armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
                if (gamepad2.b) {
                    armRotation.setPower(0.4);
                    armRotation.setTargetPosition(bPos);
                    armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
                if (gamepad2.y && hangY) {
                    armRotation.setPower(0.4);
                    armRotation.setTargetPosition(380);
                    armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    hangY = false;
                    sleep(250);
                }
                if (gamepad2.y && !hangY) {
                    armRotation.setPower(0.4);
                    armRotation.setTargetPosition(280);
                    armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    hangY = true;
                    sleep(250);
                }
                if (gamepad2.x) {
                    armRotation.setPower(0.5);
                    armRotation.setTargetPosition(510);
                    armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
                if (gamepad2.right_trigger > 0) {
                    claw.setPosition(1);
                }
                if (gamepad2.left_trigger > 0) {
                    claw.setPosition(0.7);
                }

                if (gamepad2.dpad_down) {
                    wrist.setPosition(1);
                }
                if (gamepad2.dpad_up) {
                    wrist.setPosition(0.75);
                }

                if (gamepad2.dpad_right) {
                    rotation.setPosition(0);
                }
                if (gamepad2.dpad_left) {
                    rotation.setPosition(1);
                }
                armExtend.setPower(-gamepad2.left_stick_y * 0.75);
                if (gamepad2.left_bumper && gamepad2.right_bumper) {
                    hang.setTargetPosition(1650);
                    hang.setPower(1);
                    hang.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
                if (gamepad2.right_bumper) {
                    hang.setTargetPosition(6900);
                    hang.setPower(1);
                    hang.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
                if (gamepad2.left_bumper) {
                    hang.setTargetPosition(100);
                    hang.setPower(1);
                    hang.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
                if (gamepad2.right_stick_y > 0.3) {
                    fishingArm.setPosition(0.65);
                }else if (gamepad2.right_stick_y < -0.3) {
                    fishingArm.setPosition(0.35);
                } else {
                    fishingArm.setPosition(0.5);
                }
                if (gamepad2.back) {
                    manualFishMode = true;
                    sleep(250);
                }
            }

            if (manualFishMode) {
                if (gamepad2.dpad_up) {
                    wrist1.setPosition(0.85);
                }
                if (gamepad2.dpad_down) {
                    wrist1.setPosition(0.03);
                }
                if (gamepad2.dpad_right) {
                    wrist2.setPosition(1); // wrist2
                }
                if (gamepad2.dpad_left) {
                    wrist2.setPosition(0.2);
                }
                if (gamepad2.b) {
                    wrist3.setPosition(0.99);
                }
                if (gamepad2.a) {
                    wrist3.setPosition(0.25);
                }
                if (gamepad2.left_trigger > 0.1) {
                    fishingClaw.setPosition(0.5);
                }
                if (gamepad2.right_trigger > 0.1) {
                    fishingClaw.setPosition(1);
                }
                if (gamepad2.left_stick_y > 0.3) {
                    fishingArm.setPosition(0.65);
                }else if (gamepad2.left_stick_y < -0.3) {
                    fishingArm.setPosition(0.35);
                } else {
                    fishingArm.setPosition(0.5);
                }
                if (gamepad2.back) {
                    manualFishMode = false;
                    sleep(250);
                }
            }

            telemetry.addData("hang pos", hang.getCurrentPosition());
            telemetry.addData("Arm angle", armRotation.getCurrentPosition());
            telemetry.addData("Arm Extension", -armExtend.getCurrentPosition());
            telemetry.addData("Wrist Angle", wrist.getPosition());
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
                telemetry.addLine("Blue Found");
            }
            telemetry.update();
        }
        hang.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }
}

// controls for me

// normal mode:
// left stick y - hang arm extend
// a - hang arm normal
// b - hang arm grab
// y - 2 mode toggle clip hang
// x - bucket hang arm
// dpad up - hang arm wrist up
// dpad down - hang arm wrist down
// dpad left - hang arm wrist in
// dpad right - hang arm wrist out
// left trigger - hang arm claw open
// right trigger - hang arm claw close
// left bumper - hang mechanism down
// right bumper - hang mechanism up
// back - switch modes

// fishing mode:
// left stick y - fishing arm extend
// dpad up - wrist up
// dpad down - wrist down
// dpad left - wrist left
// dpad right - wrist right
// y - wrist rotation max
// x - wrist rotation normal
// left trigger - claw open
// right trigger - claw close
// back - switch modes