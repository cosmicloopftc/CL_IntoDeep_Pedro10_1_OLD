package org.firstinspires.ftc.teamcode.Hardware;

import android.graphics.Color;


import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;



//modified from FTC Thunderbolts (Sacramento, CA) mentor's program structure

public class HardwareSensors {
//    private DcMotor Intake_Motor = null;
public ColorSensor colorIntake1;


    public int detected_color;
float hsvValues[] = {0F,0F,0F};
final float values[] = hsvValues;



    /*Constructor*/
    public HardwareSensors() {
    }

    /* Initialize standard Hardware interface */
    public void init(HardwareMap hardwareMap) {
        //Save reference to Hardware map
        colorIntake1 = hardwareMap.get(ColorSensor.class,"colorTest");


/*example:  map and setup mode of Intake motor
        Intake_Motor = hardwareMap.get(DcMotor.class, "Intake_Motor");
        Intake_Motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Intake_Motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Intake_Motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Intake_Motor.setDirection(DcMotor.Direction.FORWARD);
        Intake_Motor.setPower(0);
*/
        detected_color = Color.HSVToColor(0xff, values);

    }

    public void start() {

    }


    public void stop () {

    }
    public float getColorInfo(int index) {
       // Color.RGBToHSV(colorIntake1.red() * 8, colorIntake1.green() * 8, colorIntake1.blue() * 8, hsvValues);
        final float[] hsvValues = new float[3];
        //take the readings, etc.
        NormalizedRGBA colors = ((NormalizedColorSensor) colorIntake1).getNormalizedColors();
        Color.colorToHSV(colors.toColor(), hsvValues);

        Color.RGBToHSV((int) (colorIntake1.red() * 8),
                (int) (colorIntake1.green() * 8),
                (int) (colorIntake1.blue() * 8),
                hsvValues);

        return hsvValues[index];


    }



//example:  for Intake motor
//public method (function) for Intake motor power--to be accessible from anywhere
//    public void setPower(double i) {
//        Intake_Motor.setPower(i);
//    }
}