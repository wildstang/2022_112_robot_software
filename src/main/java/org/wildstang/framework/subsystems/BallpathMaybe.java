
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
 import org.wildstang.sample.robot.WSInputs;
 import org.wildstang.sample.robot.WSOutputs;
 
 import edu.wpi.first.wpilibj.Notifier;
 import edu.wpi.first.wpilibj.I2C;


public class BallpathMaybe implements Subsystem {
    
    private WsDigitalInput Abutton, Ybutton, Xbutton;
    private WsAnalogInput Trigger;
    private WsSparkMax Wheel, Feed;
    private WsSolenoid Intake;
    private boolean wheelDirection, wheelRun, intakeDeploy, state;
    private double feedSpeed, actualDirection, wheelSpeed;
    

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

}

@Override
public void inputUpdate(Input Source){

    boolean A = Abutton.getValue();
    boolean X = Xbutton.getValue();
    boolean Y = Ybutton.getValue();
    double feedSpeed = Trigger.getValue();

    if (A){

        wheelRun = !wheelRun;
        intakeDeploy = !intakeDeploy;

    }

    if (Y){
        wheelDirection = !wheelDirection;
    } 
    
    if(wheelDirection = true){
        actualDirection = 1;
    }

    if(wheelDirection = false){
        actualDirection = -1;
    }

    wheelSpeed = actualDirection;
    
    if(wheelRun = false){
        wheelSpeed = 0;
    }

    if(X){
        state = !state;
    }

    if(state = false){
        feedSpeed = 0;
        wheelSpeed = 0;
        intakeDeploy = false;
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
  wheelSpeed = 0;
  intakeDeploy = false;
  wheelDirection = false;
  wheelRun = false;
  state = false;
  actualDirection = 0;

}

@Override
public String getName(){
    return "Ballpath Maybe";
}

}
