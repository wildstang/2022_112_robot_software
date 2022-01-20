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

    //intake motor
    WsSparkMax motor;
    //a button
    WsJoystickButton forwardTrigger;
    //y button
    WsJoystickButton backwardTrigger;

    @Override
    public void init() {
        //intake motor
        motor = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.INTAKE_MOTOR);
        //a button
        forwardTrigger = (WsJoystickButton) Core.getInputManager().getInput(WSInputs.DRIVER_FACE_DOWN);
        //y button
        backwardTrigger = (WsJoystickButton) Core.getInputManager().getInput(WSInputs.DRIVER_FACE_UP);
    }

    @Override
    public void update() {

    }

    @Override
    public void inputUpdate(Input source) {
        //if the a button or y button has changed state
        if (source == forwardTrigger || source == backwardTrigger) {
            //if the a button is currently pressed
            if (forwardTrigger.getValue()) {
                //spin motor forward at full speed
                motor.setValue(1);
            } else if (backwardTrigger.getValue()) { //if the y button is currently pressed
                //spin motor backward at full speed
                motor.setValue(-1);
            } else { //if a or y buttons are NOT currently pressed
                //stop motor from spinning
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