//package org.firstinspires.ftc.teamcode;
//
//import androidx.annotation.NonNull;
//
//import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
//import com.acmerobotics.roadrunner.Action;
//import com.acmerobotics.roadrunner.Pose2d;
//import com.acmerobotics.roadrunner.SequentialAction;
//import com.acmerobotics.roadrunner.Vector2d;
//import com.acmerobotics.roadrunner.ftc.Actions;
//import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorEx;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//import com.qualcomm.robotcore.hardware.Servo;
//
//import org.apache.commons.math3.random.JDKRandomGenerator;
//import org.firstinspires.ftc.teamcode.MecanumDrive;
//import org.firstinspires.ftc.teamcode.rrTest;
//
//@Autonomous
//public class rr1 extends LinearOpMode {
//
//    public class armExtend {
//        private DcMotorEx armExtend;
//
//        public armExtend(HardwareMap hardwareMap) {
//            armExtend = hardwareMap.get(DcMotorEx.class, "armExtend");
//            armExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        }
//
//        public class Hang implements Action {
//
//            private boolean initialized = false;
//
//            public boolean run(@NonNull TelemetryPacket packet) {
//                if (!initialized) {
//                    armExtend.setPower(-0.5);
//                    initialized = true;
//                }
//
//                double extendPos = armExtend.getCurrentPosition();
//                packet.put("armExtendPos", extendPos);
//
//                if (extendPos > -1240) {
//                    return true;
//                } else {
//                    armExtend.setPower(0);
//                    return false;
//                }
//            }
//
//        }
//
//        public Action extendHang() {
//            return new rr1.armExtend.Hang();
//        }
//    }
//
//    public class armRotation {
//        private DcMotorEx armRotation;
//
//        public armRotation(HardwareMap hardwareMap) {
//            armRotation = hardwareMap.get(DcMotorEx.class, "armRotation");
//            armRotation.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        }
//
//        public class Hang implements Action {
//
//            private boolean initialized = false;
//
//            public boolean run(@NonNull TelemetryPacket packet) {
//                if (!initialized) {
//                    armRotation.setPower(0.3);
//                    initialized = true;
//                }
//
//                double pos = armRotation.getCurrentPosition();
//                packet.put("armRotationPos", pos);
//                if (pos > -320.0) {
//                    return true;
//                } else {
//                    armRotation.setPower(0);
//                    return false;
//                }
//
//            }
//
//        }
//
//        public Action hang() {
//            return new Hang();
//        }
//
//        public class Normal implements Action {
//            private boolean initialized = false;
//
//            @Override
//            public boolean run(@NonNull TelemetryPacket packet) {
//                if (!initialized) {
//                    armRotation.setPower(-0.3);
//                    initialized = true;
//                }
//
//                double pos = armRotation.getCurrentPosition();
//                packet.put("liftPos", pos);
//                if (pos > 100.0) {
//                    return true;
//                } else {
//                    armRotation.setPower(0);
//                    return false;
//                }
//            }
//        }
//
//        public Action normal() {
//            return new rr1.armRotation.Normal();
//        }
//    }
//
//    public class Claw {
//        private Servo claw;
//
//        public Claw(HardwareMap hardwareMap) {
//            claw = hardwareMap.get(Servo.class, "claw");
//        }
//
//        public class CloseClaw implements Action {
//            @Override
//            public boolean run(@NonNull TelemetryPacket packet) {
//                claw.setPosition(0.55);
//                return false;
//            }
//        }
//        public Action closeClaw() {
//            return new rr1.Claw.CloseClaw();
//        }
//
//        public class OpenClaw implements Action {
//            @Override
//            public boolean run(@NonNull TelemetryPacket packet) {
//                claw.setPosition(1.0);
//                return false;
//            }
//        }
//        public Action openClaw() {
//            return new rr1.Claw.OpenClaw();
//        }
//
//    }
//
//    public class Wrist {
//        private Servo wrist;
//
//        public Wrist(HardwareMap hardwareMap) {
//            wrist = hardwareMap.get(Servo.class, "wrist");
//        }
//
//        public class wristUp implements Action {
//            @Override
//            public boolean run(@NonNull TelemetryPacket packet) {
//                wrist.setPosition(1);
//                return false;
//            }
//        }
//        public Action wristUp() {
//            return new autonomous.Wrist.wristUp();
//        }
//
//        public class wristDown implements Action {
//            @Override
//            public boolean run(@NonNull TelemetryPacket packet) {
//                wrist.setPosition(0.35);
//                return false;
//            }
//        }
//        public Action wristDown() {
//            return new rr1.Wrist.wristDown();
//        }
//    }
//
//    public void runOpMode() {
//
//        // instantiate your MecanumDrive at a particular pose.
//        Pose2d initialPose = new Pose2d(-24, -63, Math.toRadians(90));
//        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
//
//        rr1.Claw claw = new autonomous.Claw(hardwareMap);
//
//        autonomous.armRotation armRotation = new autonomous.armRotation(hardwareMap);
//
//        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
//                .setReversed(false)
//                .strafeTo(new Vector2d(0, -34))
//                .waitSeconds(3);
//
//        TrajectoryActionBuilder tab2 = drive.actionBuilder(initialPose)
//
//                .setReversed(true)
//                .splineTo(new Vector2d(-51, -50), Math.toRadians(225))
//                .waitSeconds(3);
//
//        waitForStart();
//
//        if (isStopRequested()) return;
//
//        Action TAB1 = tab1.build();
//        Action TAB2 = tab2.build();
//
//        Actions.runBlocking(
//                new SequentialAction(
//                        TAB1,
//                        Wrist.wristDown(),
//                        armRotation.hang(),
//                        armExtend.extendHang(),
//                        armRotation.normal(),
//                        claw.openClaw(),
//                        TAB2
//                )
//        );
//
//    }
//
//}