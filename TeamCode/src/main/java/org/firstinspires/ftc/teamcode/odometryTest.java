package org.firstinspires.ftc.teamcode;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/** @noinspection CommentedOutCode*/
@TeleOp
public class odometryTest extends LinearOpMode {

    DcMotorEx frontLeft;
    DcMotorEx backLeft;
    DcMotorEx frontRight;
    DcMotorEx backRight;

    double left_encoder_pos_ticks;
    double left_encoder_pos;
    double right_encoder_pos_ticks;
    double right_encoder_pos;
    double center_encoder_pos_ticks;
    double center_encoder_pos;
    double y_pos = 0;
    double x_pos = 0;
    double heading = 0;
    double prev_left_encoder_pos = 0;
    double prev_right_encoder_pos = 0;
    double prev_center_encoder_pos = 0;
    double delta_left_encoder_pos;
    double delta_right_encoder_pos;
    double delta_center_encoder_pos;
    double phi;
    double delta_middle_pos;
    double delta_perp_pos;
    double delta_y;
    double delta_x;
    double trackwidth = 7.45;
    double forward_offset = 5;
    double delta_target_x;
    double delta_target_y;
    double flSpeed;
    double blSpeed;
    double frSpeed;
    double brSpeed;
    double denominator;

    double target_x = 0;
    double target_y = 24;
    double x_speed_target;
    double y_speed_target;
    double angle_target = 0;
    double angle_speed_target;

//    public void drive(double target_x, double target_y) {
//
//        delta_target_x = target_x - x_pos;
//        delta_target_y = target_y - y_pos;
//
//        denominator = Math.max(Math.abs(delta_target_y) + Math.abs(delta_target_x), 1);
//        flSpeed = (delta_target_y + delta_target_x) / denominator;
//        blSpeed = (delta_target_y - delta_target_x) / denominator;
//        frSpeed = (delta_target_y - delta_target_x) / denominator;
//        brSpeed = (delta_target_y + delta_target_x) / denominator;
//
//        frontLeft.setPower(flSpeed);
//        backLeft.setPower(blSpeed);
//        frontRight.setPower(frSpeed);
//        backRight.setPower(brSpeed);
//
//    }

    /** @noinspection CommentedOutCode, CommentedOutCode */
    public void runOpMode() throws InterruptedException {

        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");

        while (!opModeIsActive() && !isStopRequested()) {
            telemetry.addData("Status: ", "Waiting For Start");
            telemetry.update();
        }

        waitForStart();

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (opModeIsActive() && !isStopRequested()) {

            left_encoder_pos_ticks = -frontLeft.getCurrentPosition();
            left_encoder_pos = left_encoder_pos_ticks / 337;
            right_encoder_pos_ticks = frontRight.getCurrentPosition();
            right_encoder_pos = right_encoder_pos_ticks / 337;
            center_encoder_pos_ticks = backLeft.getCurrentPosition();
            center_encoder_pos = center_encoder_pos_ticks / 337;

            delta_left_encoder_pos = left_encoder_pos - prev_left_encoder_pos;
            delta_right_encoder_pos = right_encoder_pos - prev_right_encoder_pos;
            delta_center_encoder_pos = center_encoder_pos - prev_center_encoder_pos;

            phi = (delta_left_encoder_pos - delta_right_encoder_pos) / trackwidth;
            delta_middle_pos = (delta_left_encoder_pos + delta_right_encoder_pos) / 2;
            delta_perp_pos = delta_center_encoder_pos - forward_offset * phi;

            delta_x = delta_middle_pos * cos(heading) - delta_perp_pos * sin(heading);
            delta_y = delta_middle_pos * sin(heading) + delta_perp_pos * cos(heading);

            x_pos += delta_x;
            y_pos += delta_y;
            heading += phi / 2;

            prev_left_encoder_pos = left_encoder_pos;
            prev_right_encoder_pos = right_encoder_pos;
            prev_center_encoder_pos = center_encoder_pos;

            delta_target_x = target_x - x_pos;
            delta_target_y = target_y - y_pos;

            x_speed_target = delta_target_x/100;
            y_speed_target = delta_target_y/100;

            angle_speed_target = angle_target - heading;

//            denominator = Math.max(Math.abs(y_speed_target) + Math.abs(x_speed_target) + Math.abs(angle_speed_target), 1);
//            flSpeed = (y_speed_target + x_speed_target + angle_speed_target) / denominator;
//            blSpeed = (y_speed_target - x_speed_target + angle_speed_target) / denominator;
//            frSpeed = (y_speed_target - x_speed_target - angle_speed_target) / denominator;
//            brSpeed = (y_speed_target + x_speed_target - angle_speed_target) / denominator;

            denominator = Math.max(Math.abs(y_speed_target) + Math.abs(x_speed_target), 1);
            flSpeed = (y_speed_target + x_speed_target) / denominator;
            blSpeed = (y_speed_target - x_speed_target) / denominator;
            frSpeed = (y_speed_target - x_speed_target) / denominator;
            brSpeed = (y_speed_target + x_speed_target) / denominator;

            frontLeft.setPower(flSpeed);
            backLeft.setPower(blSpeed);
            frontRight.setPower(frSpeed);
            backRight.setPower(brSpeed);

//            telemetry.addData("Left", left_encoder_pos);
//            telemetry.addData("Right", right_encoder_pos);
//            telemetry.addData("Back", center_encoder_pos);
//
//            telemetry.addData("Left Change", delta_left_encoder_pos);
//            telemetry.addData("Right Change", delta_right_encoder_pos);
//            telemetry.addData("Back Change", delta_center_encoder_pos);

            telemetry.addData("x distance", delta_target_x);
            telemetry.addData("y distance", delta_target_y);

            telemetry.addData("X:", x_pos);
            telemetry.addData("Y:", y_pos);
            telemetry.addData("Heading:", heading);
            telemetry.update();

        }
    }

}



//48mm/25.4 = 1.88976in
//1.88976in*3.14 = 5.9338464in (circumference)
//2000 ticks per rotation
//2000/5.9338464 = 337.049506
//~337 ticks/in
