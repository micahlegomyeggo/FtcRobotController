package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
@Disabled
@TeleOp
public class armTrig extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor armRotation = hardwareMap.dcMotor.get("armRotation");
        DcMotor armExtend = hardwareMap.dcMotor.get("armExtend");
        Servo wrist = hardwareMap.get(Servo.class, "wrist");
        Servo claw = hardwareMap.get(Servo.class, "claw");

        double a = 8;
        double b = 9;
        double c;
        double cTicks;
        double angle;
        double angleTicks;
        double angleChange = -22.2; //-25
        double input;

        double rPower;
        double rPos;
        double rLastError = 0;
        double rError;
        double rTarget = 0; //505

        double p = 0.018; // 0.0125
        double i = 0.0017; // 0.0003 has been the closest 0.002
        double d = 0.01; // 0.019
        double integral = 0;
        double derivative;


        armExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armExtend.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armRotation.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRotation.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {

            if (gamepad2.right_trigger > 0) {claw.setPosition(1);}
            if (gamepad2.left_trigger > 0) {claw.setPosition(0.7);}
            if (gamepad2.dpad_up) {wrist.setPosition(0.75);}
            if (gamepad2.dpad_down) {wrist.setPosition(1);}

            input = -gamepad2.left_stick_y * 0.2;
            b += input;
            if (b<9){b=9;}
            if (b>30){b=30;}

            c = Math.hypot(a, b) - 12;
            cTicks = c * 100;

            armExtend.setPower(0.5);
            armExtend.setTargetPosition((int)Math.round(cTicks));
            armExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            angle = Math.toDegrees(Math.atan(b/a)) + angleChange;
            angleTicks = angle * 3.1111111;

//            armRotation.setPower(0.5);
//            armRotation.setTargetPosition((int)Math.round(angleTicks));
//            armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);

//            rTarget = (int)Math.round(angleTicks);

            if (gamepad2.a) {
                rTarget += 10;
                sleep(100);
            }
            if (gamepad2.b) {
                rTarget -= 10;
                sleep(100);
            }

            rPos = armRotation.getCurrentPosition();
            rError = rTarget - rPos;
            integral += rError;
            derivative = rError - rLastError;
            rPower = p * rError + i * integral + d * derivative;
            if (rPower > 1) {rPower=1;}
            if (rPower < -1) {rPower=-1;}
            armRotation.setPower(rPower);
            rLastError = rError;


            telemetry.addData("Power:", rPower);
            telemetry.addData("Pos:", rPos);
            telemetry.addData("Error:", rError);
            telemetry.addData("Integral:", integral);
            telemetry.addData("Derivative:", derivative);
            telemetry.addData("a", a);
            telemetry.addData("b", b);
            telemetry.addData("c", c + 12);
            telemetry.addData("c ticks", cTicks);
            telemetry.addData("c pos", armExtend.getCurrentPosition());
            telemetry.addData("angle", angle - angleChange);
            telemetry.addData("angle ticks", angleTicks);
            telemetry.addData("angle pos", armRotation.getCurrentPosition());
            telemetry.update();

        }
    }
}
