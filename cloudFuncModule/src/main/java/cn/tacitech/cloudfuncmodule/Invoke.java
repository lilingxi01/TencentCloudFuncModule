package cn.tacitech.cloudfuncmodule;

import com.alibaba.fastjson.JSONObject;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.scf.v20180416.ScfClient;
import com.tencentcloudapi.scf.v20180416.models.InvokeRequest;
import com.tencentcloudapi.scf.v20180416.models.InvokeResponse;

import java.util.Map;
import java.util.Set;

public class Invoke {
    private String result;
    private String functionName;
    private String parameter;

    private String secretId;
    private String secretKey;
    private String endpoint;
    private String region;

    public Invoke(String secretId, String secretKey, String region) {
        result = "";
        functionName = "";
        this.secretId = secretId;
        this.secretKey = secretKey;
        this.endpoint = "scf.tencentcloudapi.com";
        this.region = region;
    }

    public Invoke(String secretId, String secretKey, String endpoint, String region) {
        result = "";
        functionName = "";
        this.secretId = secretId;
        this.secretKey = secretKey;
        this.endpoint = endpoint;
        this.region = region;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getResult(){
        try {
            Credential cred = new Credential(secretId, secretKey);
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(endpoint);
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            ScfClient client = new ScfClient(cred, region, clientProfile);
            String stringParameter = convertParameter();
            String params = "{\"FunctionName\":\"" + functionName + "\",\"ClientContext\":\"" + stringParameter + "\"}";
            InvokeRequest req = InvokeRequest.fromJsonString(params, InvokeRequest.class);
            InvokeResponse resp = client.Invoke(req);
            result = InvokeRequest.toJsonString (resp);
            result = jToString(result);
            return result;
        } catch (TencentCloudSDKException e) {
            result = e.toString();
            return result;
        }
    }

    private String convertParameter(){
        String result = "{";
        JSONObject jObject = JSONObject.parseObject(parameter);
        Set<Map.Entry<String, Object>> set = jObject.entrySet();
        for (Map.Entry<String, Object> i: set){
            String value = i.getValue().toString();
            value = value.replaceAll("\\n", "\\\\\\\\n");
            result = result + "\\\"" + i.getKey() + "\\\":\\\"" + value + "\\\",";
        }
        result = result.substring(0, result.length() - 1);
        result = result + "}";
        return result;
    }

    private String jToString(String s){
        JSONObject jObject = JSONObject.parseObject(s);
        JSONObject res = jObject.getJSONObject("Result");
        String ret = res.getString("RetMsg");
        return ret;
    }
}
