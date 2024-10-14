package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Test.TestProgrammingBoard;
import org.firstinspires.ftc.teamcode.Test.TestItem;
import java.util.ArrayList;

/*THIS CLASS SETUP THE MENU.  IT PULLS EVERYTHING TOGETHER---MAIN PROGRAM FOR THE TESTING HARDWARE.
3/3/2024 update hardware and add Y and A input for 2 type of "on" button
(child class) TestProgrammingBoard + TestItem --> TestingMotor... --> TestWIRING_OpMode (parent superclass)
*/




@TeleOp (group="test", name= "TestWIRING FOR DIAGNOSTIC_1.0")
public class TestWiringOpMode extends OpMode {
    TestProgrammingBoard board = new TestProgrammingBoard();
    ArrayList<TestItem> tests;
    boolean wasDown, wasUp;
    int testNum;

    @Override
    public void init() {
        board.init(hardwareMap);
        tests = board.getTests();
    }

    @Override
    public void loop() {
        // move up in the list of test
        if (gamepad1.dpad_up && !wasUp) {
            testNum--;
            if (testNum < 0) {
                testNum = tests.size() - 1;
            }
        }
        wasUp = gamepad1.dpad_up;

        // move down in the list of tests
        if (gamepad1.dpad_down && !wasDown) {
            testNum++;
            if (testNum >= tests.size()) {
                testNum = 0;
            }
        }
        wasDown = gamepad1.dpad_down;

//Put instructions on the telemetry
        telemetry.addLine("Use Up/Down on D-pad for choices");
        //telemetry.addLine("Press A to run test");
        telemetry.addLine("");
        telemetry.addLine("For Servo, Press A for --, Y ++");
        telemetry.addLine("For other hardware, Press A or Y to run test");
        telemetry.addLine("");
        //put the test on the telemetry
        TestItem currTest = tests.get(testNum);
        telemetry.addData("Test:", currTest.getDescription());
        //run or don’t run based on a
        telemetry.addLine("");
        if(gamepad1.a && !gamepad1.y){
            gamepad1.a = true;
            gamepad1.y = false;
        }
        if(gamepad1.y && !gamepad1.a){
            gamepad1.y = true;
            gamepad1.a = false;
        }
        currTest.run(gamepad1.a, gamepad1.y, telemetry);

        telemetry.update();
    }
}