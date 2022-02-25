package org.wildstang.year2022.subsystems;

import org.apache.commons.math3.dfp.DfpDec;
import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.inputs.Input;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.roborio.inputs.WsDigitalInput;
import org.wildstang.hardware.roborio.inputs.WsJoystickAxis;
import org.wildstang.hardware.roborio.inputs.WsJoystickButton;
import org.wildstang.hardware.roborio.outputs.WsPhoenix;
import org.wildstang.hardware.roborio.outputs.WsSparkMax;
import org.wildstang.hardware.roborio.outputs.config.WsSparkMaxConfig;
import org.wildstang.year2022.robot.WSInputs;
import org.wildstang.year2022.robot.WSOutputs;

/**
 * Sample Subsystem that controls a motor with a joystick.
 * @author Liam
 */
public class Everybot implements Subsystem {

    WsSparkMax motor;
    WsJoystickButton aButton;

    @Override
    public void init() {
        
        motor = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.TEST_MOTOR);
        aButton = (WsJoystickButton) Core.getInputManager().getInput(WSInputs.DRIVER_FACE_DOWN);
        aButton.addInputListener(this);

    }

    @Override
    public void update() {

    }

    @Override
    public void inputUpdate(Input source) {

        if (source == aButton && aButton.getValue()) {
            motor.setValue(1);
        }
        

    } 

    @Override
    public void selfTest() {

    }

    @Override
    public void resetState() {

    }

    @Override
    public String getName() {
        return "Everybot";
    }
}