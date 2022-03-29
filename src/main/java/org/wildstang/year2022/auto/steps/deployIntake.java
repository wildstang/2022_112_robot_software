package org.wildstang.year2022.auto;

import org.wildstang.framework.auto.AutoStep;
import org.wildstang.framework.auto.steps.PathFollowerStep;
import org.wildstang.framework.core.Core;
import org.wildstang.year2022.robot.WSSubsystems;
import org.wildstang.year2022.subsystems.Ballpath;
import frc.paths.*;



public class deployIntake extends AutoStep {

    private Ballpath myBallpath;
    private boolean position;

    public void initialize(){

        myBallpath = (Ballpath) Core.getSubsystemManager().getSubsystem(WSSubsystems.BALLPATH.getName());

    }

    public void deployIntake(boolean state){
        position = state;
    }
    
@Override
    public void update(){

        if (position){
            myBallpath.intakeDeploy = true;
            myBallpath.wheelSpeed = 1;
            myBallpath.feedSpeed = 1;
            myBallpath.gateSpeed = 1;
        }
        else{
            myBallpath.intakeDeploy = false;
            myBallpath.wheelSpeed = 0;
            myBallpath.feedSpeed = 0;
            myBallpath.gateSpeed = 0;
        }
        setFinished(true);

    }
@Override
public String toString(){
    return "deployIntake";
}
}





