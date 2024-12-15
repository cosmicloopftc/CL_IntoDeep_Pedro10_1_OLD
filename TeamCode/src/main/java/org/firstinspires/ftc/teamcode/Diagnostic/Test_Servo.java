package org.firstinspires.ftc.teamcode.Diagnostic;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Disabled
public class Test_Servo extends TestItem {
    private Servo servo;
    double onValue;
    double offValue;
    public Test_Servo(String description, Servo servo, double offValue, double onValue) {
        super(description);
        this.servo = servo;
        this.onValue = onValue;             //max allowed servo position
        this.offValue = offValue;           //min allowed servo position
    }

    @Override
    public void run(boolean A_on, boolean Y_on, Telemetry telemetry) {
        double presentServoPosition = servo.getPosition();
        if ((A_on)&&(presentServoPosition > offValue)) {
            servo.setPosition(presentServoPosition - 0.02);
        }else if ((Y_on)&&(presentServoPosition < onValue)) {
            servo.setPosition(presentServoPosition + 0.02);
        } else {
            telemetry.addData("low limit position = ", offValue);
            telemetry.addData("high limit position = ", onValue);
        }

        telemetry.addData("Current Servo position = ", "%.3f", servo.getPosition());

    }
}