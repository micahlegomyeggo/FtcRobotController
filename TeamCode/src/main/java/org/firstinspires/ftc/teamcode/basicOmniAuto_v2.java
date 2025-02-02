package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
@Disabled
@Autonomous
public class basicOmniAuto_v2 extends LinearOpMode {

    DcMotor frontLeft;
    DcMotor backLeft;
    DcMotor frontRight;
    DcMotor backRight;
    DcMotor armRotation;
    DcMotor armExtend;

//    public void drive(double flPower, double blPower, double frPower, double brPower) {
//
//        frontLeft.setPower(flPower);
//        backLeft.setPower(blPower);
//        frontRight.setPower(frPower);
//        backRight.setPower(brPower);
//
//    }

    public void runOpMode() throws InterruptedException {

        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        armRotation = hardwareMap.dcMotor.get("armRotation");
        armExtend = hardwareMap.dcMotor.get("armExtend");
        Servo wrist = hardwareMap.get(Servo.class, "wrist");
        Servo intake = hardwareMap.get(Servo.class, "intake");

        waitForStart();

        if (isStopRequested()) return;

        armRotation.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        armRotation.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //armExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armExtend.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        if (opModeIsActive() && !isStopRequested()) {

            wrist.setPosition(1);


            //Move to high chamber while raising arm (22" while lifting arm to full length
            frontLeft.setPower(-0.5);
            backLeft.setPower(-0.5);
            frontRight.setPower(-0.5);
            backRight.setPower(-0.5);
            armExtend.setPower(1);

            sleep(1000);

            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
            armExtend.setPower(0);

            //drive 1" forward
            frontLeft.setPower(-0.5);
            backLeft.setPower(-0.5);
            frontRight.setPower(-0.5);
            backRight.setPower(-0.5);
            //raise claw 1"
            armExtend.setPower(1);

            sleep(100);

            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
            armExtend.setPower(0);

            //drive back 1.5"
            frontLeft.setPower(0.5);
            backLeft.setPower(0.5);
            frontRight.setPower(0.5);
            backRight.setPower(0.5);

            sleep(150);

            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
            armExtend.setPower(0);

            //release claw - bring claw back
            armExtend.setPower(-1);

            sleep(1100);

            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
            armExtend.setPower(0);

            //turn to bucket 180 degrees clockwise
            frontLeft.setPower(0.5);
            backLeft.setPower(0.5);
            frontRight.setPower(-0.5);
            backRight.setPower(-0.5);

            sleep(500);

            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
            armExtend.setPower(0);

            //strafe 28"
            frontLeft.setPower(0.5);
            backLeft.setPower(0.5);
            frontRight.setPower(0.5);
            backRight.setPower(0.5);

            sleep(1250);

            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
            armExtend.setPower(0);

            //strafe 25" left
            frontLeft.setPower(-0.5);
            backLeft.setPower(0.5);
            frontRight.setPower(0.5);
            backRight.setPower(-0.5);

            sleep(1000);

            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
            armExtend.setPower(0);

            //turn 45 degrees clockwise
            frontLeft.setPower(0.5);
            backLeft.setPower(-0.5);
            frontRight.setPower(-0.5);
            backRight.setPower(0.5);

            sleep(125);

            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
            armExtend.setPower(0);

            //drive 18" back & arm up to top bucket
            frontLeft.setPower(-0.5);
            backLeft.setPower(-0.5);
            frontRight.setPower(-0.5);
            backRight.setPower(-0.5);
            armExtend.setPower(1);

            sleep(1000);

            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
            armExtend.setPower(0);

            //turn 45 degrees counterclockwise"
            frontLeft.setPower(-0.5);
            backLeft.setPower(0.5);
            frontRight.setPower(0.5);
            backRight.setPower(-0.5);
            armExtend.setPower(-1);

            sleep(125);

            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
            armExtend.setPower(0);

            //start intake

            //drive 24" forward and lower arm
            frontLeft.setPower(0.5);
            backLeft.setPower(0.5);
            frontRight.setPower(0.5);
            backRight.setPower(0.5);

            sleep(1000);

            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
            armExtend.setPower(0);

            //drive 24" back
            frontLeft.setPower(-0.5);
            backLeft.setPower(0.5);
            frontRight.setPower(0.5);
            backRight.setPower(-0.5);

            sleep(1000);

            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
            armExtend.setPower(0);

            //turn 45 degrees
            frontLeft.setPower(-0.5);
            backLeft.setPower(-0.5);
            frontRight.setPower(-0.5);
            backRight.setPower(-0.5);

            sleep(125);

            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
            armExtend.setPower(0);

            //strafe left 1" & arm down
            frontLeft.setPower(-0.5);
            backLeft.setPower(0.5);
            frontRight.setPower(0.5);
            backRight.setPower(-0.5);
            armExtend.setPower(-1);

            sleep(125);

            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
            armExtend.setPower(0);

            //drive forward 56"
            frontLeft.setPower(0.5);
            backLeft.setPower(0.5);
            frontRight.setPower(0.5);
            backRight.setPower(0.5);

            sleep(2500);

            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
            armExtend.setPower(0);

            //arm raise 22"
            armExtend.setPower(1);

            sleep(500);

            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
            armExtend.setPower(0);
        }
    }
}