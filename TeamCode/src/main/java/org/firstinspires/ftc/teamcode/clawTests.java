package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class clawTests extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Servo claw1 = hardwareMap.get(Servo.class, "claw1");
        Servo wrist1 = hardwareMap.get(Servo.class, "wrist1");
        Servo shoulder1 = hardwareMap.get(Servo.class, "shoulder1");

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.a) {

            }
            if (gamepad1.b) {

            }
            if (gamepad1.y) {

            }
            if (gamepad1.x) {

            }

        }

    }
}
