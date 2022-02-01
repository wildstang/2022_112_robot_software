
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
 
 public class Everybotthing implements Subsystem {
 
     private WsSparkMax Roller;
     private WsDigitalInput Abutton, Ybutton, Bbutton;
     private double Spid;
     private WsSolenoid Arm;
     private Boolean B;
     private boolean armPosition; 
 
 
 @Override
 public void init(){
 
     Arm = (WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.ARM);
     Roller = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.ROLLER);
     Abutton = (WsDigitalInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_FACE_DOWN);
         Abutton.addInputListener(this);
     Bbutton = (WsDigitalInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_FACE_RIGHT);
         Bbutton.addInputListener(this);
     Ybutton = (WsDigitalInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_FACE_UP);
         Ybutton.addInputListener(this);
 }
 @Override
 public void inputUpdate(Input Source){
 
     Boolean A = Abutton.getValue();
     Boolean B = Bbutton.getValue();
     Boolean Y = Ybutton.getValue();
 
     if (A){
         Spid = 1;
     }
     if (Y){
         Spid = -1;
     }
     if (B){
         armPosition = !armPosition;
     }
 }
 @Override
 public void update(){
 
     Roller.setSpeed(Spid);
     Arm.setValue(armPosition);
     
 }
 
 @Override
 public void selfTest(){
 
 }
 
 @Override
 public void resetState() {
 
     armPosition = false;
     B = false;
     Spid = 0;
 
 }
 
 @Override
 public String getName(){
     return "Everybot thing";
 }
 }