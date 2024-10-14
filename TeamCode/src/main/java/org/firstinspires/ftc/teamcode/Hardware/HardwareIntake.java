package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

//modified from FTC Thunderbolts (Sacramento, CA) mentor's program structure
//TODO: program the Servo position


public class HardwareIntake {

    public DcMotor intakeSlides = null;


    public CRServo intakeLeftWheel = null;
    public CRServo intakeRightWheel = null;
    public Servo intakeLeftTilt = null;
    public Servo intakeRightTilt = null;
    //Servo Test
//    public Servo Servo_Test = null;


    /*Constructor*/
    public HardwareIntake() {
    }

    /* Initialize standard Hardware interface */
    public void init(HardwareMap hardwareMap)    {
        //Save reference to Hardware map

        //map and setup mode of Intake Slide Motor
        intakeSlides = hardwareMap.get(DcMotor.class, "Intake_Slides");
        //intake slide motor behaviors
        intakeSlides.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeSlides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        Slider_Motor_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        Slider_Motor_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        intakeSlides.setDirection(DcMotor.Direction.FORWARD); //TODO: reverse or not?????
        intakeSlides.setPower(0);

        //map and setup mode of Intake Continuous Servos
        intakeLeftWheel = hardwareMap.get(CRServo.class, "Intake_LeftWheel");
        intakeRightWheel = hardwareMap.get(CRServo.class, "Intake_RightWheel");
        intakeLeftTilt = hardwareMap.get(Servo.class, "Intake_LeftTilt");
        intakeRightTilt = hardwareMap.get(Servo.class, "Intake_RightTilt");



        //TEST SERVO
//        Servo_Test = hardwareMap.get(Servo.class, "Servo_Test");

    }


    public void start(){

    }


    public void stop () {

    }

    //TODO: Method for entire intake process
    //public method (function) for intaking sample
    public void intakeSlideSetPositionPower(
            int DesiredSliderPosition,
            double SliderPower){

        intakeSlides.setTargetPosition(DesiredSliderPosition);
        intakeSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        intakeSlides.setPower(SliderPower);
    }

    //public method (function) for intaking in sample
    public void intakeIN () {
        intakeLeftWheel.setPower(1);
        intakeRightWheel.setPower(-1);
    }


    //public method (function) for spitting out sample
    public void intakeOUT() {
        intakeLeftWheel.setPower(-1);
        intakeRightWheel.setPower(1);
    }

    //public method (function) for stopping the intake
    public void intakeSTOP() {
        intakeLeftWheel.setPower(0);
        intakeRightWheel.setPower(0);
    }

    //method for the retracted position of the intake slides
    public void intakeSlideIN() {
        intakeSlideSetPositionPower(0,0); //TODO: set power
    }

    //method for the extended position of the intake slides
    public void intakeSlideOUT() {
        intakeSlideSetPositionPower(0,0); //TODO: set position and power
    }

    public void transferIntake(){ //TODO: finish
        intakeSlideIN();

    }
}