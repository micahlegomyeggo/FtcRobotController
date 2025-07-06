package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@Disabled
@TeleOp
public class fishingCalc extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor armExtend = hardwareMap.dcMotor.get("armExtend");
        DcMotor armRotation = hardwareMap.dcMotor.get("armRotation");

        double extend;
        double rotation;
        double rotationTickToDegree = 3.111111111;
        double radians;
        double degrees;
        int rotationPos;
        double height = 8; // 8 in
        double extendTick = 100;
        double inches;

        double base = 7;

        // 90 degrees = 280 ticks
        // 1 degree = 280/90
        // 1 degree = 3.11111 ticks

        // 1 inch = 100 ticks

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {



            armExtend.setPower(-gamepad2.left_stick_y);

            extend = armExtend.getCurrentPosition();
            inches = extend / extendTick + 12; // 12 inches to start

            radians = Math.acos(height/inches);
            degrees = Math.toDegrees(radians) - 32; //-41.81
//            rotation = Math.toIntExact(Math.round(degrees * rotationTickToDegree)); // - base ticks
            rotationPos = Math.toIntExact(Math.round(degrees * rotationTickToDegree));

            armRotation.setPower(0.5);
            armRotation.setTargetPosition(rotationPos);
            armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            telemetry.addData("Extend", armExtend.getCurrentPosition());
            telemetry.addData("Rotation", degrees);
            telemetry.addData("Rotation Ticks", armRotation.getCurrentPosition());
            telemetry.update();

//            if (gamepad2.a) {
//                armRotation.setPower(0.5);
//                armRotation.setTargetPosition(220);
//                armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            }
//            if (gamepad2.b) {
//                armRotation.setPower(0.5);
//                armRotation.setTargetPosition(500);
//                armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                armExtend.setPower(0.5);
//                armExtend.setTargetPosition(100);
//                armExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            }
//            if (gamepad2.y) {
//                armExtend.setPower(0.5);
//                armExtend.setTargetPosition(200);
//                armExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            }

        }
    }
}
