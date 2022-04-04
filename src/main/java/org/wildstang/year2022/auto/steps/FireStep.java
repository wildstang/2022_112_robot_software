package org.wildstang.year2022.auto.steps;

import org.wildstang.framework.auto.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.year2022.robot.WSSubsystems;
import org.wildstang.year2022.subsystems.Ballpath;

public class FireStep extends AutoStep{

    private Ballpath ballpath;
    private boolean isLaunching;

    public FireStep(boolean shouldLaunch){
        isLaunching = shouldLaunch;
    }
    public void initialize(){
        ballpath = (Ballpath) Core.getSubsystemManager().getSubsystem(WSSubsystems.BALLPATH);
    }
    public void update(){
        ballpath.fire(isLaunching);
        setFinished();
    }
    public String toString(){
        return "Fire Step";
    }
}
