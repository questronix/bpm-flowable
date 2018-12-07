package org.prulife.com.utilities;


import org.omg.CORBA.OBJ_ADAPTER;
import org.prulife.com.entities.ResponseModel;

import java.util.Map;

public class ResponseUtility {

    public static ResponseModel returnSuccessResponseModel(){

        ResponseModel responseModel = new ResponseModel();
        responseModel.setStatus("success");
        responseModel.setStatusCode("0");
        responseModel.setIsSuccess(true);
        responseModel.setMessage("Ok");
        return responseModel;

    }

    public static ResponseModel returnSuccessResponseModel(Map<String, Object> result){

        ResponseModel responseModel = new ResponseModel();
        responseModel.setStatus("success");
        responseModel.setStatusCode("0");
        responseModel.setIsSuccess(true);
        responseModel.setMessage("Ok");
        responseModel.setResult(result);
        return responseModel;

    }

    public static ResponseModel returnFailedResponseModel(){

        ResponseModel responseModel = new ResponseModel();
        responseModel.setStatus("fail");
        responseModel.setStatusCode("1");
        responseModel.setIsSuccess(false);
        responseModel.setMessage("Error encountered while processing request.");
        return responseModel;

    }

}
