package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@TeleOp
public class blinkinTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        RevBlinkinLedDriver blinkinLedDriver = hardwareMap.get(RevBlinkinLedDriver.class, "blinkin");
        RevBlinkinLedDriver.BlinkinPattern pattern;

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.a) {
                blinkinLedDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
            }
            if (gamepad1.b) {
                blinkinLedDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
            }
            if (gamepad1.x) {
                blinkinLedDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
            }

        }

    }
}
