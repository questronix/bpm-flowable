package org.prulife.com.services;

import org.springframework.stereotype.Component;

@Component(value = "TaskAssignment")
public class Assignment {

    public String assignCsaPrescreener(String username) {
        return username;
    }

    public String assignCsaProcessor(String username) {
        return username;
    }

    public String assignCsaApprover(String unitHead) {
        return unitHead;
    }

    public String assignAsmProcessor() {
        return "lexlugerb";
    }
}
