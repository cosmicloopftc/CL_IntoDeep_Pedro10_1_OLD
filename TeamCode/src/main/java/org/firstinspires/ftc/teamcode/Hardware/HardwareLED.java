package org.firstinspires.ftc.teamcode.Hardware;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.w8wjb.ftc.AdafruitNeoDriver;

import android.graphics.Color;
import com.qualcomm.robotcore.hardware.HardwareMap;



public class HardwareLED {
    public AdafruitNeoDriver neopixels;


    public DigitalChannel LEDrightGreen;
    public DigitalChannel LEDrightRed;
    public DigitalChannel LEDleftRed;
    public DigitalChannel LEDleftGreen;

    public DigitalChannel LEDright2Green;
    public DigitalChannel LEDright2Red;
    public DigitalChannel LEDleft2Red;
    public DigitalChannel LEDleft2Green;


    public HardwareLED() {

    }
    public void init(HardwareMap hardwareMap) {
        int NUM_PIXELS = 30;

        neopixels = hardwareMap.get(AdafruitNeoDriver.class,"neopixels");
        neopixels.setNumberOfPixels(NUM_PIXELS);



        LEDrightGreen = hardwareMap.get(DigitalChannel.class, "rightgreen");             //connect to Digital port 0
        LEDrightRed = hardwareMap.get(DigitalChannel.class, "rightred");                 //connect to Digital port 1

        LEDleftGreen = hardwareMap.get(DigitalChannel.class, "leftgreen");               //connect to Digital port 2
        LEDleftRed = hardwareMap.get(DigitalChannel.class, "leftred");                   //connect to Digital port 3

        LEDright2Green = hardwareMap.get(DigitalChannel.class, "right2green");             //connect to Digital port 4
        LEDright2Red = hardwareMap.get(DigitalChannel.class, "right2red");                 //connect to Digital port 5

        LEDleft2Green = hardwareMap.get(DigitalChannel.class, "left2green");               //connect to Digital port 6
        LEDleft2Red = hardwareMap.get(DigitalChannel.class, "left2red");                   //connect to Digital port 7

        LEDrightGreen.setMode(DigitalChannel.Mode.OUTPUT);
        LEDrightRed.setMode(DigitalChannel.Mode.OUTPUT);
        LEDleftGreen.setMode(DigitalChannel.Mode.OUTPUT);
        LEDleftRed.setMode(DigitalChannel.Mode.OUTPUT);

        LEDright2Green.setMode(DigitalChannel.Mode.OUTPUT);
        LEDright2Red.setMode(DigitalChannel.Mode.OUTPUT);
        LEDleft2Green.setMode(DigitalChannel.Mode.OUTPUT);
        LEDleft2Red.setMode(DigitalChannel.Mode.OUTPUT);

    }

    public void LEDinitReady() {
        neopixels.fill(Color.rgb(0, 255, 0));
    }
    public void LEDinitError() {
        neopixels.fill(Color.rgb(255, 0, 0));
    }


}