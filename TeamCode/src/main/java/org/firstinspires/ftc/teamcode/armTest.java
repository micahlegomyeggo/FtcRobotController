package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
@Disabled
@TeleOp
public class armTest extends LinearOpMode {

    public void runOpMode() throws InterruptedException {

        DcMotor armRotation = hardwareMap.get(DcMotorEx.class, "armRotation");
        DcMotor armExtend = hardwareMap.get(DcMotorEx.class, "armExtend");
        //Servo wrist = hardwareMap.get(Servo.class, "wrist");

        waitForStart();

        if (isStopRequested()) return;

        armRotation.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        armExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        while (opModeIsActive() && !isStopRequested()) {

            if (gamepad1.a) {

                armRotation.setPower(0.5);

            } else if (gamepad1.b) {

                armRotation.setPower(-0.5);

            } else {

                armRotation.setPower(0);

            }



            if (gamepad1.left_trigger > 0) {

                armExtend.setPower(-0.65);

            } else if (gamepad1.right_trigger > 0) {

                armExtend.setPower(0.65);

            } else {

                armExtend.setPower(0);

            }



            telemetry.addData("Arm:", armRotation.getCurrentPosition());
            telemetry.addData("Extended out", armExtend.getCurrentPosition());
            telemetry.update();

        }

    }

}
