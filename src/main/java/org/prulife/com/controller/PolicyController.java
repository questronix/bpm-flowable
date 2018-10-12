package org.prulife.com.controller;

import org.prulife.com.entities.Policy;
import org.prulife.com.entities.Users;
import org.prulife.com.services.PolicyService;
import org.prulife.com.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:9000", maxAge = 3600)
public class PolicyController {
    @Autowired
    private PolicyService policyService;

    @Autowired
    private UsersService usersService;

    @Autowired

    @RequestMapping(value="/policy", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Policy> index() {
        return policyService.getAll();
    }

    @RequestMapping(value="/policy/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Policy findById(@PathVariable("id") Long id) {
        return policyService.findById(id);
    }

    @RequestMapping(value="/policy", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Policy save(@RequestBody PolicyBody policyBody) {
        Users user = usersService.findById(policyBody.createdBy);
        Policy policy = new Policy();
        policy.setCreatedBy(user);
        policy.setNumber(policyBody.number);
        policy.setAgentCode(policyBody.agentCode);
        policy.setAgentName(policyBody.agentName);
        policy.setBranch(policyBody.branch);
        policy.setNma(policyBody.nma);
        policy.setPlanDesc(policyBody.planDesc);
        policy.setPlanCurrency(policyBody.planCurrency);
        policy.setContractStatus(policyBody.contractStatus);
        policy.setPremiumStatus(policyBody.premiumStatus);
        policy.setSumAssured(policyBody.sumAssured);
        policy.setRcd(policyBody.rcd);
        policy.setFirstIssueDate(policyBody.firstIssueDate);
        policy.setSalutation(policyBody.salutation);
        policy.setFirstName(policyBody.firstName);
        policy.setLastName(policyBody.lastName);
        policy.setGender(policyBody.gender);
        policy.setOccupation(policyBody.occupation);
        policy.setHrc(policyBody.hrc);
        policy.setVip(policyBody.vip);
        policy.setStr(policyBody.str);
        policy.setNationality(policyBody.nationality);
        policy.setDateOfBirth(policyBody.dateOfBirth);
        policy.setAttainedAge(policyBody.attainedAge);
        policy.setCivilStatus(policyBody.civilStatus);
        policy.setTelNumber(policyBody.telNumber);
        policy.setMobileNumber(policyBody.mobileNumber);
        policy.setTinOrSss(policyBody.tinOrSss);
        policy.setEmail(policyBody.tinOrSss);

        return policyService.save(policy);
    }

    static class PolicyBody {

        //PolicyRepository Information
        public String number;
        public String agentCode;
        public String agentName;
        public String branch;
        public String nma;
        public String planDesc;
        public String planCurrency;
        public String contractStatus;
        public String premiumStatus;
        public double sumAssured = 0.0;
        public Date rcd;
        public Date firstIssueDate;

        //Insured Information
        public String salutation;
        public String firstName;
        public String lastName;
        public String gender;
        public String occupation;
        public String hrc;
        public String vip;
        public String str;
        public String nationality;
        public Date dateOfBirth;
        public String attainedAge;
        public String civilStatus;
        public String telNumber;
        public String mobileNumber;
        public String tinOrSss;
        public String email;

        public Long createdBy;
    }
}
