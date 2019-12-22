package cn.tacitech.cloudfuncmodule;

public class CloudFuncException extends Exception {

    private String detailedMessage;

    public CloudFuncException(String e){
        super(e);
        detailedMessage = e;
    }

    public String getMessage(){
        return detailedMessage;
    }

}
