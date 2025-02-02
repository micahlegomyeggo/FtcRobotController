package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
@Disabled
@Autonomous
public class basicOmniAuto extends LinearOpMode {

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

            armRotation.setTargetPosition(600);
            armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armRotation.setPower(0.15);

            sleep(2000);

            //armExtend.setTargetPosition(1500);
            //armExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            armExtend.setPower(1);
            sleep(3300);
            armExtend.setPower(0);

            sleep(1000);

            //drive(-0.5, -0.5, -0.5, -0.5);
            frontLeft.setPower(-0.5);
            backLeft.setPower(-0.5);
            frontRight.setPower(-0.5);
            backRight.setPower(-0.5);

            sleep(500);
            
            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);

//            wrist.setPosition(1);


            intake.setPosition(0);
            sleep(1000);
            intake.setPosition(0.5);

            wrist.setPosition(0);

            armExtend.setPower(-1);
            sleep(3300);
            armExtend.setPower(0);

            frontLeft.setPower(0.5);
            backLeft.setPower(0.5);
            frontRight.setPower(0.5);
            backRight.setPower(0.5);

            sleep(500);

            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);

            frontLeft.setPower(-0.5);
            backLeft.setPower(0.5);
            frontRight.setPower(0.5);
            backRight.setPower(-0.5);

            sleep(1800);

            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);

            frontLeft.setPower(0.5);
            backLeft.setPower(0.5);
            frontRight.setPower(0.5);
            backRight.setPower(0.5);

            sleep(500);

            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
        }
    }
}