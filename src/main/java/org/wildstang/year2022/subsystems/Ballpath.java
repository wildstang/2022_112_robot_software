
 package org.wildstang.year2022.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.inputs.Input;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.roborio.inputs.WsAnalogInput;
import org.wildstang.hardware.roborio.inputs.WsDigitalInput;
import org.wildstang.hardware.roborio.outputs.WsSolenoid;
import org.wildstang.hardware.roborio.outputs.WsSparkMax;
import org.wildstang.year2022.robot.WSInputs;
import org.wildstang.year2022.robot.WSOutputs;


public class Ballpath implements Subsystem {
    
    //inputs
    private WsDigitalInput aButton, bButton, xButton; //buttons
    private WsAnalogInput trigger; //trigger

    //outputs
    private WsSparkMax intakeMotor, feed, ballgate; //motors
    private WsSolenoid intakeSolenoid; //solenoid



 @Override
 public void init(){

    intakeSolenoid = (WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.INTAKE); //init intake solenoid

    intakeMotor = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.ARM_WHEEL); //init intake motor

    ballgate = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.BALLGATE); //init ballgate motor

    feed = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.FEED); //init feed motor

    aButton = (WsDigitalInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_FACE_RIGHT); //init A Button
    aButton.addInputListener(this); //A Button inputListener

    xButton = (WsDigitalInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_FACE_UP); //init X Button
    xButton.addInputListener(this); //X Button inputListener

    bButton = (WsDigitalInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_FACE_DOWN); //init B Button
    bButton.addInputListener(this); //B Button inputListener

    trigger = (WsAnalogInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_RIGHT_TRIGGER); //init trigger
    trigger.addInputListener(this); //trigger inputListener
    
    resetState(); //go to def state when subsystem is initialized

    }

    @Override
    public void inputUpdate(Input source){

        if (aButton.getValue()) { //if A button is pressed

            intakeSolenoid.setValue(true); //deploy intake
            intakeMotor.setValue(1); //run intake
            feed.setValue(1); //run feed

        } else if (aButton.getValue() == false) { //if A button is un-pressed

            intakeSolenoid.setValue(false); //un-deploy intake
            intakeMotor.stop(); //stop intake
            feed.stop(); //stop feed

        } else if (xButton.getValue()) { //if X button is pressed

            feed.setValue(1); //run feed

        } else if (xButton.getValue() == false) { //if X button is un-pressed

            feed.stop(); //stop feed

        } else if (bButton.getValue()) { //if B button is pressed

            intakeSolenoid.setValue(true); //deploy intake
            intakeMotor.setValue(-1); //run intake backwards
            feed.setValue(-1); //run feed backwards

        } else if (bButton.getValue() == false) { //if B button is un-pressed

            intakeSolenoid.setValue(false); //un-deploy intake
            intakeMotor.stop(); //stop intake
            feed.stop(); //stop feed

        }

    }

    @Override
    public void update(){

        ballgate.setValue(trigger.getValue()); //set ballgate motor speed to the trigger value

    }

    @Override
    public void selfTest(){

        //hmmm, what to do here?

    }

    @Override
    public void resetState() {

        intakeMotor.stop(); //stop intake motor
        feed.stop(); //stop feed motor
        ballgate.stop(); //stop ballgate motor
        intakeSolenoid.setValue(false); //retract intake solenoid

    }

    @Override
    public String getName(){

        return "Ballpath";

    }

}
