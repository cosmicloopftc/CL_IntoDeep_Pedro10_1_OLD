package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

//modified from FTC Thunderbolts (Sacramento, CA) mentor's program structure

public class HardwareOuttake {
    //    private DcMotor Intake_Motor = null;
    public DcMotorEx outtakeLeftSlide = null;
    public DcMotorEx outtakeRightSlide = null;

    public Servo leftOuttakeArm = null;
    public Servo rightOuttakeArm = null;
    public Servo claw = null;
    /*Constructor*/
    public HardwareOuttake() {
    }

    /* Initialize standard Hardware interface */
    public void init(HardwareMap hardwareMap)    {
        //Save reference to Hardware map
        //map and setup mode of slide motors
        outtakeLeftSlide = hardwareMap.get(DcMotorEx.class, "outtakeLeftSlide");
        outtakeLeftSlide.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        outtakeLeftSlide.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        outtakeLeftSlide.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        outtakeLeftSlide.setDirection(DcMotorEx.Direction.FORWARD); //It is forward on robot
        outtakeLeftSlide.setPower(0);

        outtakeRightSlide = hardwareMap.get(DcMotorEx.class, "outtakeRightSlide");
        outtakeRightSlide.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        outtakeRightSlide.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        outtakeRightSlide.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        outtakeRightSlide.setDirection(DcMotorEx.Direction.REVERSE); //It is reversed on robot
        outtakeRightSlide.setPower(0);

        leftOuttakeArm = hardwareMap.get(Servo.class, "leftOuttakeArm");
        rightOuttakeArm = hardwareMap.get(Servo.class, "rightOuttakeArm");
        claw = hardwareMap.get(Servo.class, "claw");


    }


    public void start(){

    }


    public void stop () {

    }
    public void leftSlideSetPositionPower(
            int DesiredSliderPosition,
            double SliderPower){

        outtakeLeftSlide.setTargetPosition(DesiredSliderPosition);
        outtakeLeftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        outtakeLeftSlide.setPower(SliderPower);
    }
    public void rightSlideSetPositionPower(
            int DesiredSliderPosition,
            double SliderPower){

        outtakeRightSlide.setTargetPosition(DesiredSliderPosition);
        outtakeRightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        outtakeRightSlide.setPower(SliderPower);
    }


    public void openClaw(){
        claw.setPosition(0.17);
    }
    public void closeClaw(){
        claw.setPosition(0.32);
    }
    public void groundPositionOpen(){
        leftSlideSetPositionPower(0,1);
        rightSlideSetPositionPower(0,1);
        leftOuttakeArm.setPosition(0.98);
        rightOuttakeArm.setPosition(0.02);
        openClaw();
    }
    public void groundPositionClose(){
        leftSlideSetPositionPower(0,0);
        rightSlideSetPositionPower(0,0);
        leftOuttakeArm.setPosition(0.98);
        rightOuttakeArm.setPosition(0.02);
        closeClaw();
    }

    public void readyPosition(){
        leftOuttakeArm.setPosition(0.98);
        rightOuttakeArm.setPosition(0.02);
        leftSlideSetPositionPower(500,1);
        rightSlideSetPositionPower(500,1);
    }

    public void lowBasket(){
        leftOuttakeArm.setPosition(0.2);
        rightOuttakeArm.setPosition(0.8);
        leftSlideSetPositionPower(970,0.6);
        rightSlideSetPositionPower(970,0.6);
    }
    public void highBasket(){
        leftOuttakeArm.setPosition(0.22);
        rightOuttakeArm.setPosition(0.78);
        leftSlideSetPositionPower(3400,1);
        rightSlideSetPositionPower(3400,1);
    }
    public void lowChamber(){ //Not able to do this with current V1 robot
//        leftSlideSetPositionPower(0,0);
//        rightSlideSetPositionPower(0,0);
//        leftOuttakeArm.setPosition(0);
//        rightOuttakeArm.setPosition(1);
//        claw.setPosition(0);
    }
    public void highChamberSet(){
        leftSlideSetPositionPower(700,0.6);
        rightSlideSetPositionPower(700,0.6);
        leftOuttakeArm.setPosition(0.3);
        rightOuttakeArm.setPosition(0.7);
    }
    public void highChamberFinish(){
        leftSlideSetPositionPower(700,1);
        rightSlideSetPositionPower(700,1);
        leftOuttakeArm.setPosition(0.3);
        rightOuttakeArm.setPosition(0.7);
        openClaw();
    }
    public void wallIntake(){
        leftSlideSetPositionPower(0,1);
        rightSlideSetPositionPower(0,1);
        leftOuttakeArm.setPosition(0.05);
        rightOuttakeArm.setPosition(0.95);
    }
}