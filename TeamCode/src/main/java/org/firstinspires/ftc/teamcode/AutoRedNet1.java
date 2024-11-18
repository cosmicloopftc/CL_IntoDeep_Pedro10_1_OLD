package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.AUTOconstant.AUTOredSample1X;
import static org.firstinspires.ftc.teamcode.AUTOconstant.AUTOredSample1Y;
import static org.firstinspires.ftc.teamcode.AUTOconstant.AUTOstartRedNetX;
import static org.firstinspires.ftc.teamcode.AUTOconstant.AUTOstartRedNetY;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;
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
//import org.firstinspires.ftc.teamcode.config.subsystem.ClawSubsystem;

import java.util.concurrent.TimeUnit;

import org.firstinspires.ftc.teamcode.AUTOconstant;



/**
 * adapted from @author Baron Henderson - 20077 The Indubitables
 * @version 2.0, 9/8/2024
 * 11/29/2024: start
 */


@Config
@Autonomous(name = "Auto Red Net v1.0", group = "Auto")
public class AutoRedNet1 extends OpMode {
    private Telemetry telemetryA;
    private Follower follower;
    private PoseUpdater poseUpdater;
    private DashboardPoseTracker dashboardPoseTracker;
    private Telemetry telemetry;

    private Timer pathTimer, actionTimer, opmodeTimer;
    //    private int pathState, actionState, clawState;
    private String navigation;
    //    public ClawSubsystem claw;
    private HuskyLens huskyLens;


    private Pose sample1Pose, sample2Pose, sample3Pose, sample4Pose, sample5Pose, sample6Pose;
    private Path startSample1, sample1Net, nwtSample2Net, netSample3Net;
    //Start Pose
    private Pose startPose = new Pose(AUTOstartRedNetX, AUTOstartRedNetY, Math.toRadians(180));

    HardwareRobot robot = new HardwareRobot();



//    public void buildPaths() {
//        /** There are two major types of paths components: BezierCurves and BezierLines.
//         *    * BezierCurves are curved, and require > 3 points. There are the start and end points, and the control points.
//         *    - Control points manipulate the curve between the start and end points.
//         *    - A good visualizer for this is [this](https://www.desmos.com/calculator/3so1zx0hcd).
//         *    * BezierLines are straight, and require 2 points. There are the start and end points. **/
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

    @Override
    public void init() {

        poseUpdater = new PoseUpdater(hardwareMap);
        dashboardPoseTracker = new DashboardPoseTracker(poseUpdater);
        follower = new Follower(hardwareMap);

        robot.init(hardwareMap);   //note hardwareMap is default and part of FTC Robot Controller HardwareMap class

        startSample1 = new Path(new BezierLine(new Point(144,24, Point.CARTESIAN), new Point(120,24, Point.CARTESIAN)));
        startSample1.setConstantHeadingInterpolation(0);
        follower.followPath(startSample1);

        //telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        //telemetryA.update();

    }

    @Override
    public void loop() {
        follower.update();

       // telemetryA.addLine("going forward");


        //follower.telemetryDebug(telemetryA);
    }
}