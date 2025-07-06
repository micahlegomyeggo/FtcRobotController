package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
@Disabled
@TeleOp
public class wrist extends LinearOpMode{

    public void runOpMode() throws InterruptedException {

        Servo wrist2 = hardwareMap.get(Servo.class, "wrist2");

        Double pos = 0.25;

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {

            if (gamepad2.dpad_left) {
                pos -= 0.1;
                sleep(250);
            }
            if (gamepad2.dpad_right) {
                pos += 0.1;
                sleep(250);
            }
            if (pos >= 1) {
                pos = 1.0;
            }
            if (pos <= 0) {
                pos = 0.0;
            }

            wrist2.setPosition(pos);

            telemetry.addData("Pos", pos);
            telemetry.update();

        }

    }

}
