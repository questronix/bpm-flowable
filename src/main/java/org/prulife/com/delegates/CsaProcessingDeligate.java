package org.prulife.com.delegates;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class CsaProcessingDeligate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("CsaProcessingDeligate is Called!");
    }
}
