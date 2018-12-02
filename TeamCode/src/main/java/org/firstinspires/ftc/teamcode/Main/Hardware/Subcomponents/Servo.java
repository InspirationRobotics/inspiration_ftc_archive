package org.firstinspires.ftc.teamcode.Main.Hardware.Subcomponents;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by adityamavalankar on 11/5/18.
 */

public abstract class Servo implements com.qualcomm.robotcore.hardware.Servo {

    private com.qualcomm.robotcore.hardware.Servo s;
    public HardwareMap hwmap;

    public void init(String hardwareName, HardwareMap inputMap) {

        hwmap = inputMap;

        s = hwmap.get(com.qualcomm.robotcore.hardware.Servo.class, hardwareName);
    }

}
