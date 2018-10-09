package org.prulife.com.delegates;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class External implements JavaDelegate {
    public void execute(DelegateExecution execution) {
        System.out.println("External is Called!");
    }
}
