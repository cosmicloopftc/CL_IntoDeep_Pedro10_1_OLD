package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.List;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Hardware.HardwareDrivetrain;
import org.firstinspires.ftc.teamcode.Hardware.HardwareIntake;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.Hardware.HardwareRobot;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.PoseUpdater;
import org.firstinspires.ftc.teamcode.pedroPathing.util.DashboardPoseTracker;
import org.firstinspires.ftc.teamcode.pedroPathing.util.Drawing;
import com.qualcomm.robotcore.hardware.DcMotorEx;

/**
 * This is the TeleOpEnhancements OpMode. It is an example usage of the TeleOp enhancements that
 * Pedro Pathing is capable of.Anyi Lin, Aaron Yang, Harrison Womack - 10158 Scott's Bots, @version 1.0, 3/21/2024

 8/26/2024:  update motor map in this class, in PedroPathing/follower/Follower, in PedroPathing/localization/localizers/ThreeWheelLocalizer
 9/2/2024WT: add FTC dashboard lines similar to those in LocalizerTest
 10/13/2024OT: transfer over PedroPath TeleOp from previous Pedropath tuning
 10/13/2024WT: transfer PedroPath tuning info
 10/14/2024MT/WT: add manual driving, not using PedroPath Follower
 10/24/2024WT: correct HardwareLED class, rename in AdafruitLED object name

 */



@Config    //need this to allow appearance in FtcDashboard Configuration to make adjust of variables
@TeleOp(group="Primary", name= "TeleOpV1.0")
public class TeleOpV1 extends OpMode {
    private Follower follower;
    private PoseUpdater poseUpdater;
    private DashboardPoseTracker dashboardPoseTracker;
    private Telemetry telemetry;


    HardwareRobot robot = new HardwareRobot();

    enum State{
        START,
        INTAKE,
        TRANSFER,
        OUTTAKE_READY,
        OUTTAKE,
        READY_DOWN
    }
    State state = State.START;



    private ElapsedTime runtime = new ElapsedTime();
    double botHeading;
    String drivingOrientation = "robotOriented";                //TODO: as default for Eduardo, but will also reset in init as well.
    double lastTime;
    double imuAngle;
    String outtakeOption = "";
//Declare variables for standard driving--not using PedroPath follower method (using Learn JAVA for FTC book)
    double y, x, rx, powerShift;
    //double newForward = 0, newRight = 0, driveTheta = 0, r = 0, powerShift = 0;


    @Override
    public void init() {
        poseUpdater = new PoseUpdater(hardwareMap);
        dashboardPoseTracker = new DashboardPoseTracker(poseUpdater);
        follower = new Follower(hardwareMap);

        robot.init(hardwareMap);   //note hardwareMap is default and part of FTC Robot Controller HardwareMap class
        robot.imu.resetYaw();      //reset the IMU/Gyro angle with each match.
        runtime.reset();

        //telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        //Important Step 2: Get access to a list of Expansion Hub Modules to enable changing caching methods.
        //List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);
//        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);
//        for (LynxModule hub : allHubs) {
//            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
//        }
//
//        follower.startTeleopDrive();
//        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
//        Drawing.drawRobot(poseUpdater.getPose(), "#4CAF50");
//        Drawing.sendPacket();



//        telemetry.addData(">", "Hardware Initialized");
//        telemetry.update();
    }

    @Override
    public void init_loop() {
//        telemetry.addData("Present Heading by IMU in degree = ", "(%.1f)", robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
//        telemetry.addData("Robot Driving Orientation = ", drivingOrientation);
//        telemetry.update();

        robot.AdafruitLED.LEDinitReady();
    }

    @Override
    public void start() {
        robot.start();
        runtime.reset();
        drivingOrientation = "robotOriented";
        state = State.START;
    }

    @Override
    public void loop() {
//        bulkReadTELEOP();
//        telemetry.addData("State = ", state);
//        telemetry.addData("Runtime = ", "(%.1f)", getRuntime());
//        telemetry.addData("Robot Driving Orientation = ", drivingOrientation);
//        telemetry.addData("Present Heading by IMU in degree = ", "(%.1f)", imuAngle);
         //telemetry.addData("Time in State = ", 0);
        //telemetry.addData("lastTime = ", lastTime);




        switch (state) {
            case START:
                if(gamepad2.a || outtakeOption.equals("start")) { // this current code would be in transfer state when intake ready
                    robot.Outtake.groundPositionOpen();
                    outtakeOption = "";
                }
                else if(gamepad2.left_trigger > 0.2 || gamepad2.right_trigger > 0.2){
                    robot.Outtake.groundPositionClose();
                }
                if(gamepad2.b) {
                    outtakeOption = "lowBasket";
                    state = State.OUTTAKE_READY;
                }
                else if(gamepad2.y) {
                    outtakeOption = "highBasket";
                    state = State.OUTTAKE_READY;
                }
                else if(gamepad2.dpad_down){
                    outtakeOption = "wallIntake";
                    state = State.OUTTAKE_READY;
                }
                break;
            case INTAKE:

                break;
            case TRANSFER:

                break;
            case OUTTAKE_READY:
                robot.Outtake.readyPosition();
                if(robot.Outtake.outtakeLeftSlide.getCurrentPosition()>400){
                    state = State.OUTTAKE;
                }
                break;

            case OUTTAKE:
                if (outtakeOption.equals("lowBasket")){
                    robot.Outtake.lowBasket();
                    if (gamepad2.left_bumper || gamepad1.right_bumper){
                        robot.Outtake.openClaw();
                    }
                }
                if (outtakeOption.equals("highBasket")){
                    robot.Outtake.leftSlideSetPositionPower(3000,0.6);
                    robot.Outtake.rightSlideSetPositionPower(3000,0.6);
                    if (robot.Outtake.outtakeLeftSlide.getCurrentPosition()>2000){
                        robot.Outtake.highBasket();
                    }
                    if (gamepad2.left_bumper || gamepad1.right_bumper){
                        robot.Outtake.openClaw();
                    }
                }
                if (outtakeOption.equals("wallIntake")){
                    robot.Outtake.wallIntake();
                    if (gamepad2.left_trigger > 0.2 || gamepad2.right_trigger > 0.2){
                        robot.Outtake.closeClaw();
                        outtakeOption = "highChamber";
                    }
                if (outtakeOption.equals("highChamber")){
                    robot.Outtake.highChamberSet();
                    if (gamepad2.left_bumper || gamepad2.right_bumper){
                        robot.Outtake.highChamberFinish(); //TODO: See how robot can go back to ground position from here
                    }
                }
                }
                if (gamepad2.a){ // Move slides down
                    if (outtakeOption.equals("lowBasket")){ //Makes sure arm does not hit intake when going down
                        resetRuntime();
                        robot.Outtake.leftOuttakeArm.setPosition(0.98);
                        robot.Outtake.rightOuttakeArm.setPosition(0.02);
                        if (getRuntime() > 0.1){
                            state = State.READY_DOWN;
                        }
                    }
                    else{
                        state = State.READY_DOWN;
                    }
                }
                break;
            case READY_DOWN:
                robot.Outtake.readyPosition();
                if(robot.Outtake.outtakeLeftSlide.getCurrentPosition()<600){
                    outtakeOption = "start";
                    state = State.START;
                }
                break;
        }

        //Drivetrain Movement:
        //MANUAL DRIVE for Mecanum wheel drive.
        y = -gamepad1.left_stick_y;           // Remember,joystick value is reversed!
        x = gamepad1.left_stick_x;
        rx = gamepad1.right_stick_x;

        //Cancel angle movement of gamepad left stick, make move move either up/down or right/left
        if (Math.abs(y) >= Math.abs(x)) {
            y = y;
            x = 0;
        } else {
            y = 0;
            x = x;
        }
        //DRIVETRAIN
        //baseline:  reduce motor speed to 50% max
        double motorPowerDefault = 0.6;
        double powerChange;

//SLOW DOWN with RIGHT LOWER TRIGGER (lower of the top side button) press with the other gamepad stick.
        if ((Math.abs(gamepad1.left_stick_y) > 0.1 && gamepad1.right_trigger > 0.1) || (Math.abs(gamepad1.left_stick_x) > 0.1 && gamepad1.right_trigger > 0.1) || (Math.abs(gamepad1.right_stick_x) > 0.1 && gamepad1.right_trigger > 0.1)) {
            powerChange = -0.3;
            //SPEED UP with RIGHT UPPER BUMPER (up of the top side button) press with the other gamepad stick.
        } else if ((Math.abs(gamepad1.left_stick_y) > 0.1 && gamepad1.right_bumper) || (Math.abs(gamepad1.left_stick_x) > 0.1 && gamepad1.right_bumper) || (Math.abs(gamepad1.right_stick_x) > 0.1 && gamepad1.right_bumper)) {
            powerChange = 0.4;
        } else {
            powerChange = 0;
        }
        powerShift = motorPowerDefault + powerChange;
        //robot.Intake.IntakeIN();
        robot.drive(y, x, rx, powerShift, botHeading, drivingOrientation);


//        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x);
//        follower.update();

//        telemetry.addData("x = ", poseUpdater.getPose().getX());
//        telemetry.addData("y =", poseUpdater.getPose().getY());
//        telemetry.addData("Odom. heading = ", poseUpdater.getPose().getHeading());
//        telemetry.addData("Odom. total heading = ", poseUpdater.getTotalHeading());

//        Drawing.drawPoseHistory(dashboardPoseTracker, "#4CAF50");
//        Drawing.drawRobot(poseUpdater.getPose(), "#4CAF50");
//        Drawing.sendPacket();
//        telemetry.update();
    }


    @Override
    public void stop() {
        robot.stop();
        HardwareDrivetrain.setMotorPower(0, 0, 0, 0);
    }


    public void bulkReadTELEOP() {
        imuAngle = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);       //0. direction is reverse
        //newRightEncoder = robot.RBack_Motor.getCurrentPosition();       //1.

    }


}
