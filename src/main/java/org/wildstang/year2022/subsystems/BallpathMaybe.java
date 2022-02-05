
 package org.wildstang.framework.subsystems;

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
 
 import edu.wpi.first.wpilibj.Notifier;
 import edu.wpi.first.wpilibj.I2C;


public class BallpathMaybe implements Subsystem {
    
    private WsDigitalInput Abutton, Ybutton, Xbutton;
    private WsAnalogInput Trigger;
    private WsSparkMax Wheel, Feed;
    private WsSolenoid Intake;
    private boolean intakeDeploy;
    private double feedSpeed, wheelSpeed;
    

 @Override
 public void init(){

    Intake = (WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.INTAKE);
    Wheel = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.WHEEL);
    Feed = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.FEED);

    Abutton = (WsDigitalInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_FACE_DOWN);
         Abutton.addInputListener(this);
    Xbutton = (WsDigitalInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_FACE_LEFT);
         Xbutton.addInputListener(this);
    Ybutton = (WsDigitalInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_FACE_UP);
         Ybutton.addInputListener(this);
    Trigger = (WsAnalogInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_RIGHT_TRIGGER);
         Trigger.addInputListener(this);
    
    resetState();

}

@Override
public void inputUpdate(Input source){

    if (Trigger.getValue() > 0.05){
        feedSpeed = Trigger.getValue();
    }    

    else if(feedSpeed > 0.05){
        feedSpeed = 0;
    }

    if (Abutton.getValue() && source == Abutton){

        intakeDeploy = !intakeDeploy;

        if(wheelSpeed != 0){
            wheelSpeed = 0;
        }

        else {
            wheelSpeed = 1;
        }

    }

    if (Ybutton.getValue() && source == Ybutton){
        
        if(wheelSpeed == 1){
            wheelSpeed = -1;
        }

        else if(wheelSpeed == -1){
            wheelSpeed = 1;
        }

    }


    if(Xbutton.getValue() && source == Xbutton){

        if(feedSpeed == 0){
            feedSpeed = -1;
        }

        else if(feedSpeed == -1){
            feedSpeed = 0;
        }

    }
}

@Override
public void update(){

    Feed.setSpeed(feedSpeed);
    Wheel.setSpeed(wheelSpeed);
    Intake.setValue(intakeDeploy);

}

@Override
public void selfTest(){

}

@Override
public void resetState() {

  feedSpeed = 0;
  wheelSpeed= 0;
  intakeDeploy = false;

}

@Override
public String getName(){
    return "Ballpath Maybe";
}

}
