package org.firstinspires.ftc.teamcode.Hardware;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.w8wjb.ftc.AdafruitNeoDriver;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
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
    public ColorSensor colorSensor;


    public HardwareLED() {

    }
    public void init(HardwareMap hardwareMap) {
            int NUM_PIXELS = 30;
        colorSensor = hardwareMap.get(ColorSensor.class,"colorSensor");


//        neopixels = hardwareMap.get(AdafruitNeoDriver.class,"neopixels");
//        neopixels.setNumberOfPixels(NUM_PIXELS);
    }
    public void start() {

    }
    public void stop() {

    }
    public void LEDinitReady() {
//        neopixels.fill(Color.rgb(0, 255, 0));
//        neopixels.show();


    }
    public void LEDinitError() {
        neopixels.fill(Color.rgb(255, 0, 0));
        neopixels.show();

    }
}

// purple: (255, 0, 255), white: (255, 255, 255), yellow: (255, 222, 33),