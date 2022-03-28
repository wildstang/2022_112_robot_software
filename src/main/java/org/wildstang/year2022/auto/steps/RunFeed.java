package org.wildstang.year2022.auto;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.PathFollowerStep;
import org.wildstang.framework.core.Core;
import org.wildstang.year2022.robot.WSSubsystems;
import org.wildstang.year2022.subsystems.Ballpath;
import frc.paths.*;


public class RunFeed{

    private Ballpath myBallpath;
   

    public void init(){

        myBallpath = (Ballpath) Core.getSubsystemManager().getSubsystem(WSSubsystems.BALLPATH.getName());

    }


    public void update(){
    
        myBallpath.feedSpeed = 1;

        if (myBallpath.feedSpeed == 1){
            this.setFinished(true);
        }

    }

}

