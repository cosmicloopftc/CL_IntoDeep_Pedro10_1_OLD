package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

//modified from FTC Thunderbolts (Sacramento, CA) mentor's program structure

public class HardwareOuttake {
//    private DcMotor Intake_Motor = null;
    public DcMotor outtakeLeftSlide = null;
    public DcMotor outtakeRightSlide = null;

    public Servo outtakeLeftTilt = null;
    public Servo outtakeRightTilt = null;
    public Servo outtakeClaw = null;
    /*Constructor*/
    public HardwareOuttake() {
    }

    /* Initialize standard Hardware interface */
    public void init(HardwareMap hardwareMap)    {
        //Save reference to Hardware map
        //map and setup mode of slide motors
        outtakeLeftSlide = hardwareMap.get(DcMotor.class, "outtakeLeftSlide");
        outtakeLeftSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        outtakeLeftSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeLeftSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        Slider_Motor_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        Slider_Motor_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        outtakeLeftSlide.setDirection(DcMotor.Direction.FORWARD); //It is forward on robot
        outtakeLeftSlide.setPower(0);

        outtakeRightSlide = hardwareMap.get(DcMotor.class, "outtakeRightSlide");
        outtakeRightSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        outtakeRightSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeRightSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        Slider_Motor_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        Slider_Motor_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        outtakeRightSlide.setDirection(DcMotor.Direction.REVERSE); //It is reversed on robot
        outtakeRightSlide.setPower(0);

        outtakeLeftTilt = hardwareMap.get(Servo.class, "outtakeLeftTilt");
        outtakeRightTilt = hardwareMap.get(Servo.class, "outtakeRightTilt");
        outtakeClaw = hardwareMap.get(Servo.class, "outtakeClaw");


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

    public void groundPositionOpen(){
        leftSlideSetPositionPower(0,0.6);
        rightSlideSetPositionPower(0,0.6);
//        outtakeLeftTilt.setPosition(0);
//        outtakeRightTilt.setPosition(1);
//        outtakeClaw.setPosition(0);
    }
    public void groundPositionClose(){
        leftSlideSetPositionPower(0,0);
        rightSlideSetPositionPower(0,0);
//        outtakeLeftTilt.setPosition(0);
//        outtakeRightTilt.setPosition(1);
//        outtakeClaw.setPosition(0);
    }

    public void lowBasket(){
        leftSlideSetPositionPower(1000,0.6);
        rightSlideSetPositionPower(1000,0.6);
//        outtakeLeftTilt.setPosition(0);
//        outtakeRightTilt.setPosition(1);
//        outtakeClaw.setPosition(0);
    }
    public void highBasket(){
        leftSlideSetPositionPower(3000,0.6);
        rightSlideSetPositionPower(3000,0.6);
//        outtakeLeftTilt.setPosition(0);
//        outtakeRightTilt.setPosition(1);
//        outtakeClaw.setPosition(0);
    }
    public void lowChamber(){
        leftSlideSetPositionPower(0,0);
        rightSlideSetPositionPower(0,0);
//        outtakeLeftTilt.setPosition(0);
//        outtakeRightTilt.setPosition(1);
//        outtakeClaw.setPosition(0);
    }
    public void highChamber(){
        leftSlideSetPositionPower(0,0);
        rightSlideSetPositionPower(0,0);
//        outtakeLeftTilt.setPosition(0);
//        outtakeRightTilt.setPosition(1);
//        outtakeClaw.setPosition(0);
    }

}