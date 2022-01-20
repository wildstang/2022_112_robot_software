package org.wildstang.year2022.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.inputs.Input;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.roborio.inputs.WsJoystickButton;
import org.wildstang.hardware.roborio.outputs.WsSparkMax;
import org.wildstang.year2022.robot.WSInputs;
import org.wildstang.year2022.robot.WSOutputs;

/**
 * Turns the intake motor on/off based on joystick button states
 * @author foxler2010
 */
public class Intake implements Subsystem {

    WsSparkMax motor;
    WsJoystickButton forwardTrigger;
    WsJoystickButton backwardTrigger;

    @Override
    public void init() {
        motor = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.INTAKE_MOTOR);
        forwardTrigger = (WsJoystickButton) Core.getInputManager().getInput(WSInputs.DRIVER_FACE_DOWN);
        backwardTrigger = (WsJoystickButton) Core.getInputManager().getInput(WSInputs.DRIVER_FACE_UP);
    }

    @Override
    public void update() {

    }

    @Override
    public void inputUpdate(Input source) {
        if (source == forwardTrigger || source == backwardTrigger) {
            if (forwardTrigger.getValue()) {
                motor.setValue(1);
            } else if (backwardTrigger.getValue()) {
                motor.setValue(-1);
            } else {
                motor.setValue(0);
            }
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
        return "Intake Motor";
    }
}