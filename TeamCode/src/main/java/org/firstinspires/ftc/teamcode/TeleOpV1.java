package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.List;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.w8wjb.ftc.AdafruitNeoDriver;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Hardware.HardwareLED;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.Hardware.HardwareRobot;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.PoseUpdater;
import org.firstinspires.ftc.teamcode.pedroPathing.util.DashboardPoseTracker;
import org.firstinspires.ftc.teamcode.pedroPathing.util.Drawing;

/**
 * This is the TeleOpEnhancements OpMode. It is an example usage of the TeleOp enhancements that
 * Pedro Pathing is capable of.Anyi Lin, Aaron Yang, Harrison Womack - 10158 Scott's Bots, @version 1.0, 3/21/2024

 8/26/2024:  update motor map in this class, in PedroPathing/follower/Follower, in PedroPathing/localization/localizers/ThreeWheelLocalizer
 9/2/2024WT: add FTC dashboard lines similar to those in LocalizerTest
 10/13/2024OT: transfer over PedroPath TeleOp from previous Pedropath tunning
 10/13/2024WT: transfer PedroPath tunning info



 */



@Config    //need this to allow appearance in FtcDashboard Configuration to make adjust of variables
@TeleOp(group="Primary", name= "TeleOpV1.0")
public class TeleOpV1 extends OpMode {
    private Follower follower;
    public DigitalChannel LEDrightGreen;
    public DigitalChannel LEDrightRed;
    private PoseUpdater poseUpdater;
    private DashboardPoseTracker dashboardPoseTracker;
    private Telemetry telemetry;

    public AdafruitNeoDriver neopixels;
    HardwareRobot robot = new HardwareRobot();

    enum State{
        START,
    }
    State state = State.START;



    private ElapsedTime runtime = new ElapsedTime();
    double botHeading;
    String drivingOrientation = "robotOriented";                //TODO: as default for Eduardo, but will also reset in init as well.
    double lastTime;
    double imuAngle;

//    public AdafruitNeoDriver neopixels;




    @Override
    public void init() {



//        neopixels = hardwareMap.get(AdafruitNeoDriver.class, "neopixels");
//        neopixels.setNumberOfPixels(NUM_PIXELS);
       // poseUpdater = new PoseUpdater(hardwareMap);
       // dashboardPoseTracker = new DashboardPoseTracker(poseUpdater);
        //follower = new Follower(hardwareMap);

        robot.init(hardwareMap);   //note hardwareMap is default and part of FTC Robot Controller HardwareMap class
//        robot.imu.resetYaw();      //reset the IMU/Gyro angle with each match.
//        runtime.reset();

      //  telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        //Important Step 2: Get access to a list of Expansion Hub Modules to enable changing caching methods.
        //List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);
    //    List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);
//        for (LynxModule hub : allHubs) {
//            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
//        }

   //     follower.startTeleopDrive();
     //   telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    //    Drawing.drawRobot(poseUpdater.getPose(), "#4CAF50");
   //     Drawing.sendPacket();

//        telemetry.addData(">", "Hardware Initialized");
//        telemetry.update();

    }

    @Override
    public void init_loop() {

      //  telemetry.addData("Present Heading by IMU in degree = ", "(%.1f)", robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
      //  telemetry.addData("Robot Driving Orientation = ", drivingOrientation);





        robot.AdafruitLED.LEDinitReady();

//        robot.AdafruitLED.LEDleft2Green.setState(true);
//        robot.AdafruitLED.LEDleft2Red.setState(false);






//        telemetry.update();
   //     robot.AdafruitLED.LEDrightGreen.setMode(DigitalChannel.Mode.INPUT);
 //       robot.AdafruitLED.LEDrightRed.setMode(DigitalChannel.Mode.OUTPUT);
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


        robot.AdafruitLED.LEDinitError();
/*
        neopixels.fill(Color.rgb(0, 255, 0));
        neopixels.show();
        bulkReadTELEOP();
        telemetry.addData("State = ", state);
        telemetry.addData("Runtime = ", "(%.1f)", getRuntime());
        telemetry.addData("Robot Driving Orientation = ", drivingOrientation);
        telemetry.addData("Present Heading by IMU in degree = ", "(%.1f)", imuAngle);
         //telemetry.addData("Time in State = ", 0);
        //telemetry.addData("lastTime = ", lastTime);




        switch (state) {
            case START:

                break;
        }

        //Drivetrain Movement:
        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x);
        follower.update();

        telemetry.addData("x = ", poseUpdater.getPose().getX());
        telemetry.addData("y =", poseUpdater.getPose().getY());
        telemetry.addData("Odom. heading = ", poseUpdater.getPose().getHeading());
        telemetry.addData("Odom. total heading = ", poseUpdater.getTotalHeading());

        Drawing.drawPoseHistory(dashboardPoseTracker, "#4CAF50");
        Drawing.drawRobot(poseUpdater.getPose(), "#4CAF50");
        Drawing.sendPacket();
        telemetry.update();
        */

    }


    @Override
    public void stop() {
        robot.stop();
    }


    public void bulkReadTELEOP() {
        imuAngle = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);       //0. direction is reverse
        //newRightEncoder = robot.RBack_Motor.getCurrentPosition();       //1.

    }



}
