package org.firstinspires.ftc.teamcode.Hardware;
//package org.firstinspires.ftc.teamcode.pedroPathing.examples;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.teamcode.pedroPathing.tuning.FollowerConstants.leftFrontMotorName;
import static org.firstinspires.ftc.teamcode.pedroPathing.tuning.FollowerConstants.leftRearMotorName;
import static org.firstinspires.ftc.teamcode.pedroPathing.tuning.FollowerConstants.rightFrontMotorName;
import static org.firstinspires.ftc.teamcode.pedroPathing.tuning.FollowerConstants.rightRearMotorName;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;

//modified from FTC Thunderbolts (Sacramento, CA) mentor's program structure

public class HardwareDrivetrain {
    public static DcMotorEx leftFront = null;
    public static DcMotorEx leftRear = null;
    public static DcMotorEx rightFront = null;
    public static DcMotorEx rightRear = null;


    double newForward = 0, newRight = 0, driveTheta = 0, r = 0 ;
    /*Constructor*/
    public HardwareDrivetrain() {
    }


    /* Initialize standard Hardware interface */
    public void init(HardwareMap hardwareMap)    {

        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftRear = hardwareMap.get(DcMotorEx.class, "leftRear");
        rightRear = hardwareMap.get(DcMotorEx.class, "rightRear");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
//        rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
//        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        setMotorPower(0,0,0,0);
    }


    public void start(){

    }


    public void stop (){
        setMotorPower(0,0,0,0);
    }
    public static void setMotorPower(double RF, double LF, double RB, double LB) {
        rightFront.setPower(RF);
        leftFront.setPower(LF);
        rightRear.setPower(RB);
        leftRear.setPower(LB);
    }


}