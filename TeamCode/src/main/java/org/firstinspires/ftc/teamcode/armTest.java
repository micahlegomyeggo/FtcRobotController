package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
@Disabled
@TeleOp
public class armTest extends LinearOpMode {

    public void runOpMode() throws InterruptedException {

        DcMotor armRotation = hardwareMap.get(DcMotorEx.class, "armRotation");

        DcMotor hang = hardwareMap.get(DcMotor.class, "hang");
//        DcMotor armExtend = hardwareMap.get(DcMotorEx.class, "armExtend");
        Servo rotation = hardwareMap.get(Servo.class, "rotation");

        waitForStart();

        if (isStopRequested()) return;

        armRotation.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armRotation.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRotation.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (opModeIsActive() && !isStopRequested()) {

            if (gamepad1.a) {
                armRotation.setPower(1);
                armRotation.setTargetPosition(100);
                armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                armRotation.setPower(1);
            } else if (gamepad1.b) {
                armRotation.setPower(1);
                armRotation.setTargetPosition(150);
                armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                armRotation.setPower(-1);
            }
            if(gamepad1.y){
                hang.setPower(1);
                hang.setTargetPosition(6000);
                hang.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            if (gamepad1.x){
                hang.setPower(1);
                hang.setTargetPosition(5);
                hang.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            if (gamepad1.dpad_right) {
                rotation.setPosition(0);
            }
            if (gamepad1.dpad_left) {
                rotation.setPosition(1);
            }
//            else {
//                armRotation.setPower(0);
//            }

            telemetry.addData("Arm:", armRotation.getCurrentPosition());
            telemetry.update();

        }

    }

}
