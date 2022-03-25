package org.wildstang.year2022.auto;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.PathFollowerStep;
import org.wildstang.framework.core.Core;
import org.wildstang.year2022.robot.WSSubsystems;
import org.wildstang.year2022.subsystems.Ballpath;
import frc.paths.*;


public class RunFeed{
   
    @Override 
    public void init(){

        myBallpath = Core.getSubsystemManager.getSubsystem(WSSubsystems.BALLPATH.getName());

    }

    @Override
    public void update(){
    
        myBallpath.myfeedSpeed.setSpeed(1);

        if (myBallpath.myfeedSpeed.getVelocity() == 1){
            getFinished(true);

    }

}
public class StopFeed{
   
    @Override 
    public void init(){

        myBallpath = Core.getSubsystemManager.getSubsystem(WSSubsystems.BALLPATH.getName());

    }

    @Override
    public void update(){
    
        myBallpath.myfeedSpeed.setSpeed(0);

        if (myBallpath.myfeedSpeed.getVelocity() == 0){
            getFinished(true);

    }

}
