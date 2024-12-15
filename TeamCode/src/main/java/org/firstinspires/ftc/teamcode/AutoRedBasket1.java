package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.AUTOconstant.AUTORedNetX;
import static org.firstinspires.ftc.teamcode.AUTOconstant.AUTORedNetY;
import static org.firstinspires.ftc.teamcode.AUTOconstant.AUTOfrontIntakePickupLength;
import static org.firstinspires.ftc.teamcode.AUTOconstant.AUTOredSample1X;
import static org.firstinspires.ftc.teamcode.AUTOconstant.AUTOredSample1Y;
import static org.firstinspires.ftc.teamcode.AUTOconstant.AUTOredSample2X;
import static org.firstinspires.ftc.teamcode.AUTOconstant.AUTOredSample2Y;
import static org.firstinspires.ftc.teamcode.AUTOconstant.AUTOredSample3X;
import static org.firstinspires.ftc.teamcode.AUTOconstant.AUTOredSample3Y;
import static org.firstinspires.ftc.teamcode.AUTOconstant.AUTOstartRedNetX;
import static org.firstinspires.ftc.teamcode.AUTOconstant.AUTOstartRedNetY;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware.HardwareNoDriveTrainRobot;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.*;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.PoseUpdater;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;
import org.firstinspires.ftc.teamcode.pedroPathing.util.DashboardPoseTracker;
import org.firstinspires.ftc.teamcode.pedroPathing.util.Timer;

/**
 * adapted from @author Baron Henderson - 20077 The Indubitables
 * @version 2.0, 9/8/2024
 * based on example at: https://github.com/AnyiLin/10158-Centerstage/tree/master/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/competition/autonomous
 *
 * There are two major types of paths components: BezierCurves and BezierLines.
 * * BezierCurves are curved, and require > 3 points. There are the start and end points, and the control points.
 *   - Control points manipulate the curve between the start and end points.
 *   - A good visualizer for this is [this](https://www.desmos.com/calculator/3so1zx0hcd).
 * * BezierLines are straight, and require 2 points. There are the start and end points.
 * * Pose = (X,Y, Heading as Math.toRadians(degrees))...[if no Heading, then it is input as 0 degree]
 * * Point = (Pose) or = (pose.getX(), pose.getY(), Point.CARTESIAN) or = (X, Y, Point.CARTESIAN)
 *
 * Path: Represents a single movement, which can be a curve (BezierCurve) or a straight line (BezierLine).
 * PathChain: Contains Path(s) within it.
 * Interpolations: Define how the robot adjusts heading (rotation) throughout a path.
 * Timeout Constraints: Limit the time the robot spends attempting to complete a path.
 *
 *
 * 11/29/2024: start, ChainPath--start to Sample 3 and then to Net
 *             include FTC Dashboard
 * 12/2/2024 OT:  modify from PedroPathing.com auto example
 * 12/15/2024 OT: correct strafe pod anchoring; erase negative sign on straf tick/inch
 *                  and erase reverse of strafe reading; reTune all translational, drive and heading
 *                  distanceCenterToIntakePickup = 23 inches;
 *                  distanceCenterToOuttakeBasketDropOff = 13 inches
 *                  distanceCenterToSideWall = 6.5 inches
 *                  distanceCentertobackRobot = 9 inches
 *                  distanceCentertobackWallPickup = 13 inches
 *12/20/2024 MT: add subsystem actions and add pathChain to first sample pickup
 *
 */



@Config
@Autonomous(name = "Auto Red Basket v1.0", group = "Auto")
public class AutoRedBasket1 extends OpMode {
    private Telemetry telemetryA;

    private PoseUpdater poseUpdater;
    private DashboardPoseTracker dashboardPoseTracker;

    private String navigation;
    //    public ClawSubsystem claw;
    private HuskyLens huskyLens;


    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;
    // private Pose sample1Pose, sample2Pose, sample3Pose, sample4Pose, sample5Pose, sample6Pose, redNet, blueNet;
    private PathChain preLoadScore;
    private PathChain pickupSample3, pickupSample2, pickupSample1;
    private PathChain scoreSample3, scoreSample2, scoreSample1;



    HardwareNoDriveTrainRobot autoRobot = new HardwareNoDriveTrainRobot();    //TODO: will this interfere with follower(hardwareMap)? in .init

    //private Pose startPose = new Pose(23.6 * 5 + 16, 39.75, Math.toRadians(90));  //(AUTOstartRedNetX, AUTOstartRedNetY, Math.toRadians(90));
    private Pose pickup1Pose = new Pose(AUTOredSample1X + AUTOfrontIntakePickupLength, AUTOredSample1Y, Math.toRadians(180));
    private Pose pickup2Pose = new Pose(AUTOredSample2X + AUTOfrontIntakePickupLength, AUTOredSample2Y, Math.toRadians(180));
    private Pose pickup3Pose = new Pose(AUTOredSample3X + AUTOfrontIntakePickupLength, AUTOredSample3Y, Math.toRadians(180));
    //private Pose redScorePose = new Pose(23.6 * 5 + 6, 14, Math.toRadians(135));      //(AUTORedNetX, AUTORedNetY, Math.toRadians(135));;


    private Pose startPose = new Pose(134, 39.75, Math.toRadians(90));
    private Pose redScorePose = new Pose(124, 14, Math.toRadians(135));

    //private Pose startPose = new Pose(144, 0, Math.toRadians(90));
    //private Pose redScorePose = new Pose(104, 0, Math.toRadians(90));
    //private Pose startPose = new Pose(144, 0, Math.toRadians(180));
    //private Pose redScorePose = new Pose(104, 0, Math.toRadians(180));



    public void buildPaths() {
        //preLoadScore = new Path(new BezierLine(new Point(startPose), new Point(redScorePose)));
        //preLoadScore.setLinearHeadingInterpolation(startPose.getHeading(), redScorePose.getHeading());

        //pathChain for preLoadScore position
        preLoadScore = follower.pathBuilder()
                  .addPath(new BezierLine(new Point(startPose), new Point(redScorePose)))
                  .setLinearHeadingInterpolation(startPose.getHeading(), redScorePose.getHeading())
                  .build();

//        creating pathChain
        pickupSample3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(redScorePose), new Point(pickup3Pose)))
                .setLinearHeadingInterpolation(redScorePose.getHeading(), pickup3Pose.getHeading())            //one Heading only
                .build();
//
        scoreSample3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup3Pose), new Point(redScorePose)))
                .setLinearHeadingInterpolation(pickup3Pose.getHeading(), redScorePose.getHeading())            //one Heading only
                .build();
    }






    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:     //goto basket and score
                //follower.followPath(preLoadScore);

                //TODO:start to raise outtake slider, start rotating outtake when halfway up
                autoRobot.Outtake.closeClaw();
                autoRobot.Outtake.highBasket();
//                autoRobot.Outtake.leftSlideSetPositionPower(3400,1);
//                autoRobot.Outtake.rightSlideSetPositionPower(3400,1);
//                if (pathTimer.getElapsedTimeSeconds()>5){
//                    autoRobot.Outtake.highBasket();
//                }
                follower.followPath(preLoadScore, true);
                setPathState(1);
                break;
            case 1:     //goto specimen 3 and pick it up
//                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if (follower.getPose().getX() > (redScorePose.getX() - 1) && follower.getPose().getY() > (redScorePose.getY() - 1) && pathTimer.getElapsedTimeSeconds()>10) {
                    /* Score Preload */
                    //TODO: rotate outtake and open claw/outtake to drop sample
                    autoRobot.Outtake.openClaw();
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    //then go to next path--go to Specimen 3 pickup position
                    follower.followPath(pickupSample3,false);
                    setPathState(2);
                }
                break;
//            case 2:     //goto basket and score
//                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup1Pose's position */
//                if (follower.getPose().getX() > (pickup1Pose.getX() - 1) && follower.getPose().getY() > (pickup1Pose.getY() - 1)) {
//                    /* Grab Sample */
//                    //TODO: do something
//                    // reset outtake arm and slides
//                    autoRobot.Outtake.readyPosition();
//                    autoRobot.Outtake.groundPositionOpen();
//                    // Extend intake
//                    autoRobot.Intake.intakeSlideMID();
//                    // Move intake down
//                     autoRobot.Intake.intakeDOWN();
//                    // Intake sample
//                    autoRobot.Intake.intakeIN();
//                    /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
//                    follower.followPath(scoreSample3, true);
//                    setPathState(3);
//                }
//                break;
        }
    }


    /** These change the states of the paths and actions
     * It will also reset the timers of the individual switches **/
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

//**********************************************************************************
    @Override
    public void init() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        autoRobot.init(hardwareMap);   //TODO: check if conflicts with Follower(hardwareMap);note hardwareMap is default and part of FTC Robot Controller HardwareMap class

        poseUpdater = new PoseUpdater(hardwareMap);
        dashboardPoseTracker = new DashboardPoseTracker(poseUpdater);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);
        buildPaths();

        autoRobot.Outtake.groundPositionClose();


        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetryA.update();
    }

    @Override
    public void init_loop() {


    }

    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);

    }

//**********************************************************************************
    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();
        // telemetryA.addLine("going forward");
        follower.telemetryDebug(telemetryA);
        telemetryA.addLine("");
        telemetryA.addLine("");
        telemetryA.addData("path state", pathState);
        telemetryA.addData("x", follower.getPose().getX());
        telemetryA.addData("y", follower.getPose().getY());
        telemetryA.addData("heading", Math.toDegrees(follower.getPose().getHeading()));
       //telemetry.update();


        //poseUpdater.update();
        //dashboardPoseTracker.update();
        telemetryA.update();
    }


    @Override
    public void stop() {
    }
}



/* There are two major types of paths components: BezierCurves and BezierLines.
 *    * BezierCurves are curved, and require >= 3 points. There are the start and end points, and the control points.
 *    - Control points manipulate the curve between the start and end points.
 *    - A good visualizer for this is [this](https://pedro-path-generator.vercel.app/).
 *    * BezierLines are straight, and require 2 points. There are the start and end points.
 * Paths have can have heading interpolation: Constant, Linear, or Tangential
 *    * Linear heading interpolation:
 *    - Pedro will slowly change the heading of the robot from the startHeading to the endHeading over the course of the entire path.
 *    * Constant Heading Interpolation:
 *    - Pedro will maintain one heading throughout the entire path.
 *    * Tangential Heading Interpolation:
 *    - Pedro will follows the angle of the path such that the robot is always driving forward when it follows the path.
 * PathChains hold Path(s) within it and are able to hold their end point, meaning that they will holdPoint until another path is followed.
 * Here is a explanation of the difference between Paths and PathChains <https://pedropathing.com/commonissues/pathtopathchain.html>
 */




//    public void buildPaths() {
//        Pose sample1Pose = new Pose(AUTOredSample1X, AUTOredSample1Y);
//        startSample1 = new Path(new BezierLine(new Point(startPose), new Point(sample1Pose);
//        //Sample1.setLinearHeadingInterpolation(startSample1.getHeading(), initialBackdropGoalPose.getHeading());
//        startSample1.setPathEndTimeoutConstraint(0);
//
//        /** This is a path chain, defined on line 66
//         * It, well, chains multiple paths together. Here we use a constant heading from the board to the stack.
//         * On line 97, we set the Linear Interpolation,
//         * which means that Pedro will slowly change the heading of the robot from the startHeading to the endHeading over the course of the entire path */
//
////        cycleStackTo = follower.pathBuilder()
////                .addPath(new BezierLine(new Point(initialBackdropGoalPose), new Point(TopTruss)))
////                .setConstantHeadingInterpolation(firstCycleBackdropGoalPose.getHeading())
////                .addPath(new BezierLine(new Point(TopTruss), new Point(BottomTruss)))
////                .setConstantHeadingInterpolation(firstCycleBackdropGoalPose.getHeading())
////                .addPath(new BezierCurve(new Point(BottomTruss), new Point(12 + 13 + 1, 12, Point.CARTESIAN), new Point(31 + 12 + 1, 36, Point.CARTESIAN), new Point(Stack)))
////                .setConstantHeadingInterpolation(firstCycleBackdropGoalPose.getHeading())
////                .setPathEndTimeoutConstraint(0)
////                .build();
//
////        cycleStackBack = follower.pathBuilder()
////                .addPath(new BezierLine(new Point(Stack), new Point(BottomTruss)))
////                .setConstantHeadingInterpolation(WhiteBackdrop.getHeading())
////                .addPath(new BezierLine(new Point(BottomTruss), new Point(TopTruss)))
////                .setConstantHeadingInterpolation(WhiteBackdrop.getHeading())
////                .addPath(new BezierLine(new Point(TopTruss), new Point(WhiteBackdrop)))
////                .setConstantHeadingInterpolation(WhiteBackdrop.getHeading())
////                .setPathEndTimeoutConstraint(0)
////                .build();
//
//    }