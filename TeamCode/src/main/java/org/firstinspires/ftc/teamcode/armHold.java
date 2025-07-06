package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Disabled
@TeleOp
public class armHold extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor arm = hardwareMap.dcMotor.get("armRotation");

        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        arm.setDirection(DcMotorSimple.Direction.REVERSE);

        double power = 0;
        double pos;
        double lastError = 0;
        double error = 0;
        double target = 0; //505

        double p = 0.0079; // 0.0079
        double i = 0.000003; // 0.00003 has been the closest
        double d = 0.095; //0.095
        double integral = 0;
        double derivative;

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad2.a) {
                target = 100;
            }
            if (gamepad2.b) {
                target = 200;
            }
            if (gamepad2.y) {
                target = 350;
            }
            if (gamepad2.x) {
                target = 510;
            }

            pos = arm.getCurrentPosition();
            error = target - pos;

            integral += error;

            derivative = error - lastError;

            power = p * error + i * integral + d * derivative;

            if (power>1){power=1;}
            if (power<-1){power=-1;}

            arm.setPower(power);

            lastError = error;

            telemetry.addData("Power:", power);
            telemetry.addData("Pos:", pos);
            telemetry.addData("Error:", error);
            telemetry.addData("Integral:", integral);
            telemetry.addData("Derivative:", derivative);

            telemetry.update();

            /*
            PID
            P = error
            I = Error over time
            D = Rate of change for Error

            proportional = goal - pos
            integral += error;
            derivative = error - lastError




            if (gamepad2.dpad_up) {
                power = power - 0.02;
                sleep(250);
            }
            if (gamepad2.dpad_down) {
                power = power + 0.02;
                sleep(250);
            }
             */
            // power =

        }
    }
}
