package com.example.yeecc.awss3;


import com.alibaba.fastjson.JSONObject;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification;

public class LambdaApplication implements RequestHandler<S3Event,String> {
    @Override
    public String handleRequest(S3Event s3Event, Context context) {
        for (S3EventNotification.S3EventNotificationRecord record : s3Event.getRecords()) {
            String s3Key = record.getS3().getObject().getKey();
            String s3Bucket = record.getS3().getBucket().getName();

            JSONObject targetJson = new JSONObject();
            targetJson.put("s3Bucket",s3Bucket);
            targetJson.put("s3Key",s3Key);
            String targetJsonStr = targetJson.toJSONString();

            try {
                aws.lambda.start.HttpClient.excute(targetJsonStr);
            } catch (Exception e) {
                e.printStackTrace();
                return  "false";
            }
        }
        return  "success";
    }



}
