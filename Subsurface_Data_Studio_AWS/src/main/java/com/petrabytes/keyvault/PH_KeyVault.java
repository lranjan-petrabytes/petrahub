package com.petrabytes.keyvault;

import java.io.Console;
import java.time.OffsetDateTime;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.identity.DefaultAzureCredentialBuilder;

import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import com.azure.security.keyvault.secrets.models.SecretProperties;

public class PH_KeyVault {
	private static String kvUri = "https://sky-key-vaults.vault.azure.net";

	private static ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
			.clientId("ec5bc673-32fb-42b1-bcc3-498fafc39099").clientSecret("7-e5gp3QOESz.Z_4gOJ-Gc1_ozZWmfnRv1")
			.tenantId("ab39c0bb-b1ad-4ce1-961e-3d9f90b738f6").build();

	/**
	 * get secret value from azure key vault by using key
	 * @param secret key
	 * @return secret Value
	 */
	public static String getSecretKey(String secretName) {
		SecretClient secretClient = new SecretClientBuilder().vaultUrl(kvUri).credential(clientSecretCredential)
				.buildClient();


		KeyVaultSecret retrievedSecret = secretClient.getSecret(secretName);

		return retrievedSecret.getValue();
	}

	/**
	 * update or add secrete key and value to azure key vault
	 * @param secretKey
	 * @param SecrtValue
	 */
	public static void update_OR_ADD_Secret(String secretKey,String SecrtValue) {
		SecretClient secretClient = new SecretClientBuilder().vaultUrl(kvUri).credential(clientSecretCredential)
				.buildClient();
		secretClient.setSecret(secretKey,SecrtValue);
		
	}

	
//	public static void main(String[] args) {
//		update_OR_ADD_Secret("Databricks-Token1", "dapi4e5313c9a81011768f44ae356b5675e00");
//		System.out.println(getSecretKey("Databricks-Token1"));
//	}
}
