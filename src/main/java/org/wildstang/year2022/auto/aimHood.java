package org.wildstang.year2022.auto;

import org.wildstang.framework.auto.AutoStep;
import org.wildstang.framework.auto.steps.PathFollowerStep;
import org.wildstang.framework.core.Core;
import org.wildstang.year2022.robot.WSSubsystems;
import org.wildstang.year2022.subsystems.hood;
import frc.paths.*;

public class aimHood extends AutoStep{

    private hood myHood;

    @Override
    public void initialize(){

        myHood = (hood) Core.getSubsystemManager().getSubsystem(WSSubsystems.HOOD);
    }
    
    @Override
    public void update(){

        myHood.limelightHood(true);

    }

    @Override
    public String toString(){

        return "aimHood";

    }
}
