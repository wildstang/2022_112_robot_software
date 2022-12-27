
package org.wildstang.year2022.auto;

import org.wildstang.framework.auto.AutoStep;
import org.wildstang.framework.auto.steps.PathFollowerStep;
import org.wildstang.framework.core.Core;
import org.wildstang.year2022.robot.WSSubsystems;
import org.wildstang.year2022.subsystems.Launcher;
import frc.paths.*;


public class flywheelOn extends AutoStep { 
    
    private Launcher myLauncher;

    public void initialize(){

        myLauncher = (Launcher) Core.getSubsystemManager().getSubsystem(WSSubsystems.LAUNCHER.getName());
    }


    public void update(){

        myLauncher.launcherOn();

    }

    public String toString(){

        return "flywheelOn";

    }

}
