package org.wildstang.year2022.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.inputs.Input;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.roborio.inputs.WsJoystickButton;
import org.wildstang.hardware.roborio.outputs.WsSolenoid;
import org.wildstang.hardware.roborio.outputs.WsSparkMax;
import org.wildstang.year2022.robot.WSInputs;
import org.wildstang.year2022.robot.WSOutputs;

/**
 * Controls the Input and Solenoid.
 * @author YashwantCherukuri
 * @author foxler2010
 */
public class Everybot implements Subsystem {

    WsSparkMax motor;
    WsJoystickButton aButton;
    WsJoystickButton yButton;
    WsJoystickButton bButton;
    WsSolenoid drewTheGOAT;

    @Override
    public void init() {
        
        motor = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.INTAKE_MOTOR);
        drewTheGOAT = (WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.ARM_SOLENOID);
        aButton = (WsJoystickButton) Core.getInputManager().getInput(WSInputs.DRIVER_FACE_DOWN);
        aButton.addInputListener(this);
        yButton = (WsJoystickButton) Core.getInputManager().getInput(WSInputs.DRIVER_FACE_UP);
        yButton.addInputListener(this);
        bButton = (WsJoystickButton) Core.getInputManager().getInput(WSInputs.DRIVER_FACE_RIGHT);
        bButton.addInputListener(this);

    }

    @Override
    public void update() {

    }

    @Override
    public void inputUpdate(Input source) {

        if (source == aButton && aButton.getValue()) {

            motor.setValue(1);

        }

        if ((source == aButton || source == yButton) && (!aButton.getValue() && !yButton.getValue())) {

            motor.setValue(0);

        }

        if (source == yButton && yButton.getValue()) {

            motor.setValue(-1);

        }

        if (source == bButton && bButton.getValue()) {

            drewTheGOAT.setValue(true);
        
        }

        if (source == bButton && !bButton.getValue()) {

            drewTheGOAT.setValue(false);
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