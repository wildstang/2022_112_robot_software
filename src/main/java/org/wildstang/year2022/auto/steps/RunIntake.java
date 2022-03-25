package org.wildstang.year2022.auto;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.PathFollowerStep;
import org.wildstang.framework.core.Core;
import org.wildstang.year2022.robot.WSSubsystems;
import org.wildstang.year2022.subsystems.drive.Drive;
import frc.paths.*;


public class IntakeForward{
   
    @Override 
    public void init(){

        myBallpath = Core.getSubsystemManager.getSubsystem(WSSubsystems.BALLPATH.getName());

    }

    @Override
    public void update(){
    
        myBallpath.mywheelSpeed.setSpeed(1);

        if (myBallpath.mywheelSpeed.getVelocity() == 1){
            getFinished(true);
        }
    

    }

}

public class IntakeOff{
   
    @Override 
    public void init(){

        myBallpath = Core.getSubsystemManager.getSubsystem(WSSubsystems.BALLPATH.getName());

    }

    @Override
    public void update(){
    
        myBallpath.mywheelSpeed.setSpeed(0);

        if (myBallpath.mywheelSpeed.getVelocity() == 0){
            getFinished(true);
        }

    }

}