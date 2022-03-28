package org.wildstang.year2022.auto;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.PathFollowerStep;
import org.wildstang.framework.core.Core;
import org.wildstang.year2022.robot.WSSubsystems;
import org.wildstang.year2022.subsystems.Ballpath;
import frc.paths.*;


public class IntakeForward{
   
    private Ballpath myBallpath;


    public void init(){

        myBallpath = (Ballpath) Core.getSubsystemManager().getSubsystem(WSSubsystems.BALLPATH.getName());

    }


    public void update(){
    
        myBallpath.wheelSpeed = 1;

        if (myBallpath.wheelSpeed == 1){
            this.setFinished(true);
        }
    

    }

}

