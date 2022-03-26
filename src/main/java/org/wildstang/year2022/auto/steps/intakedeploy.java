package org.wildstang.year2022.auto;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.PathFollowerStep;
import org.wildstang.framework.core.Core;
import org.wildstang.year2022.robot.WSSubsystems;
import org.wildstang.year2022.subsystems.Ballpath;
import frc.paths.*;



public class IntakeExtend{

    private Ballpath myBallpath;


    public void init(){

        myBallpath = Core.getSubsystemManager.getSubsystem(WSSubsystems.BALLPATH.getName());

    }
    

    public void update(){

        myBallpath.myintakeDeploy.setValue(true);

        if (myBallpath.myintakeDeploy.getValue() == true){
            setFinished(true);
        }

    }
}

public class IntakeRetract{

    private Ballpath myBallpath;


    public void init(){

        myBallpath = Core.getSubsystemManager.getSubsystem(WSSubsystems.BALLPATH.getName());

    }
    

    public void update(){

        myBallpath.myintakeDeploy.setValue(false);

        if (myBallpath.myintakeDeploy.getValue() == false){
            setFinished(true);
        }

    }
}


