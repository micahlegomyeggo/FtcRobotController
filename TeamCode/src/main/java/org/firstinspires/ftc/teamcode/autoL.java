package org.firstinspires.ftc.teamcode;

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
                    armExtend.setPower(1);
                    armExtend.setTargetPosition(1);
                    armExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    initialized = true;
                }

                double pos = armExtend.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos > 1) {
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
                    armExtend.setPower(1);
                    armExtend.setTargetPosition(760); //850
                    armExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    initialized = true;
                }

                double pos = armExtend.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos < 760) { //890
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
                    armExtend.setPower(1);
                    armExtend.setTargetPosition(2335); //2315
                    armExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    initialized = true;
                }

                double pos = armExtend.getCurrentPosition();
                packet.put("liftPos", pos);
                telemetry.addData("Extend", pos);
                telemetry.update();
                if (pos < 2334) {
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

        double rPower;
        double rPos;
        double rLastError = 0;
        double rError;
        double rTarget = 0; //505

        double p = 0.005;//0.0025; // 0.01
        double i = 0.000125;//0.000125; // 0.000125
        double d = 0.011;//0.035; // 0.03
        double integral = 0;
        double derivative;

        public boolean finish = false;
        public boolean rotateCorrect;

        private DcMotorEx armRotation;

        public armRotation(HardwareMap hardwareMap) {
            armRotation = hardwareMap.get(DcMotorEx.class, "armRotation");
            armRotation.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        public class Angle implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                int pos = armRotation.getCurrentPosition();
                packet.put("armRotationPos", pos);
                rPos = armRotation.getCurrentPosition();
                rError = rTarget - rPos;
                integral += rError;
                derivative = rError - rLastError;
                rPower = p * rError + i * integral + d * derivative;
                if (rPower > 1) {rPower=1;}
                if (rPower < -1) {rPower=-1;}
                armRotation.setPower(rPower);
                rLastError = rError;
                rotateCorrect = rPos < rTarget + 25 && rPos > rTarget - 0.25;
                telemetry.addData("Power:", rPower);
                telemetry.addData("Pos:", rPos);
                telemetry.addData("Error:", rError);
                telemetry.addData("Integral:", integral);
                telemetry.addData("Derivative:", derivative);
                telemetry.addData("Correct", rotateCorrect);
                telemetry.update();
                return !finish;
            }
        }

        public Action angle() {return new Angle();}

        public class Normal implements Action {
            private boolean initialized = false;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    rTarget = 10;
                    initialized = true;
                }
                int pos = -armRotation.getCurrentPosition();
                packet.put("liftPos", pos);
                return !rotateCorrect;
            }
        }

        public Action normal() {
            return new autoL.armRotation.Normal();
        }

        public class Bucket implements Action {
            private boolean initialized = false;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    rTarget = 534; //534
                    initialized = true;
                }
                int pos = -armRotation.getCurrentPosition();
                packet.put("armRotationPos", pos);
                return !rotateCorrect;
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
                    rTarget = 98; //100
                    initialized = true;
                }
                int pos = -armRotation.getCurrentPosition();
                packet.put("armRotationPos", pos);
                return !rotateCorrect;
            }
        }

        public Action grab() {
            return new Grab();
        }

        public class Reset implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                armRotation.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                armRotation.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                return false;
            }
        }

        public Action reset() {
            return new Reset();
        }

        public class Finish implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                finish = true;
                return false;
            }
        }

        public Action finish(){return new Finish();}

//        public class pUP implements Action {
//            @Override
//            public boolean run(@NonNull TelemetryPacket packet) {
//                p = 0.008; //0.01
//                return false;
//            }
//        }
//
//        public Action pUp(){return new pUP();}
//
//        public class pDOWN implements Action {
//            @Override
//            public boolean run(@NonNull TelemetryPacket packet) {
//                p = 0.005;
//                return false;
//            }
//        }
//
//        public Action pDown(){return new pDOWN();}
//
//        public class iUP implements Action {
//            @Override
//            public boolean run(@NonNull TelemetryPacket packet) {
//                i = 0.001;
//                return false;
//            }
//        }
//
//        public Action iUp(){return new iUP();}
//
//        public class iDOWN implements Action {
//            @Override
//            public boolean run(@NonNull TelemetryPacket packet) {
//                i = 0.0005;
//                return false;
//            }
//        }
//
//        public Action iDown(){return new iDOWN();}

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
                claw.setPosition(0.7);
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
                rotation.setPosition(1);
                return false;
            }
        }
        public Action in() {
            return new rotation.in();
        }
    }

    public class hang {
        private DcMotorEx hang;
        public hang(HardwareMap hardwareMap) {
            hang = hardwareMap.get(DcMotorEx.class, "hang");
            hang.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        public class Raise implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                hang.setPower(1);
                hang.setTargetPosition(6000);
                hang.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                return hang.getCurrentPosition() < 5999;
            }
        }
        public Action Raise(){return new Raise();}
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
                .strafeToLinearHeading(new Vector2d(-58, -51), Math.toRadians(45)); //-52, -51

        TrajectoryActionBuilder bucket12 = bucket1.endTrajectory().fresh() //TODO: make this work
                .strafeToConstantHeading(new Vector2d(-62, -55)); //-59.75, -57.75

        TrajectoryActionBuilder bucket13 = bucket12.endTrajectory().fresh()
                .strafeToConstantHeading(new Vector2d(-52, -52));

        TrajectoryActionBuilder grab1 = bucket13.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(-51.5, -37.9), Math.toRadians(90)); //-56 -39 100

        TrajectoryActionBuilder bucket2 = grab1.endTrajectory().fresh()
                .setReversed(true)
                .strafeToLinearHeading(new Vector2d(-58, -49), Math.toRadians(45)); //-52, -51, 50

        TrajectoryActionBuilder bucket22 = bucket2.endTrajectory().fresh()
                .setTangent(0)
                .strafeToConstantHeading(new Vector2d(-62, -55)); //-59, -57

        TrajectoryActionBuilder bucket23 = bucket22.endTrajectory().fresh()
                .strafeToConstantHeading(new Vector2d(-52, -52));

        TrajectoryActionBuilder grab2 = bucket23.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(-66.95, -34), Math.toRadians(90)); //-66.5, -39.75, 90

        TrajectoryActionBuilder bucket3 = grab2.endTrajectory().fresh()
                .setReversed(true)
                .strafeToLinearHeading(new Vector2d(-58, -47), Math.toRadians(45));

        TrajectoryActionBuilder bucket32 = bucket3.endTrajectory().fresh()
                .setTangent(0)
                .strafeToConstantHeading(new Vector2d(-62, -55));
//                .lineToXConstantHeading(-62);

        TrajectoryActionBuilder bucket33 = bucket32.endTrajectory().fresh()
                .strafeToConstantHeading(new Vector2d(-52, -52));

        TrajectoryActionBuilder grab3 = bucket33.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(-60,-27.3), Math.toRadians(180)); // -61.7 -31.1

        TrajectoryActionBuilder bucket4 = grab3.endTrajectory().fresh()
                .setReversed(true)
                .strafeToLinearHeading(new Vector2d(-59, -52), Math.toRadians(45));

        TrajectoryActionBuilder bucket42 = bucket4.endTrajectory().fresh()
                .setTangent(0)
                .strafeToConstantHeading(new Vector2d(-62, -55));

        TrajectoryActionBuilder bucket43 = bucket42.endTrajectory().fresh()
                .strafeToConstantHeading(new Vector2d(-52, -52));

        TrajectoryActionBuilder park = bucket43.endTrajectory().fresh()
                .turn(Math.toRadians(135))
                .strafeToConstantHeading(new Vector2d(-30, -4))
                .strafeToConstantHeading(new Vector2d(-22, -4));

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
        Action Bucket32 = bucket32.build();
        Action Bucket33 = bucket33.build();
        Action Grab3 = grab3.build();
        Action Bucket4 = bucket4.build();
        Action Bucket42 = bucket42.build();
        Action Bucket43 = bucket43.build();
        Action Park = park.build();

        VelConstraint baseVelConstraint = new MinVelConstraint(Collections.singletonList(
                new TranslationalVelConstraint(40.0)
        ));
        AccelConstraint baseAccelConstraint = new ProfileAccelConstraint(-10.0, 25.0);

        //Blue + Red left auto
        //Hangs 1 on bar, puts two yellow into bucket (will be 3)
        Actions.runBlocking(
            new ParallelAction(
                armRotation.angle(),
                new SequentialAction(
                    armExtend.reset(),
                    armRotation.reset(),
                    rotation.in(),
                    claw.closeClaw(),
                    wrist.up(),
                    new ParallelAction(
                        Bucket1,
                        new SequentialAction(
                            new SleepAction(0.75),
                            armRotation.bucket()
                        )
                    ),
                    new SequentialAction(
                        new SleepAction(0.15),
                        armExtend.bucket(),
                        Bucket12,
                        armRotation.bucket(),
//                            new SleepAction(0.1), // 0.3
                        claw.openClaw(),
                        new SleepAction(0.15),
                        new ParallelAction(
                            Bucket13,
                            new SequentialAction(
                                new SleepAction(0.15),
                                armExtend.normal()
                            )
                        ),
                        new ParallelAction(
                            armRotation.grab(),
                            Grab1
                        ),
                        new SleepAction(0.15),
                        new ParallelAction(
                            armExtend.grab(),
                            wrist.down()
                        ),
                        claw.closeClaw(),
                        new SleepAction(0.15),
                        new ParallelAction(
                            wrist.up(),
                            armExtend.normal()
                        ),

                        new ParallelAction(
                            armRotation.bucket(),
                            Bucket2,
                            new SequentialAction(
                                new SleepAction(0.6),
                                armExtend.bucket(),
                                Bucket22,
//                                    new SleepAction(0.1), //0.3
                                claw.openClaw(),
                                new SleepAction(0.15),
                                new ParallelAction(
                                    Bucket23,
                                    new SequentialAction(
                                        new SleepAction(0.15),
                                        armExtend.normal()
                                    )
                                ),
                                new ParallelAction(
                                    armRotation.grab(),
                                    Grab2
                                ),
                                new SleepAction(0.15),
                                new ParallelAction(
                                    armExtend.grab(),
                                    wrist.down()
                                ),
                                claw.closeClaw(),
                                new SleepAction(0.15),
                                new ParallelAction(
                                    wrist.up(),
                                    armExtend.normal()
                                ),
                                new ParallelAction(
                                    armRotation.bucket(),
                                    Bucket3,
                                    new SequentialAction(
                                        new SleepAction(0.6),
                                        armExtend.bucket(),
                                        Bucket32,
//                                    new SleepAction(0.1), //0.3
                                        claw.openClaw(),
                                        new SleepAction(0.15),
                                        new ParallelAction(
                                            Bucket33,
                                            new SequentialAction(
                                                new SleepAction(0.15),
                                                armExtend.normal()
                                            )
                                        ),
                                        new ParallelAction(
                                            armRotation.grab(),
                                            Grab3
                                        ),
                                        new SleepAction(0.15),
                                        new ParallelAction(
                                            armExtend.grab(),
                                            wrist.down()
                                        ),
                                        claw.closeClaw(),
                                        new SleepAction(0.15),
                                        new ParallelAction(
                                            wrist.up(),
                                            armExtend.normal()
                                        ),

                                        new ParallelAction(
                                            armRotation.bucket(),
                                            Bucket4,
                                            new SequentialAction(
                                                new SleepAction(0.6),
                                                armExtend.bucket(),
                                                Bucket42,
                                                claw.openClaw(),
                                                new SleepAction(0.15),
                                                new ParallelAction(
                                                    Bucket43,
                                                    new SequentialAction(
                                                        new SleepAction(0.15),
                                                        armExtend.normal()
                                                    )
                                                ),
                                                new ParallelAction(
                                                    armRotation.normal(),
                                                    Park
                                                )
                                            )
                                        ),

                                        armRotation.finish()
                                    )
                                )
                            )
                        )
                    )
                )
            )
        );
    }
}