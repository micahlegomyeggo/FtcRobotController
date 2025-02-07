package org.firstinspires.ftc.teamcode.auto;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.AccelConstraint;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.MinVelConstraint;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;

import java.util.Collections;

@Autonomous
public class autoL extends LinearOpMode {

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
                    armExtend.setPower(0.6);
                    armExtend.setTargetPosition(1080);
                    armExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    initialized = true;
                }

                double extendPos = armExtend.getCurrentPosition();
                packet.put("armExtendPos", extendPos);

                if (extendPos < 1080) {
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
                    armExtend.setTargetPosition(30);
                    armExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    initialized = true;
                }

                double pos = armExtend.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos > 30) {
                    return true;
                } else {
                    armExtend.setPower(0);
                    return false;
                }
            }
        }

        public Action normal() {
            return new autoL.armExtend.Normal();
        }

        public class Grab implements Action {

            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    armExtend.setPower(0.6);
                    armExtend.setTargetPosition(935);
                    armExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    initialized = true;
                }

                double pos = armExtend.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos < 935) {
                    return true;
                } else {
                    armExtend.setPower(0);
                    return false;
                }
            }
        }

        public Action grab() {
            return new Grab();
        }

        public class Bucket implements Action {

            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    armExtend.setPower(0.6);
                    armExtend.setTargetPosition(2375);
                    armExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    initialized = true;
                }

                double pos = armExtend.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos < 2375) {
                    return true;
                } else {
                    armExtend.setPower(0);
                    return false;
                }
            }
        }

        public Action bucket() {
            return new Bucket();
        }

        public class Hang2 implements Action {

            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    armExtend.setPower(0.6);
                    armExtend.setTargetPosition(950);
                    armExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    initialized = true;
                }

                double pos = armExtend.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos < 950.0) {
                    return true;
                } else {
                    armExtend.setPower(0);
                    return false;
                }
            }
        }

        public Action hang2() {
            return new Hang2();
        }

        public class Reset implements Action {

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                armExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                return false;
            }
        }

        public Action reset() {
            return new Reset();
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
                    armRotation.setTargetPosition(385);
                    armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    initialized = true;
                }

                int pos = armRotation.getCurrentPosition();
                packet.put("armRotationPos", pos);
                if (pos < 385) {
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
                    armRotation.setPower(0.28);
                    armRotation.setTargetPosition(10);
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
            return new autoL.armRotation.Normal();
        }

        public class Hang2 implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    armRotation.setPower(0.6);
                    armRotation.setTargetPosition(270);
                    armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    initialized = true;
                }

                int pos = -armRotation.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos > 270.0) {
                    return true;
                } else {
                    armRotation.setPower(-0.22);
                    return false;
                }
            }
        }

        public Action hang2() {
            return new autoL.armRotation.Hang2();
        }

        public class Bucket implements Action {

            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    armRotation.setPower(0.5);
                    armRotation.setTargetPosition(510);
                    armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    initialized = true;
                }

                int pos = -armRotation.getCurrentPosition();
                packet.put("armRotationPos", pos);
                if (pos < 510.0) {
                    return true;
                } else {
                    armRotation.setPower(-0.22);
                    return false;
                }

            }

        }

        public Action bucket() {
            return new Bucket();
        }

        public class Grab implements Action {

            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    armRotation.setPower(0.5);
                    armRotation.setTargetPosition(95);
                    armRotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    initialized = true;
                }

                int pos = -armRotation.getCurrentPosition();
                packet.put("armRotationPos", pos);
                if (pos > 95) {
                    return true;
                } else {
                    armRotation.setPower(0.22);
                    return false;
                }

            }
        }

        public Action grab() {
            return new Grab();
        }

        public class Reset implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                armRotation.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                return false;
            }
        }

        public Action reset() {
            return new Reset();
        }
    }

    public class claw {
        private Servo claw;

        public claw(HardwareMap hardwareMap) {
            claw = hardwareMap.get(Servo.class, "claw");
        }

        public class CloseClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                claw.setPosition(1);
                return false;
            }
        }
        public Action closeClaw() {
            return new claw.CloseClaw();
        }

        public class OpenClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                claw.setPosition(0.75);
                return false;
            }
        }
        public Action openClaw() {
            return new claw.OpenClaw();
        }

    }

    public class wrist {
        private Servo wrist;

        public wrist(HardwareMap hardwareMap) {
            wrist = hardwareMap.get(Servo.class, "wrist");
        }

        public class up implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                wrist.setPosition(0.75);
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
                return false;
            }
        }
        public Action down() {
            return new wrist.down();
        }
    }

    public class rotation {
        private Servo rotation;

        public rotation(HardwareMap hardwareMap) {
            rotation = hardwareMap.get(Servo.class, "rotation");
        }

        public class out implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                rotation.setPosition(0.99);
                return false;
            }
        }
        public Action out() {
            return new rotation.out();
        }

        public class in implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                rotation.setPosition(0);
                return false;
            }
        }
        public Action in() {
            return new rotation.in();
        }
    }

    public void runOpMode() {

        // instantiate your MecanumDrive at a particular pose.
        Pose2d initialPose = new Pose2d(-24, -63, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        autoL.claw claw = new autoL.claw(hardwareMap);

        autoL.armRotation armRotation = new autoL.armRotation(hardwareMap);

        autoL.armExtend armExtend = new autoL.armExtend(hardwareMap);

        autoL.wrist wrist = new autoL.wrist(hardwareMap);

        autoL.rotation rotation = new autoL.rotation(hardwareMap);

        TrajectoryActionBuilder bucket1 = drive.actionBuilder(initialPose)
                .strafeToLinearHeading(new Vector2d(-54, -51), Math.toRadians(30));

        TrajectoryActionBuilder bucket12 = bucket1.endTrajectory().fresh()
                .lineToXConstantHeading(-62);

        TrajectoryActionBuilder bucket13 = bucket12.endTrajectory().fresh()
                .lineToXConstantHeading(-53);

        TrajectoryActionBuilder grab1 = bucket13.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(-47.25, -35.75), Math.toRadians(75));

        TrajectoryActionBuilder bucket2 = grab1.endTrajectory().fresh()
                .setReversed(true)
                .strafeToLinearHeading(new Vector2d(-54, -51), Math.toRadians(30));

        TrajectoryActionBuilder bucket22 = bucket2.endTrajectory().fresh()
                .setTangent(0)
                .lineToX(-62);
//                .lineToXConstantHeading(-62);

        TrajectoryActionBuilder bucket23 = bucket22.endTrajectory().fresh()
                .lineToXConstantHeading(-53);

        TrajectoryActionBuilder grab2 = bucket13.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(-59, -41), Math.toRadians(80));

        TrajectoryActionBuilder bucket3 = grab2.endTrajectory().fresh()
                .setReversed(true)
                .strafeToLinearHeading(new Vector2d(-60, -51), Math.toRadians(30));

        TrajectoryActionBuilder park = bucket13.endTrajectory().fresh()
                .turn(Math.toRadians(150))
                .strafeToConstantHeading(new Vector2d(-24, -4))
                .strafeToConstantHeading(new Vector2d(-16, -4));

//        TrajectoryActionBuilder grab3 = bucket2.fresh()
//                .setReversed(false)
//                .splineTo(new Vector2d(), Math.toRadians(90)); // Make this facing wall with wrist turned 90 degrees

//        TrajectoryActionBuilder bucket3 = grab3.fresh()
//                .setReversed(true)
//                .splineTo(new Vector2d(-53, -51), Math.toRadians(225))
//                .setReversed(false);


        waitForStart();

        if (isStopRequested()) return;

        Action Bucket1 = bucket1.build();
        Action Bucket12 = bucket12.build();
        Action Bucket13 = bucket13.build();
        Action Grab1 = grab1.build();
        Action Bucket2 = bucket2.build();
        Action Bucket22 = bucket22.build();
        Action Bucket23 = bucket23.build();
        Action Grab2 = grab2.build();
        Action Bucket3 = bucket3.build();
        Action Park = park.build();

        VelConstraint baseVelConstraint = new MinVelConstraint(Collections.singletonList(
                new TranslationalVelConstraint(40.0)
        ));
        AccelConstraint baseAccelConstraint = new ProfileAccelConstraint(-10.0, 25.0);

        //Blue + Red left auto
        //Hangs 1 on bar, puts two yellow into bucket (will be 3)
        Actions.runBlocking(
            new ParallelAction(
                new SequentialAction(
                    armExtend.reset(),
                    armRotation.reset(),
                    rotation.in(),
                    claw.closeClaw(),
                    wrist.up(),
                    new ParallelAction(
                        Bucket1,
                        new SequentialAction(
                            new SleepAction(0.6),
                            armRotation.bucket()
                        ),
                        new SequentialAction(
                            new SleepAction(1),
                            armExtend.bucket(),
                            Bucket12,
                                new SleepAction(0.25),
                            claw.openClaw(),
                            new SleepAction(0.25),
                            new ParallelAction(
                                Bucket13,
                                armExtend.normal()
                            ),

                            new ParallelAction(
                                armRotation.grab(),
                                Grab1
                ),
                            new SleepAction(0.25),
                            new ParallelAction(
                                armExtend.grab(),
                                wrist.down()
                            ),
                            claw.closeClaw(),
                            new SleepAction(0.25),
                            new ParallelAction(
                                wrist.up(),
                                armExtend.normal()
                            ),
                            new ParallelAction(
                                armRotation.bucket(),
                                Bucket2,
                                new SequentialAction(
                                    new SleepAction(1),
                                    armExtend.bucket(),
                                    Bucket22,
                                    new SleepAction(0.25),
                                    claw.openClaw(),
                                    new SleepAction(0.25),
                                    new ParallelAction(
                                        Bucket23,
                                        armExtend.normal()
                                    ),
                                        Park // this point is temporary for now, but could be used in comp

//                                    new ParallelAction(
//                                        armRotation.grab(),
//                                        Grab2,
//                ),
//                                    new SleepAction(0.25),
//                                    new ParallelAction(
//                                        armExtend.grab(),
//                                        wrist.down()
//                                    ),
//                                    claw.closeClaw(),
//                                    new SleepAction(0.25),
//                                    new ParallelAction(
//                                        wrist.up(),
//                                        armExtend.normal()
//                                    ),
//                                    new ParallelAction(
//                                        armRotation.bucket(),
//                                        Bucket3,
//                                        new SequentialAction(
//                                            new SleepAction(1),
//                                            armExtend.bucket(),
//                                            Bucket12,
//                                            new SleepAction(0.25),
//                                            claw.openClaw(),
//                                            new SleepAction(0.25),
//                                            new ParallelAction(
//                                                Bucket13
//                                                armExtend.normal()
//                                            ),
//                                            armRotation.normal()
//                                        )
//                                    )
                                )
                            )
                        )
                    )
                )
            )
        );
    }
}