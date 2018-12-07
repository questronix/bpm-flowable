package org.prulife.com.delegates;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class SendEmail implements JavaDelegate {
    public void execute(DelegateExecution execution) {

        System.out.println("SendEmail is Called!");
    }
}
