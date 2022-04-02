package org.wildstang.year2022.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.control.AutoStepDelay;
import org.wildstang.year2022.auto.steps.DeployIntakeStep;

public class TwoBall extends AutoProgram{

    public void defineSteps(){

        //deploy intake, turn on intake and feed
        addStep(new DeployIntakeStep(true));
        //wait like 0.5 sec
        addStep(new AutoStepDelay(500));
        //drive backwards at like 30% power for 2 seconds
        //activate limelight
        //wait like 1 sec
        addStep(new AutoStepDelay(1000));
        //turn on ball gate motor

    }

    public String toString(){
        return "Two Ball";
    }
    
}
