package org.wildstang.year2022.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.control.AutoStepDelay;
import org.wildstang.year2022.auto.steps.AutoDriveStep;
import org.wildstang.year2022.auto.steps.DeployIntakeStep;
import org.wildstang.year2022.auto.steps.FireStep;
import org.wildstang.year2022.auto.steps.LimelightStep;

public class TwoBall extends AutoProgram{

    public void defineSteps(){

        //deploy intake, turn on intake and feed
        addStep(new DeployIntakeStep(true));
        //wait like 0.5 sec
        addStep(new AutoStepDelay(500));
        //drive backwards at like 30% power for 2 seconds
        addStep(new AutoDriveStep(2.0, true));
        //activate limelight
        addStep(new LimelightStep(true));
        //wait like 1 sec
        addStep(new AutoStepDelay(1000));
        //turn on ball gate motor
        addStep(new FireStep(true));
        addStep(new AutoStepDelay(2000));

        // //optional 4 ball addition
        // //turn off firing and limelight
        // addStep(new FireStep(false));
        // addStep(new LimelightStep(false));
        // //turn robot to angle
        // addStep(new AutoRotateStep(0.2, true));
        // //drive backwards
        // addStep(new AutoDriveStep(4.0, true));
        // addStep(new AutoStepDelay(1000));
        // //drive forwards
        // addStep(new AutoDriveStep(4.0, false));
        // //activate limelight, wait 0.5 sec
        // addStep(new LimelightStep(true));
        // //fire
        // addStep(new FireStep(true));

    }

    public String toString(){
        return "Two Ball";
    }
    
}
