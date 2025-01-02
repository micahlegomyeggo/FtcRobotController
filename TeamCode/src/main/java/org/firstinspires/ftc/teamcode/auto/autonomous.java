package org.firstinspires.ftc.teamcode.auto;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous
public class autonomous extends LinearOpMode {

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
                    armExtend.setPower(-0.4);
                    initialized = true;
                }

                double extendPos = armExtend.getCurrentPosition();
                packet.put("armExtendPos", extendPos);

                if (extendPos > -1240) {
                    return true;
                } else {
                    armExtend.setPower(0);
                    return false;
                }
            }

        }

        public Action extendHang() {
            return new autonomous.armExtend.Hang();
        }

        public class Normal implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    armExtend.setPower(0.4);
                    initialized = true;
                }

                double pos = armExtend.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos < -100.0) {
                    return true;
                } else {
                    armExtend.setPower(0);
                    return false;
                }
            }
        }

        public Action normal() {
            return new autonomous.armExtend.Normal();
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

            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    armRotation.setPower(-0.3);
                    initialized = true;
                }

                double pos = armRotation.getCurrentPosition();
                packet.put("armRotationPos", pos);
                if (pos > -320.0) {
                    return true;
                } else {
                    armRotation.setPower(0);
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
                    armRotation.setPower(0.3);
                    initialized = true;
                }

                double pos = armRotation.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos < -10.0) {
                    return true;
                } else {
                    armRotation.setPower(0);
                    return false;
                }
            }
        }

        public Action normal() {
            return new autonomous.armRotation.Normal();
        }

        public class Hang2 implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    armRotation.setPower(0.3);
                    initialized = true;
                }

                double pos = armRotation.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos < -300.0) {
                    return true;
                } else {
                    armRotation.setPower(0);
                    return false;
                }
            }
        }

        public Action hang2() {
            return new autonomous.armRotation.Hang2();
        }
    }

    public class Claw {
        private Servo claw;

        public Claw(HardwareMap hardwareMap) {
            claw = hardwareMap.get(Servo.class, "claw");
        }

        public class CloseClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                claw.setPosition(0.55);
                return false;
            }
        }
        public Action closeClaw() {
            return new autonomous.Claw.CloseClaw();
        }

        public class OpenClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                claw.setPosition(1.0);
                return false;
            }
        }
        public Action openClaw() {
            return new autonomous.Claw.OpenClaw();
        }

    }

    public class Wrist {
        private Servo wrist;

        public Wrist(HardwareMap hardwareMap) {
            wrist = hardwareMap.get(Servo.class, "wrist");
        }

        public class wristUp implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                wrist.setPosition(1);
                return false;
            }
        }
        public Action wristUp() {
            return new autonomous.Wrist.wristUp();
        }

        public class wristDown implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                wrist.setPosition(0.35);
                return false;
            }
        }
        public Action wristDown() {
            return new autonomous.Wrist.wristDown();
        }
    }

    public void runOpMode() {

        // instantiate your MecanumDrive at a particular pose.
        Pose2d initialPose = new Pose2d(-24, -63, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        autonomous.Claw claw = new autonomous.Claw(hardwareMap);

        autonomous.armRotation armRotation = new autonomous.armRotation(hardwareMap);

        autonomous.armExtend armExtend = new autonomous.armExtend(hardwareMap);

        autonomous.Wrist wrist = new autonomous.Wrist(hardwareMap);

        TrajectoryActionBuilder clipHang = drive.actionBuilder(initialPose)
                .setReversed(false)
                .strafeTo(new Vector2d(0, -34))
                .waitSeconds(3);

        TrajectoryActionBuilder clipHang2 = drive.actionBuilder(new Pose2d(0, -34, Math.toRadians(90)))
                .strafeTo(new Vector2d(0, -36));

        TrajectoryActionBuilder bucket = drive.actionBuilder(new Pose2d(0, -38, Math.toRadians(90)))

                .setReversed(true)
                .splineTo(new Vector2d(-51, -50), Math.toRadians(225))
                .waitSeconds(3);

        waitForStart();

        if (isStopRequested()) return;

        Action TAB1 = clipHang.build();
        Action TAB2 = clipHang2.build();
        Action TAB3 = bucket.build();

        Actions.runBlocking(new SequentialAction(
                claw.closeClaw(),
                        TAB1,
                        armRotation.hang(),
                        armExtend.extendHang()
//                        wrist.wristDown(),
//                        armRotation.hang2(),
//                        TAB2,
//                        claw.openClaw(),
//                        armExtend.normal(),
//                        armRotation.normal()
//                        TAB3
                )
        );

    }

}