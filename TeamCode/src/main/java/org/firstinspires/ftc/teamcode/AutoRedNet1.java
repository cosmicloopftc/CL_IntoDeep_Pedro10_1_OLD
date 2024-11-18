package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.AUTOconstant.AUTORedNetX;
import static org.firstinspires.ftc.teamcode.AUTOconstant.AUTORedNetY;
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
import org.firstinspires.ftc.teamcode.Hardware.HardwareRobot;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.*;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.PoseUpdater;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierCurve;
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
 */


@Config
@Autonomous(name = "Auto Red Net v1.0", group = "Auto")
public class AutoRedNet1 extends OpMode {
    private Telemetry telemetryA;
    private Follower follower;
    private PoseUpdater poseUpdater;
    private DashboardPoseTracker dashboardPoseTracker;
    //private Telemetry telemetry;

    private Timer pathTimer, actionTimer, opmodeTimer;
    //    private int pathState, actionState, clawState;
    private String navigation;
    //    public ClawSubsystem claw;
    private HuskyLens huskyLens;


    private Pose sample1Pose, sample2Pose, sample3Pose, sample4Pose, sample5Pose, sample6Pose, redNet, blueNet;
    private PathChain startSample3Net;
    private PathChain netSample2Net;
    private PathChain netSample1Net;

    private Pose startPose = new Pose(AUTOstartRedNetX, AUTOstartRedNetY, Math.toRadians(180));

    HardwareRobot robot = new HardwareRobot();

    public void buildPaths() {
        Pose sample1Pose = new Pose(AUTOredSample1X, AUTOredSample1Y, Math.toRadians(180));
        Pose sample2Pose = new Pose(AUTOredSample2X, AUTOredSample2Y, Math.toRadians(180));
        Pose sample3Pose = new Pose(AUTOredSample3X, AUTOredSample3Y, Math.toRadians(180));
        Pose redNetPose = new Pose(AUTORedNetX, AUTORedNetY, Math.toRadians(315));

        startSample3Net = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(startPose), new Point(sample3Pose.getX()+5+9, sample3Pose.getY(), Point.CARTESIAN), new Point(sample3Pose.getX()+9, sample3Pose.getY(), Point.CARTESIAN)))
                .setConstantHeadingInterpolation(sample3Pose.getHeading())              //one Heading only
                .addPath(new BezierLine(new Point(sample3Pose.getX() + 9, sample3Pose.getY(), Point.CARTESIAN), new Point(redNetPose.getX() - 9, redNetPose.getY() + 9, Point.CARTESIAN)))
                .setLinearHeadingInterpolation(sample3Pose.getHeading(), redNetPose.getHeading())       //startHeading and endHeading
                .setPathEndTimeoutConstraint(3)
                .build();

    }



    @Override
    public void init() {

        poseUpdater = new PoseUpdater(hardwareMap);
        dashboardPoseTracker = new DashboardPoseTracker(poseUpdater);
        follower = new Follower(hardwareMap);

        robot.init(hardwareMap);   //note hardwareMap is default and part of FTC Robot Controller HardwareMap class

        buildPaths();



        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetryA.update();

    }

    public void start() {
        opmodeTimer.resetTimer();
    }


    @Override
    public void loop() {
        follower.update();

       // telemetryA.addLine("going forward");


        follower.telemetryDebug(telemetryA);
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


//        startSample1 = new Path(new BezierLine(new Point(144,24, Point.CARTESIAN), new Point(120,24, Point.CARTESIAN)));
//        startSample1.setConstantHeadingInterpolation(0);
//        follower.followPath(startSample1);


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