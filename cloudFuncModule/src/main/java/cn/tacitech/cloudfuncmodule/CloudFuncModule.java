package cn.tacitech.cloudfuncmodule;

import android.os.AsyncTask;

import com.alibaba.fastjson.JSONObject;

public class CloudFuncModule {

    private static String secretId = "", secretKey = "", endpoint = "", region = ""; // 重要数据

    private String functionName;
    private CloudFuncTask cloudFuncTask;
    private CloudFuncRunnableModule cloudFuncRunnableModule;
    private JSONObject parameters;

    public CloudFuncModule(String functionName){
        this.functionName = functionName;
        this.parameters = new JSONObject();
    }

    /**
     * 在程序开始时设置接口信息（不需要每次都调用）
     * @param secretId
     * @param secretKey
     * @param region
     */
    public static void setBasicInfo(String secretId, String secretKey, String region){
        CloudFuncModule.secretId = secretId;
        CloudFuncModule.secretKey = secretKey;
        CloudFuncModule.endpoint = "";
        CloudFuncModule.region = region;
    }

    /**
     * 在程序开始时设置接口信息（不需要每次都调用）
     * @param secretId
     * @param secretKey
     * @param endpoint
     * @param region
     */
    public static void setBasicInfo(String secretId, String secretKey, String endpoint, String region){
        CloudFuncModule.secretId = secretId;
        CloudFuncModule.secretKey = secretKey;
        CloudFuncModule.endpoint = endpoint;
        CloudFuncModule.region = region;
    }

    /**
     * 向参数库添加参数
     * @param parameterKey
     * @param parameterValue
     */
    public void addParameter(String parameterKey, String parameterValue){
        parameters.put(parameterKey, parameterValue);
    }

    /**
     * 开始调用云函数
     */
    public void start() {
        try {
            if(functionName.equals("")) {
                throw new CloudFuncException("Function name is missing.");
            } else if(secretId.equals("") || secretKey.equals("") || region.equals("")){
                throw new CloudFuncException("Basic information is missing.");
            } else {
                cloudFuncTask = new CloudFuncTask();
                cloudFuncTask.execute();
            }
        } catch (CloudFuncException e){
            e.printStackTrace();
        }

    }

    /**
     * 将用户传入的执行模块赋到私有变量上，在异步执行时进行调用
     * @param cloudFuncRunnableModule 传入执行模块
     */
    public void setCloudFuncRunnableModule(CloudFuncRunnableModule cloudFuncRunnableModule){
        this.cloudFuncRunnableModule = cloudFuncRunnableModule;
    }

    public interface CloudFuncRunnableModule {
        /**
         *
         */
        void onCreated();

        /**
         *
         * @param result
         */
        void onFinished(String result);
    }

    private class CloudFuncTask extends AsyncTask<String,Void,String> {

        private Invoke invoke;

        public CloudFuncTask(){
            if(endpoint.equals("")){
                invoke = new Invoke(secretId, secretKey, region);
            } else {
                invoke = new Invoke(secretId, secretKey, endpoint, region);
            }
            String pendingParameter = parameters.toJSONString();
            invoke.setFunctionName(functionName);
            invoke.setParameter(pendingParameter);
        }

        @Override
        protected String doInBackground(String... params) {
            String result = invoke.getResult();
            return result;
        }

        @Override
        protected void onPreExecute() {
            if(cloudFuncRunnableModule != null) {
                cloudFuncRunnableModule.onCreated();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if(cloudFuncRunnableModule != null) {
                cloudFuncRunnableModule.onFinished(s);
            }
        }
    }

}
