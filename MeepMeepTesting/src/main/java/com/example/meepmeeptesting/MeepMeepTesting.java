package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(75, 75, Math.toRadians(360), Math.toRadians(360), 18)
                .build();

//        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(0, 0, 0))
//                .lineToX(30)
//                .turn(Math.toRadians(90))
//                .lineToY(30)
//                .turn(Math.toRadians(90))
//                .lineToX(0)
//                .turn(Math.toRadians(90))
//                .lineToY(0)
//                .turn(Math.toRadians(90))
//                .build());

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(24, -63, Math.toRadians(90))) //autoR
                .setTangent(90)
                .splineToConstantHeading(new Vector2d(6, -35), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(6, -39), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(39, -42), Math.toRadians(90))
                .setTangent(90)
                .splineToConstantHeading(new Vector2d(39, -14), Math.toRadians(90)) //270
                .splineToConstantHeading(new Vector2d(49, -14), Math.toRadians(90)) // 270
                .turnTo(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(45, -60.25), Math.toRadians(270))
//                        new TranslationalVelConstraint(40), new ProfileAccelConstraint(-10, 40))
                .setTangent(270)
                .strafeToSplineHeading(new Vector2d(3, -35), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(3, -39), Math.toRadians(90))
                .setTangent(90)
                .setReversed(true)
                .strafeToSplineHeading(new Vector2d(42, -58), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(45, -60), Math.toRadians(270))
                .setTangent(270)
                .strafeToSplineHeading(new Vector2d(0, -35), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(0, -39), Math.toRadians(90))
                .strafeToConstantHeading(new Vector2d(48, -63))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}