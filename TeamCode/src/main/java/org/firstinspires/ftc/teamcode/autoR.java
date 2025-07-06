package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.AccelConstraint;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.AngularVelConstraint;
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

import java.util.Arrays;

@Autonomous
public class autoR extends LinearOpMode {

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
                    armExtend.setPower(1);
                    armExtend.setTargetPosition(1080); //1080
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
                    armExtend.setTargetPosition(-12);
                    armExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    initialized = true;
                }

                double pos = armExtend.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos > -12) {
                    return true;
                } else {
                    armExtend.setPower(0);
                    return false;
                }
            }
        }

        public Action normal() {
            return new autoR.armExtend.Normal();
        }

        public class Grab implements Action {

            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    armExtend.setPower(1);
                    armExtend.setTargetPosition(920);
                    armExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    initialized = true;
                }

                double pos = armExtend.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos < 920) {
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
                    armExtend.setTargetPosition(2450);
                    armExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    initialized = true;
                }

                double pos = armExtend.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos < 2450) {
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
                    armExtend.setPower(1);
                    armExtend.setTargetPosition(1000);
                    armExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    initialized = true;
                }

                double pos = armExtend.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos < 1000) {
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
                armExtend.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
        double rTarget = 0;

        double p = 0.006; //0.0025
        double i = 0.0002; //0.00025
        double d = 0.012; //0.03
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
                rotateCorrect = rPos < rTarget + 3 && rPos > rTarget - 1;
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

        public Action angle() {return new autoR.armRotation.Angle();}

        public class Hang implements Action {
            private boolean initialized = false;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    rTarget = 385;
                    initialized = true;
                }
                int pos = armRotation.getCurrentPosition();
                packet.put("armRotationPos", pos);
                return !rotateCorrect;
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
                    rTarget = 10;
                    initialized = true;
                }
                int pos = armRotation.getCurrentPosition();
                packet.put("liftPos", pos);
                return !rotateCorrect;
            }
        }

        public Action normal() {
            return new autoR.armRotation.Normal();
        }

        public class Hang2 implements Action {
            private boolean initialized = false;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    rTarget = 325;
                    initialized = true;
                }
                int pos = armRotation.getCurrentPosition();
                packet.put("liftPos", pos);
                return !rotateCorrect;
            }
        }

        public Action hang2() {
            return new autoR.armRotation.Hang2();
        }

        public class Human implements Action {
            private boolean initialized = false;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    rTarget = 105; //103
                    initialized = true;
                }
                int pos = armRotation.getCurrentPosition();
                packet.put("liftPos", pos);
                return !rotateCorrect;
            }
        }

        public Action human() {
            return new autoR.armRotation.Human();
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

        public Action finish(){return new autoR.armRotation.Finish();}

    }

    public class Claw {
        private Servo claw;

        public Claw(HardwareMap hardwareMap) {
            claw = hardwareMap.get(Servo.class, "claw");
        }

        public class CloseClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                claw.setPosition(0.9375);
                return false;
            }
        }
        public Action closeClaw() {
            return new Claw.CloseClaw();
        }

        public class OpenClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                claw.setPosition(0.75);
                return false;
            }
        }
        public Action openClaw() {
            return new Claw.OpenClaw();
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
                rotation.setPosition(1);
                return false;
            }
        }
        public Action out() {
            return new autoR.rotation.out();
        }

        public class in implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                rotation.setPosition(1);
                return false;
            }
        }
        public Action in() {
            return new autoR.rotation.in();
        }
    }

    public void runOpMode() {

        // instantiate your MecanumDrive at a particular pose.
        Pose2d initialPose = new Pose2d(24, -63, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        autoR.Claw claw = new autoR.Claw(hardwareMap);

        autoR.armRotation armRotation = new autoR.armRotation(hardwareMap);

        autoR.armExtend armExtend = new autoR.armExtend(hardwareMap);

        autoR.wrist wrist = new autoR.wrist(hardwareMap);

        autoR.rotation rotation = new autoR.rotation(hardwareMap);

        TrajectoryActionBuilder hang11 = drive.actionBuilder(initialPose)
                .setTangent(90)
                .strafeToLinearHeading(new Vector2d(6, -39), Math.toRadians(90)); // 6, -37.65

        TrajectoryActionBuilder hang12 = hang11.endTrajectory().fresh()
                .strafeToConstantHeading(new Vector2d(6, -41.75));

        TrajectoryActionBuilder grab1 = hang12.endTrajectory().fresh()
                .strafeToConstantHeading(new Vector2d(39, -42))
                .setTangent(90)
                .splineToConstantHeading(new Vector2d(39, -14), Math.toRadians(90)) //270
                .splineToConstantHeading(new Vector2d(49, -14), Math.toRadians(90)) // 270
                .turnTo(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(49, -61.25), Math.toRadians(270)); // 50 -62

        TrajectoryActionBuilder hang21 = grab1.endTrajectory().fresh()
                .setTangent(270)
                .strafeToSplineHeading(new Vector2d(3, -45.75), Math.toRadians(95)); // 3 -43.4 95

        TrajectoryActionBuilder hang22 = hang21.endTrajectory().fresh()
                .strafeTo(new Vector2d(3, -48.5)); // 3 -44

        TrajectoryActionBuilder grab2 = hang22.endTrajectory().fresh()
                .setTangent(90)
                .setReversed(true)
                .strafeTo(new Vector2d(5, -48))
                .strafeToSplineHeading(new Vector2d(46.5, -54), Math.toRadians(270)) // 47 -58 262
                .strafeTo(new Vector2d(46.5, -60.5)); //47 -67

        TrajectoryActionBuilder hang31 = grab2.endTrajectory().fresh()
                .setTangent(270)
                .strafeToSplineHeading(new Vector2d(0, -46), Math.toRadians(97.5)); // 0 -43.35 95

        TrajectoryActionBuilder hang32 = hang31.endTrajectory().fresh()
                .strafeToConstantHeading(new Vector2d(0, -49)); // 0 -41

//        TrajectoryActionBuilder grab3 = hang32.endTrajectory().fresh()
//                .setTangent(90)
//                .strafeToSplineHeading(new Vector2d(41, -60.25), Math.toRadians(270));

//        TrajectoryActionBuilder hang41 = grab3.endTrajectory().fresh()
//                .setTangent(90)
//                .strafeToSplineHeading(new Vector2d(-1, -37), Math.toRadians(90));
//
//        TrajectoryActionBuilder hang42 = hang41.endTrajectory().fresh()
//                .strafeTo(new Vector2d(-1, -40.5));

        TrajectoryActionBuilder park = hang32.endTrajectory().fresh()
//                .splineToConstantHeading(new Vector2d(39, -35), 90)
//                .strafeToConstantHeading(new Vector2d(66, 3),
//                    new TranslationalVelConstraint(100), new ProfileAccelConstraint(-100, 100))
                .strafeToConstantHeading(new Vector2d(66, -63),
                    new TranslationalVelConstraint(100), new ProfileAccelConstraint(-100, 100));

        waitForStart();

        if (isStopRequested()) return;

        Action Hang11 = hang11.build();
        Action Hang12 = hang12.build();
        Action Grab1 = grab1.build();
        Action Hang21 = hang21.build();
        Action Hang22 = hang22.build();
        Action Grab2 = grab2.build();
        Action Hang31 = hang31.build();
        Action Hang32 = hang32.build();
        Action Park = park.build();

        VelConstraint baseVelConstraint = new MinVelConstraint(Arrays.asList(
                new TranslationalVelConstraint(40.0),
                new AngularVelConstraint(Math.PI/2)
        ));
        AccelConstraint baseAccelConstraint = new ProfileAccelConstraint(-10.0, 25.0);

        //Blue + Red right auto
        Actions.runBlocking(
                new ParallelAction(
                        armRotation.reset(),
                        armExtend.reset(),
                        armRotation.angle(),
                        new SequentialAction(
                                new ParallelAction(
                                        claw.closeClaw(),
                                        rotation.in(),
                                        wrist.up(),
                                        Hang11,
                                        armRotation.hang()
                                ),
                                new ParallelAction(
                                        armExtend.hang(),
                                        new SequentialAction(
                                                new SleepAction(0.8),
                                                wrist.down())),
                                new ParallelAction(
                                        armRotation.hang2(),
                                        new SleepAction(0.1),
                                        Hang12,
                                        new SequentialAction(
                                                new SleepAction(0.25),
                                                armExtend.hang2()
                                        )),
                                claw.openClaw(),

                                new ParallelAction(
                                        claw.openClaw(),
                                        wrist.up(),
                                        new SequentialAction(
                                            armExtend.normal(),
                                            new ParallelAction(
                                                Grab1,
                                                armRotation.human()))),
                                new SleepAction(0.25),
                                claw.closeClaw(),
                                new SleepAction(0.25),
                                new ParallelAction(
                                        armRotation.hang(),
                                        Hang21,
                                        new SequentialAction(
                                                new SleepAction(1.25),
                                                armExtend.hang(),
                                                new SleepAction(0.4),
                                                wrist.down())),
                                new ParallelAction(
                                        armRotation.hang2(),
                                        new SleepAction(0.1),
                                        Hang22,
                                        new SequentialAction(
                                                new SleepAction(0.25),
                                                armExtend.hang2())),
                                claw.openClaw(),
                                new ParallelAction(
                                        wrist.up(),
                                        armExtend.normal(),
                                        new SleepAction(1)),

                                new ParallelAction(
                                        Grab2,
                                        armRotation.human()),
                                new SleepAction(0.25),
                                claw.closeClaw(),
                                new SleepAction(0.25),

                                armRotation.hang(),
                                Hang31,
                                new ParallelAction(
                                        armExtend.hang(),
                                        new SequentialAction(
                                                new SleepAction(0.8),
                                                wrist.down())),
                                new ParallelAction(
                                        armRotation.hang2(),
                                        Hang32,
                                        new SequentialAction(
                                                new SleepAction(0.25),
                                                armExtend.hang2())),
                                claw.openClaw(),
                                wrist.up(),
                                new SequentialAction(
                                        new ParallelAction(
                                                armExtend.normal(),
                                                new SleepAction(1.5)),
                                        new ParallelAction(
                                        armRotation.normal(),
                                        Park)
                                ),
                                armRotation.finish()
                        )
                )
        );
    }
}