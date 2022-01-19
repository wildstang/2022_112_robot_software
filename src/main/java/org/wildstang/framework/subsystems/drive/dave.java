
 package org.wildstang.sample.subsystems.dave;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.kauailabs.navx.frc.AHRS;

import org.wildstang.framework.core.Core;
import com.revrobotics.CANSparkMax;
import org.wildstang.framework.io.inputs.Input;
import org.wildstang.framework.logger.Log;
import org.wildstang.framework.pid.PIDConstants;
import org.wildstang.framework.subsystems.drive.Path;
import org.wildstang.framework.subsystems.drive.PathFollowingDrive;
import org.wildstang.framework.subsystems.drive.TankPath;
import org.wildstang.hardware.roborio.inputs.WsAnalogInput;
import org.wildstang.hardware.roborio.inputs.WsDigitalInput;
import org.wildstang.hardware.roborio.inputs.WsJoystickButton;
import org.wildstang.hardware.roborio.outputs.WsPhoenix;
import org.wildstang.hardware.roborio.outputs.WsSparkMax;
import org.wildstang.sample.robot.WSInputs;
import org.wildstang.sample.robot.WSOutputs;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.I2C;

public class OmniDave{


private WsSparkMax MotorA, MotorB, MotorC, MotorD; 
private WsAnalogInput throttleJoystickY,  throttleJoystickX, headingJoystickY,  headingJoystickX;
private double AValue, BValue, CValue, DValue; 

public void init(){
    MotorA = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.DriveA);
    MotorB = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.DriveB);
    MotorC = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.DriveC);
    MotorD = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.DriveD);
    throttleJoystickY = (WsAnalogInput) Core.getInputManager().getInput(WSInputs.DRIVER_LEFT_JOYSTICK_Y);
        throttleJoystickY.addInputListener(this);
    throttleJoystickX = (WsAnalogInput) Core.getInputManager().getInput(WSInputs.DRIVER_LEFT_JOYSTICK_X);
        throttleJoystickX.addInputListener(this);
    headingJoystickY = (WsAnalogInput) Core.getInputManager().getInput(WSInputs.DRIVER_RIGHT_JOYSTICK_Y);
        headingJoystickY.addInputListener(this); 
    headingJoystickX = (WsAnalogInput) Core.getInputManager().getInput(WSInputs.DRIVER_RIGHT_JOYSTICK_X);
        headingJoystickX.addInputListener(this); 
}

public void InputUpdate(Input Source){

    double throttleX = throttleJoystickX.GetValue();
    double throttleY = throttleJoystickY.GetValue();
    double headingX = headingJoystickX.GetValue();
    double headingY = headingJoystickY.GetValue()
}

//golf

public void selfTest(){
}
}