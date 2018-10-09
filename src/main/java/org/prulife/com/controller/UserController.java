package org.prulife.com.controller;

        import lombok.Data;
        import org.prulife.com.entities.Users;
        import org.prulife.com.services.DummyService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.MediaType;
        import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private DummyService myService;

    @RequestMapping(value="/process", method= RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Users startProcessInstance(@RequestBody UserBody userBody) {
        Users user = myService.getUser(userBody.test);
        return user;
    }

    @RequestMapping(value="/process2", method= RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody TestModel startProcessInstance2(String body) {
         TestModel model = new TestModel();

        model.setField1("12312321");
        model.setField2("1231321");
        return model;
    }

    static class UserBody {
        public String test;

    }

    @Data
    class TestModel{
        private String field1;
        private String field2;

    }
}
