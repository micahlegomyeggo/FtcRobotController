package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
@Disabled
@Autonomous
public class wristTest extends LinearOpMode {

    public class armExtend {
        private DcMotorEx armExtend;

        public armExtend(HardwareMap hardwareMap) {
            armExtend = hardwareMap.get(DcMotorEx.class, "armExtend");
            armExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        public class Hang implements Action {

            private boolean initialized = false;

            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    armExtend.setPower(0.4);
                    armExtend.setTargetPosition(1000);
                    armExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    initialized = true;
                }

                double extendPos = armExtend.getCurrentPosition();
                packet.put("armExtendPos", extendPos);

                if (extendPos < 1000) {
                    return true;
                } else {
                    armExtend.setPower(0);
                    return false;
                }
            }

        }

        public Action hang() {
            return new armExtend.Hang();
        }

        public class Normal implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    armExtend.setPower(0.4);
                    armExtend.setTargetPosition(5);
                    armExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    initialized = true;
                }

                double pos = armExtend.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos > 5.0) {
                    return true;
                } else {
                    armExtend.setPower(0);
                    return false;
                }
            }
        }

        public Action normal() {
            return new wristTest.armExtend.Normal();
        }

        public class Half implements Action {

            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    armExtend.setPower(0.4);
                    armExtend.setTargetPosition(500);
                    armExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    initialized = true;
                }

                double pos = armExtend.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos < 700.0) {
                    return true;
                } else {
                    armExtend.setPower(0);
                    return false;
                }
            }
        }

        public Action half() {
            return new Half();
        }

    }

    public class armRotation {

        private DcMotorEx armRotation;

        public armRotation(HardwareMap hardwareMap) {
            armRotation = hardwareMap.get(DcMotorEx.class, "armRotation");
            armRotation.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        public class Hang implements Action {

            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    armRotation.setPower(0.5);
                    armRotation.setTargetPosition(-390);
                    armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    initialized = true;
                }

                int pos = -armRotation.getCurrentPosition();
                packet.put("armRotationPos", pos);
                if (pos < 390) {
                    telemetry.addData("Pos, ", pos);
                    telemetry.update();
                    return true;
                } else {
                    armRotation.setPower(-0.22);
                    return false;
                }

            }

        }

        public Action hang() {
            return new Hang();
        }



        public class Normal implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    armRotation.setPower(0.25);
                    armRotation.setTargetPosition(-10);
                    armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    initialized = true;
                }

                int pos = -armRotation.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos > 10.0) {
                    return true;
                } else {
                    armRotation.setPower(-0.22);
                    return false;
                }
            }
        }

        public Action normal() {
            return new wristTest.armRotation.Normal();
        }
    }

    public class wrist {
        Servo wrist;

        public wrist(HardwareMap hardwareMap) {
            wrist = hardwareMap.get(Servo.class, "wrist");
        }

        public class up implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (true) {
                    wrist.setPosition(0.35);
                }
                telemetry.addData("Up", "");

                return false;
            }
        }
        public Action up() {
            return new wrist.up();
        }

        public class down implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                wrist.setPosition(1);
                telemetry.addData("Down", "");

                return false;
            }
        }
        public Action down() {
            return new wrist.down();
        }

        public class update implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                telemetry.update();
                return false;
            }
        }

        public Action update() {
            return new wrist.update();
        }
    }

    public void runOpMode() {

        // instantiate your MecanumDrive at a particular pose.
        Pose2d initialPose = new Pose2d(-24, -63, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        wristTest.armRotation armRotation = new wristTest.armRotation(hardwareMap);

        wristTest.armExtend armExtend = new wristTest.armExtend(hardwareMap);

        wristTest.wrist wrist = new wristTest.wrist(hardwareMap);

        TrajectoryActionBuilder wait = drive.actionBuilder(initialPose)
                .waitSeconds(2);

        waitForStart();

        if (isStopRequested()) return;

        Action Wait = wait.build();

//        Actions.runBlocking(new SequentialAction(armRotation.hang(), armExtend.half(), new SleepAction(3), new SequentialAction(wrist.up(), new SleepAction(3)), new SequentialAction(wrist.down(), new SleepAction(3), wrist.up()), armExtend.normal(), armRotation.normal(), wrist.update()));

        Actions.runBlocking(new SequentialAction(wrist.up(), new SleepAction(1), wrist.down(), new SleepAction(1), wrist.up(), new SleepAction(1), wrist.down(), new SleepAction(1), wrist.up(), new SleepAction(1), wrist.down(), new SleepAction(1), wrist.up(), new SleepAction(1), wrist.down(), new SleepAction(1), wrist.up()));

    }
}