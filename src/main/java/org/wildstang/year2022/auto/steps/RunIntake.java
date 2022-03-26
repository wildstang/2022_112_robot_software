package org.wildstang.year2022.auto;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.PathFollowerStep;
import org.wildstang.framework.core.Core;
import org.wildstang.year2022.robot.WSSubsystems;
import org.wildstang.year2022.subsystems.drive.Drive;
import frc.paths.*;


public class IntakeForward{
   
    private Ballpath myBallpath;


    public void init(){

        myBallpath = Core.getSubsystemManager.getSubsystem(WSSubsystems.BALLPATH.getName());

    }


    public void update(){
    
        myBallpath.mywheelSpeed.setSpeed(1);

        if (myBallpath.mywheelSpeed.getVelocity() == 1){
            setFinished(true);
        }
    

    }

}

public class IntakeOff{

    private Ballpath myBallpath;
   

    public void init(){

        myBallpath = Core.getSubsystemManager.getSubsystem(WSSubsystems.BALLPATH.getName());

    }


    public void update(){
    
        myBallpath.mywheelSpeed.setSpeed(0);

        if (myBallpath.mywheelSpeed.getVelocity() == 0){
            setFinished(true);
        }

    }

}