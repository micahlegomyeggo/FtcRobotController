package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
@Disabled
@TeleOp
public class holdPosition extends LinearOpMode {

    public void runOpMode() throws InterruptedException {

        double p = 0.0018, i = 0, d = 0.0001, f = 0.1;

        DcMotor arm = hardwareMap.dcMotor.get("armRotation");

        double error;
        double out;
        double encoderPosition;
        double derivative;
        double ff;
        double power;

        double reference = 100;

        double integralSum = 0;

        double lastError = 0;

        double ticksPerDeg = 0.003889;

        ElapsedTime timer = new ElapsedTime();

        waitForStart();

        while (opModeIsActive()) {

            encoderPosition = arm.getCurrentPosition();

            error = reference - encoderPosition;

            derivative = (error - lastError) / timer.seconds();

            integralSum = integralSum + (error * timer.seconds());

            out = (p * error) + (i * integralSum) + (d * derivative);

            ff = Math.cos(Math.toRadians(reference / ticksPerDeg)) * f;

            power = out;// + ff;

            arm.setPower(-power);

            lastError = error;

            timer.reset();

            telemetry.addData("pos", encoderPosition);
            telemetry.update();

        }
    }
}
