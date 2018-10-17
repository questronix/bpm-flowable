package org.prulife.com.delegates;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class ReferToUCorAML implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("ReferToUCorAML is Called!");
    }
}
