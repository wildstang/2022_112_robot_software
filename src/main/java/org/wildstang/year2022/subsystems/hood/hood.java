package org.wildstang.year2022.subsystems.hood;
import org.wildstang.framework.core.Core;
import com.revrobotics.SparkMaxAnalogSensor.Mode;

import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.io.inputs.Input;
import org.wildstang.hardware.roborio.inputs.WsJoystickAxis;
import org.wildstang.hardware.roborio.inputs.WsJoystickButton;
import org.wildstang.hardware.roborio.outputs.WsPhoenix;
import org.wildstang.hardware.roborio.outputs.WsSparkMax;
import org.wildstang.year2022.robot.WSInputs;
import org.wildstang.year2022.robot.WSOutputs;
import org.wildstang.framework.subsystems.Subsystem;

public class hood implements Subsystem {

    WsSparkMax Hood_Motor;
    WsJoystickAxis yAxis;
    AnalogInput left_trigger;
    DigitalInput dpad_up;
    DigitalInput dpad_down;
    DigitalInput dpad_left;
    DigitalInput dpad_right;
    

    double position = 0;
    double position_change = .112;
    double p1 = 0;
    double p2 = 1;
    double p3 = 2;
    double p4 = 3;


    @Override
    public void init() {
        
        Hood_Motor = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.Hood_Motor);
        Hood_Motor.initClosedLoop(1.0, 0.0, 0.0, 0.0);
        yAxis = (WsJoystickAxis) Core.getInputManager().getInput(WSInputs.DRIVER_LEFT_JOYSTICK_Y);
        yAxis.addInputListener(this);
        dpad_up = (DigitalInput) Core.getInputManager().getInput(WSInputs.DRIVER_DPAD_UP); 
        dpad_up.addInputListener(this);
        dpad_right = (DigitalInput) Core.getInputManager().getInput(WSInputs.DRIVER_DPAD_RIGHT); 
        dpad_right.addInputListener(this);
        dpad_left = (DigitalInput) Core.getInputManager().getInput(WSInputs.DRIVER_DPAD_LEFT); 
        dpad_left.addInputListener(this);
        dpad_down = (DigitalInput) Core.getInputManager().getInput(WSInputs.DRIVER_DPAD_DOWN); 
        dpad_down.addInputListener(this);
        left_trigger =  (AnalogInput) Core.getInputManager().getInput(WSInputs.DRIVER_LEFT_TRIGGER);
        left_trigger.addInputListener(this);
        resetState();
    }

    @Override
    public void update() {
        Hood_Motor.setPosition(position);
    }

    @Override
    public void inputUpdate(Input source) {
        if  (source == yAxis){
            if (yAxis.getValue() >= .75){
                position  += position_change;
            }
            else if (yAxis.getValue() <= -0.75){
                position -= position_change;
            }
        }
        if (source == dpad_up && dpad_up.getValue()){
            position = p1;
        }
        if (source == dpad_down && dpad_down.getValue()){
            position = p2;
        }
         if (source == dpad_right && dpad_right.getValue()){
            position = p3;
        }
        if (source == dpad_left && dpad_left.getValue()){
            position = p4;
        }
    } 

    @Override
    public void selfTest() {

    }

    @Override
    public void resetState() {
        position = 0;
        Hood_Motor.resetEncoder();
        Hood_Motor.setValue(Hood_Motor.getController().getAnalog(Mode.kAbsolute).getVoltage()/3.3);
        
    }

    @Override
    public String getName() {
        return "hood";
    }
}