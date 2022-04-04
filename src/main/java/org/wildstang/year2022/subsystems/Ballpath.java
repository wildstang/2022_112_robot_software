
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
    private double intakeMotorSpeed, feedSpeed, ballgateSpeed; //motor speeds
    private WsSolenoid intakeSolenoid; //solenoid
    private boolean intakeSolenoidState; //solenoid state



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

            intakeSolenoidState = true;
            intakeMotorSpeed = 1; //run intake
            feedSpeed = 1; //run feed

        } else if (aButton.getValue() == false) { //if A button is un-pressed

            intakeSolenoidState = false; //un-deploy intake
            intakeMotorSpeed = 0; //stop intake
            feedSpeed = 0; //stop feed

        } else if (xButton.getValue()) { //if X button is pressed

            feedSpeed = 1; //run feed

        } else if (xButton.getValue() == false) { //if X button is un-pressed

            feedSpeed = 0; //stop feed

        } else if (bButton.getValue()) { //if B button is pressed

            intakeSolenoidState = true; //deploy intake
            intakeMotorSpeed = -1; //run intake backwards
            feedSpeed = -1; //run feed backwards

        } else if (bButton.getValue() == false) { //if B button is un-pressed

            intakeSolenoidState = false; //un-deploy intake
            intakeMotorSpeed = 0; //stop intake
            feedSpeed = 0; //stop feed

        } else if (source == trigger) {

            ballgateSpeed = trigger.getValue();

        }

    }

    @Override
    public void update(){

        intakeMotor.setValue(intakeMotorSpeed);
        feed.setValue(feedSpeed);
        ballgate.setValue(ballgateSpeed);
        intakeSolenoid.setValue(intakeSolenoidState);

    }

    @Override
    public void selfTest(){

        //hmmm, what to do here?

    }

    @Override
    public void resetState() {

        intakeMotorSpeed = 0; //stop intake motor
        feedSpeed = 0; //stop feed motor
        ballgateSpeed = 0; //stop ballgate motor
        intakeSolenoidState = false; //retract intake solenoid

    }

    @Override
    public String getName(){

        return "Ballpath";

    }

}
