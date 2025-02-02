package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
@Disabled
@TeleOp
public class colorSensor extends LinearOpMode {

    public final static ElapsedTime fishingTime = new ElapsedTime();

    Servo fishingArm;
    DcMotor frontLeft;
    DcMotor backLeft;
    DcMotor frontRight;
    DcMotor backRight;

    NormalizedColorSensor colorSensor;

    @Override public void runOpMode() {

        fishingTime.reset();

        runSample();

    }

    protected void runSample() {

        double fishPower = 1;

        float gain = 2;

        Servo fishingArm = hardwareMap.get(Servo.class, "fishingArm");
        DcMotor frontLeft = hardwareMap.dcMotor.get("frontLeft");
        DcMotor backLeft = hardwareMap.dcMotor.get("backLeft");
        DcMotor frontRight = hardwareMap.dcMotor.get("frontRight");
        DcMotor backRight = hardwareMap.dcMotor.get("backRight");

        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");

        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight)colorSensor).enableLight(true);
        }

        waitForStart();

        while (opModeIsActive()) {

            frontLeft.setPower(0.12 * 0.95);
            backLeft.setPower(-0.12 * 0.8);
            frontRight.setPower(-0.12 * 0.95);
            backRight.setPower(0.12 * 0.8);

            fishingArm.setPosition(fishPower);

            NormalizedRGBA colors = colorSensor.getNormalizedColors();

            telemetry.addLine()
                    .addData("Red", "%.3f", colors.red)
                    .addData("Green", "%.3f", colors.green)
                    .addData("Blue", "%.3f", colors.blue);
            telemetry.addData("Alpha", "%.3f", colors.alpha);

            if (colorSensor instanceof DistanceSensor) {
                telemetry.addData("Distance (cm)", "%.3f", ((DistanceSensor) colorSensor).getDistance(DistanceUnit.CM));
            }

            telemetry.update();

            if (fishingTime.seconds() > 2) {
                fishingTime.reset();

                if (fishPower == 1) {
                    fishPower = 0;
                } else {
                    fishPower = 1;
                }
            }

            if (colors.blue > colors.red && colors.blue > colors.green) {
                fishingArm.setPosition(0.5);

                frontLeft.setPower(0);
                backLeft.setPower(0);
                frontRight.setPower(0);
                backRight.setPower(0);

                telemetry.addLine("Blue found");
                telemetry.update();

                break;
            }

        }
    }
}
