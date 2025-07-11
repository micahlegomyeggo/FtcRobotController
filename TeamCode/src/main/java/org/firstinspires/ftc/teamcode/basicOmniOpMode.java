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

        armExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armRotation.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRotation.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armExtend.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        hang.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hang.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        boolean hangY = true;
        boolean driveMode = true;
        boolean manualFishMode = false;
        boolean found = false;

        double wrist2Pos = 0.25;

        double rPower;
        double rPos;
        double rLastError = 0;
        double rError;
        double rTarget = 0; //505

        double p = 0.00675; // 0.005
        double i = 0.00013; // 0.000125
        double d = 0.0125; // 0.011

        double integral = 0;

        double derivative;

        while (opModeIsActive() && !isStopRequested()) {
            rotation.setPosition(1);
            wrist3.setPosition(0.99);

            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x; // *0.85
            double rx = gamepad1.right_stick_x; // *0.85
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

//            if (gamepad1.a && driveMode) {
//                driveMode = false;
//                sleep(250);
//            }
//            if (gamepad1.a && !driveMode) {
//                driveMode = true;
//                sleep(250);
//            }
            if (driveMode) {
                frontLeft.setPower(frontLeftPower);// * 0.75);  // change back to 75 later for ben
                backLeft.setPower(backLeftPower);// * 0.75);
                frontRight.setPower(frontRightPower);// * 0.75);
                backRight.setPower(backRightPower);// * 0.75);
            }
//            if (!driveMode) {
//                frontLeft.setPower(frontLeftPower * 0.35); // slow mode
//                backLeft.setPower(backLeftPower * 0.35);
//                frontRight.setPower(frontRightPower * 0.35);
//                backRight.setPower(backRightPower * 0.35);
//            }

            if (gamepad2.right_bumper) {
                hang.setTargetPosition(6900);
                hang.setPower(1);
                hang.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            if (gamepad2.left_bumper) {
                hang.setTargetPosition(1300); //1800 100% works
                hang.setPower(1);
                hang.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            if (!manualFishMode) {
                if (gamepad2.a) {
//                    i = 0.000125;
//                    d = 0.015;
                    rTarget = 10;
                    /*armRotation.setPower(0.4);
                    armRotation.setTargetPosition(10);
                    armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);*/
                }
                if (gamepad2.b) {
//                    i = 0.000125;
//                    d = 0.015;
                    rTarget = 118; //115
                    /*armRotation.setPower(0.4);
                    armRotation.setTargetPosition(101);
                    armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);*/
                }
                if (gamepad2.y && hangY) {
//                    i = 0.000125;
//                    d = 0.015;
                    rTarget = 380;
                    /*armRotation.setPower(0.4);
                    armRotation.setTargetPosition(380);
                    armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);*/
                    hangY = false;
                    sleep(250);
                }
                if (gamepad2.y && !hangY) {
//                    i = 0.000125;
//                    d = 0.015;
                    rTarget = 280;
                    /*armRotation.setPower(0.4);
                    armRotation.setTargetPosition(280);
                    armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);*/
                    hangY = true;
                    sleep(250);
                }
                if (gamepad2.x) {
//                    i = 0.003; //0.001
//                    d = 0.015; //0.011
                    rTarget = 530; //524
                    /*armRotation.setPower(0.5);
                    armRotation.setTargetPosition(505);
                    armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);*/
                }

                rPos = armRotation.getCurrentPosition();
                rError = rTarget - rPos;
                integral += rError;
                derivative = rError - rLastError;
                rPower = (p * rError) + (i * integral) + (d * derivative);
                if (rPower > 1) {rPower=1;}
                if (rPower < -1) {rPower=-1;}
                armRotation.setPower(rPower);
                rLastError = rError;

                telemetry.addData("Power:", rPower);
                telemetry.addData("Pos:", rPos);
                telemetry.addData("Error:", rError);
                telemetry.addData("Integral:", integral);
                telemetry.addData("Derivative:", derivative);

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

//                if (gamepad2.dpad_right) {
//                    rotation.setPosition(0);
//                }
//                if (gamepad2.dpad_right) {
//                    rotation.setPosition(1);
//                }

                armExtend.setPower(-gamepad2.left_stick_y * 0.9);

                if (armRotation.getCurrentPosition() < 200 & armExtend.getCurrentPosition() > 1000) {
                    wrist.setPosition(1);
                }

                if (gamepad2.right_stick_y > 0.3) {
                    fishingArm.setPosition(0.85); //0.65
                }else if (gamepad2.right_stick_y < -0.3) {
                    fishingArm.setPosition(0.15); //0.35
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
                    wrist2Pos += 0.1; // wrist2
                    sleep(100);
                }
                if (gamepad2.dpad_left) {
                    wrist2Pos -= 0.1;
                    sleep(100);
                }
                if (wrist2Pos >= 1) {
                    wrist2Pos = 1;
                }
                if (wrist2Pos <= 0) {
                    wrist2Pos = 0;
                }
                if (gamepad2.x) {
                    wrist2Pos = 0.25;
//                    wrist1.setPosition(0.85);
                }
                wrist2.setPosition(wrist2Pos);

//                if (gamepad2.b) {
//                    wrist3.setPosition(0.99);
//                }
//                if (gamepad2.a) {
//                    wrist3.setPosition(0.25);
//                }
                if (gamepad2.left_trigger > 0.1) {
                    fishingClaw.setPosition(0.5);
                }
                if (gamepad2.right_trigger > 0.1) {
                    fishingClaw.setPosition(1);
                }
                if (gamepad2.left_stick_y > 0.3) {
                    fishingArm.setPosition(0.95); //0.65
                }else if (gamepad2.left_stick_y < -0.3) {
                    fishingArm.setPosition(0.05); //0.35
                } else {
                    fishingArm.setPosition(0.5);
                }
                if (gamepad2.back) {
//                    wrist1.setPosition(0.03);
//                    fishingClaw.setPosition(1);
                    manualFishMode = false;
                    sleep(250);
                }
            }

            telemetry.addData("hang pos", hang.getCurrentPosition());
            telemetry.addData("Arm angle", armRotation.getCurrentPosition());
            telemetry.addData("Arm Extension", -armExtend.getCurrentPosition());
            telemetry.addData("Wrist Angle", wrist.getPosition());
            telemetry.addData("Wrist3", wrist3.getPosition());
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