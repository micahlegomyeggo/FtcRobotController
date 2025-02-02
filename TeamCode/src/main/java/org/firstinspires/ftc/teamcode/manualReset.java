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
public class manualReset extends LinearOpMode {

    int bPos = 110;

    public void runOpMode() throws InterruptedException {

        DcMotor armExtend = hardwareMap.get(DcMotorEx.class, "armExtend");
        DcMotor hang = hardwareMap.get(DcMotorEx.class, "hang");
        Servo fishingArm = hardwareMap.get(Servo.class, "fishingExtend");

        waitForStart();

        if (isStopRequested()) return;

        armExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        armExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armExtend.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (opModeIsActive() && !isStopRequested()) {

            armExtend.setPower(-gamepad2.left_stick_y * 0.75);

            hang.setPower(-gamepad2.right_stick_y);

        }

        hang.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

    }

}
