
 package org.wildstang.year2022.subsystems;

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
 import org.wildstang.hardware.roborio.outputs.WsSolenoid;
 import org.wildstang.year2022.robot.WSInputs;
 import org.wildstang.year2022.robot.WSOutputs;
 import org.wildstang.framework.subsystems.Subsystem;
 
 import edu.wpi.first.wpilibj.Notifier;
 import edu.wpi.first.wpilibj.I2C;


public class Ballpath implements Subsystem{
    
    private WsDigitalInput Abutton, Bbutton, Xbutton;
    private WsAnalogInput Trigger;
    private WsSparkMax Wheel, Feed, Ballgate;
    private WsSolenoid Intake;
    public boolean intakeDeploy;
    public double feedSpeed, wheelSpeed, gateSpeed;
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
    Wheel = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.ARM_WHEEL);
    Feed = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.FEED);
    Ballgate = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.BALLGATE);

    Abutton = (WsDigitalInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_FACE_DOWN);
         Abutton.addInputListener(this);
    Xbutton = (WsDigitalInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_FACE_LEFT);
         Xbutton.addInputListener(this);
    Bbutton = (WsDigitalInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_FACE_RIGHT);
         Bbutton.addInputListener(this);
    Trigger = (WsAnalogInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_RIGHT_TRIGGER);
         Trigger.addInputListener(this);
    
    resetState();

}

@Override
public void inputUpdate(Input source){

    if (Trigger.getValue() > 0.05){
       gateSpeed = Trigger.getValue();
    }    

    else if(Trigger == source){
        feedState = feedStates.off;
    }

    if (Abutton.getValue() && source == Abutton){

        intakeDeploy = !intakeDeploy;

        if(intakeDeploy == false){
            wheelState = wheelStates.off;
            feedState = feedStates.off;
        }

        else if(wheelState == wheelStates.off){
            wheelState = wheelStates.forward;
            feedState = feedStates.up;
        }

    }

    if (Bbutton.getValue() && source == Bbutton){
        
        intakeDeploy = !intakeDeploy;

        if(intakeDeploy == false){
            wheelState = wheelStates.off;
            feedState = feedStates.off;
        }

        else if(wheelState == wheelStates.off){
            wheelState = wheelStates.backward;
            feedState = feedStates.out;
        }

    }


    if(Xbutton.getValue() && source == Xbutton){

        if(feedState == feedStates.off){
            feedState = feedStates.up;
        }

        else if(feedState == feedStates.up){
            feedState = feedStates.off;
        }

    }

    if(feedState == feedStates.up){
        feedSpeed = 1;
    }

    if(feedState == feedStates.out){
        feedSpeed = -1;
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
    Ballgate.setSpeed(gateSpeed);

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

public void startBallpath(){
    intakeDeploy = true;
    wheelSpeed = 1;
    feedSpeed = 1;
    gateSpeed = 1;
}
public void stopBallpath(){
    intakeDeploy = false;
    wheelSpeed = 0;
    feedSpeed = 0;
    gateSpeed = 0;
}


@Override
public String getName(){
    return "Ballpath";
}

}
