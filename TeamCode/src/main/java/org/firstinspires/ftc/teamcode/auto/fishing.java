package org.firstinspires.ftc.teamcode.auto;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class fishing extends LinearOpMode{

    //this program is preliminary code for a two arm auto

    public void runOpMode() {

        //setup (camera too)

        //camera looks for clumps of blue or red samples for highest chance of getting one first try

        //if biggest clump is on the other half go to next biggest clump

        waitForStart();

        while (opModeIsActive()) {

            //main loop

            //camera looks for clumps of blue or red samples for highest chance of getting one first try

            //extend arm once sample has been removed

            //color sensor looks for blue or red sample below grabber

            //if one isn't directly below keep looping (while)

            //grab sample once confirmed and robot does not have another

            //return arm

            //notify hanging

            //immediately start looking for another sample

        }

    }

}
