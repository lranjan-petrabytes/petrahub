package com.petrabytes.keyvault;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.CreateSecretRequest;
import com.amazonaws.services.secretsmanager.model.DecryptionFailureException;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.amazonaws.services.secretsmanager.model.InternalServiceErrorException;
import com.amazonaws.services.secretsmanager.model.InvalidParameterException;
import com.amazonaws.services.secretsmanager.model.InvalidRequestException;
import com.amazonaws.services.secretsmanager.model.PutSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.PutSecretValueResult;
import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petrabytes.config.Cluster_Info_view;

public class AWS_Key_Vault {
	// Use this code snippet in your app.
	// If you need more information about configurations or implementing the sample code, visit the AWS docs:
	// https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/java-dg-samples.html#prerequisites
	// Create a Secrets Manager client
	private static String region = "us-east-2";
	private static AWSSecretsManager client  = AWSSecretsManagerClientBuilder.standard()
                                    .withRegion(region)
                                    .build();
    
    // In this sample we only handle the specific exceptions for the 'GetSecretValue' API.
    // See https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
    // We rethrow the exception by default.
		    
	public static String getSecret(String secretName) {
		
		System.setProperty("aws.accessKeyId", "AKIARZVLKHBSU7O7ZZX2");
	    System.setProperty("aws.secretKey", "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");   
	    
	    String secret, decodedBinarySecret;
	    GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
	                    .withSecretId(secretName);
	    GetSecretValueResult getSecretValueResult = null;
	   

	    try {
	        getSecretValueResult = client.getSecretValue(getSecretValueRequest);
	        System.out.println(getSecretValueResult.getSecretString());
	    } catch (DecryptionFailureException e) {
	        // Secrets Manager can't decrypt the protected secret text using the provided KMS key.
	        // Deal with the exception here, and/or rethrow at your discretion.
	        throw e;
	    } catch (InternalServiceErrorException e) {
	        // An error occurred on the server side.
	        // Deal with the exception here, and/or rethrow at your discretion.
	        throw e;
	    } catch (InvalidParameterException e) {
	        // You provided an invalid value for a parameter.
	        // Deal with the exception here, and/or rethrow at your discretion.
	        throw e;
	    } catch (InvalidRequestException e) {
	        // You provided a parameter value that is not valid for the current state of the resource.
	        // Deal with the exception here, and/or rethrow at your discretion.
	        throw e;
	    } catch (ResourceNotFoundException e) {
	        // We can't find the resource that you asked for.
	        // Deal with the exception here, and/or rethrow at your discretion.
	        throw e;
	    }

	    // Decrypts secret using the associated KMS CMK.
	    // Depending on whether the secret is a string or binary, one of these fields will be populated.
	    if (getSecretValueResult.getSecretString() != null) {
	        secret = getSecretValueResult.getSecretString();
	    }
	    else {
	    	secret = "";
	        decodedBinarySecret = new String(Base64.getDecoder().decode(getSecretValueResult.getSecretBinary()).array());
	    }

	    return secret;
	}
	
	public static void updateSecret(String secretName, String secretValue) {
		System.setProperty("aws.accessKeyId", "AKIARZVLKHBSU7O7ZZX2");
	    System.setProperty("aws.secretKey", "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");
	    
	    PutSecretValueRequest putSecretValueRequest = new PutSecretValueRequest()
	    		.withSecretId(secretName).withSecretString(secretValue);
	    
	    try {        
			client.putSecretValue(putSecretValueRequest);
			getSecret(secretName);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static void createNewSecret(String secretName, String secretValue) {

        try {
        	System.setProperty("aws.accessKeyId", "AKIARZVLKHBSU7O7ZZX2");
    	    System.setProperty("aws.secretKey", "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");
            CreateSecretRequest secretRequest = new CreateSecretRequest().withName(secretName).withSecretString(secretValue);
            client.createSecret(secretRequest);

        } catch (Exception e) {
        	e.printStackTrace();
        }
  
    }
	
	public static List<Cluster_Info_view> getExistingConfigsList() {
		List<Cluster_Info_view> existingConfigList = new <Cluster_Info_view>ArrayList();
		String count = AWS_Key_Vault.getSecret("databricks-serverless-config-count");
		for (int i = 1; i <=Integer.parseInt(count); i++) {
			String jsonString = AWS_Key_Vault.getSecret("databricks-serverless-config-" + String.valueOf(i));
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				Cluster_Info_view config = objectMapper.readValue(jsonString, Cluster_Info_view.class);
				existingConfigList.add(config);
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return existingConfigList;
	}
}