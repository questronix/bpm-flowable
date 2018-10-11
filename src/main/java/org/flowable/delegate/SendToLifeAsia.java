package org.flowable.delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class SendToLifeAsia implements JavaDelegate {

    public void execute(DelegateExecution execution) {

        System.out.println("Send to LifeAsia is Called!");
    }
}
