package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
@Disabled
public class armHold extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor arm = hardwareMap.dcMotor.get("armRotation");

        double power = 0;
        double pos;
        double lastPos = 0;
        double error = 0;
        double lastError = 0;
        double target = 100;

        waitForStart();

        while (opModeIsActive()) {

            pos = -arm.getCurrentPosition();
            error = target - pos;

            if (gamepad2.dpad_up) {
                power = power - 0.02;
                sleep(250);
            }
            if (gamepad2.dpad_down) {
                power = power + 0.02;
                sleep(250);
            }

            arm.setPower(power);

            lastPos = pos;
            lastError = error;

            telemetry.addData("Power:", -power);
            telemetry.addData("Pos:", pos);
            telemetry.addData("Error:", error);
            telemetry.update();

        }
    }
}
