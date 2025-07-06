package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.autoL;

@Disabled
@Autonomous
public class hanging extends autoL {

    //this is preliminary code for a two arm auto

    public boolean sampleClipped = false;
    public boolean sampleHung = false;
    public boolean possession = false;

    //setup

    public void runOpMode() {

        //main loop
        while (opModeIsActive()) {



            //wait for notification of sample from fishing

            //grab sample out of other arm

            //clip the sample

            //extend and rotate arm (at same time to be more efficient) to the high bar

            //reset clipping mechanism somewhere in here

            //clip sample onto high bar

            //return arm to normal

        }

    }



}
