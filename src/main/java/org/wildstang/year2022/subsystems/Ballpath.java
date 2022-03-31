
 package org.wildstang.year2022.subsystems;

 import com.ctre.phoenix.motion.MotionProfileStatus;
 import com.kauailabs.navx.frc.AHRS;
 
 import org.wildstang.framework.core.Core;
 import com.revrobotics.CANSparkMax;

import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.inputs.DigitalInput;
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
 import org.wildstang.hardware.roborio.outputs.WsSolenoid;
 import org.wildstang.year2022.robot.WSInputs;
 import org.wildstang.year2022.robot.WSOutputs;
 import org.wildstang.framework.subsystems.Subsystem;
 
 import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.I2C;


public class Ballpath implements Subsystem{
    

    private DigitalInput Abutton, Ybutton, Xbutton;
    private AnalogInput Trigger;
    private WsSparkMax Wheel, Feed, Ball_Gate;
    private WsSolenoid Intake, Intake2;
    private boolean intakeDeploy;
    private double feedSpeed, wheelSpeed, gateSpeed;

    private enum feedStates{
        up, out, off;
    }
    private feedStates feedState;
    private enum wheelStates{
        forward, backward, off;
    }
    private wheelStates wheelState;

 @Override
 public void init(){

    Intake = (WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.INTAKE);
    Intake2 = (WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.INTAKE_FOLLOWER);
    Wheel = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.ARM_WHEEL);
    Feed = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.FEED);

    Ball_Gate = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.BALL_GATE);
    Wheel.setCurrentLimit(25, 25, 0);
    Feed.setCurrentLimit(25, 25, 0);
    Ball_Gate.setCurrentLimit(25, 25, 0);

    Abutton = (DigitalInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_FACE_DOWN);
         Abutton.addInputListener(this);
    Xbutton = (DigitalInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_FACE_LEFT);
         Xbutton.addInputListener(this);

    Ybutton = (DigitalInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_FACE_UP);
         Ybutton.addInputListener(this);
    Trigger = (AnalogInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_RIGHT_TRIGGER);
         Trigger.addInputListener(this);
    
    resetState();

}

@Override
public void inputUpdate(Input source){


    if (Trigger.getValue() > 0.25){
       gateSpeed = 1;
    }    
    else {
        gateSpeed = 0;
    }


    if (Abutton.getValue()){

        intakeDeploy = true;
        wheelState = wheelStates.forward;
        feedState = feedStates.up;

    }
    else if (Ybutton.getValue()){
        
        intakeDeploy = true;
        wheelState = wheelStates.backward;
        feedState = feedStates.out;
    }
    else if (Xbutton.getValue()){

        feedState = feedStates.up;
        intakeDeploy = false;
        wheelState = wheelStates.off; 

    }
    else {
        intakeDeploy = false;
        feedState = feedStates.off;
        wheelState = wheelStates.off;

    }

    if(feedState == feedStates.up){
        feedSpeed = -1;
    }

    if(feedState == feedStates.out){
        feedSpeed = 1;
    }

    if(feedState == feedStates.off){
        feedSpeed = 0;
    }

    if(wheelState == wheelStates.forward){
        wheelSpeed = 1;
    }

    if(wheelState == wheelStates.backward){
        wheelSpeed = -1;
    }

    if(wheelState == wheelStates.off){
        wheelSpeed = 0;
    }

}

@Override
public void update(){

    Feed.setSpeed(feedSpeed);
    Wheel.setSpeed(wheelSpeed);
    Intake.setValue(intakeDeploy);
    Intake2.setValue(intakeDeploy);
    Ball_Gate.setSpeed(gateSpeed);
    SmartDashboard.putNumber("Intake", wheelSpeed);


}

@Override
public void selfTest(){

}

@Override
public void resetState() {

  feedSpeed = 0;
  wheelSpeed = 0;
  gateSpeed = 0;
  intakeDeploy = false;
  feedState = feedStates.off;
  wheelState = wheelStates.off;
}

@Override
public String getName(){
    return "Ballpath";
}

}
