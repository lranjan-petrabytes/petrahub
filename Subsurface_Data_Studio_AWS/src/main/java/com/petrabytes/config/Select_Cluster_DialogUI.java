package com.petrabytes.config;

import com.petrabytes.databricks.DatabricksClusterPopupInfo;
import com.petrabytes.dialogUI.PetrabyteUI_Dialog;
import com.petrabytes.keyvault.AWS_Key_Vault;
import com.petrabytes.keyvault.PH_KeyVault;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Select_Cluster_DialogUI extends PetrabyteUI_Dialog {
	private VerticalLayout mainLayout = new VerticalLayout();
	private Label messageLabel = new Label();
	private Label messageLabel1 = new Label();
	private Label messageLabel2 = new Label();
	private String cluster_id;
	private String cluster_name;
	private String cluster_status;
	private boolean awsFlag;
	private DatabricksClusterPopupInfo cluster;
	public Select_Cluster_DialogUI(DatabricksClusterPopupInfo cluster, Boolean awsFlag) {
		this.cluster = cluster;
		this.cluster_id = cluster.getCluster();
		this.cluster_name = cluster.getName();
		this.awsFlag = awsFlag;
		setUI();
	}
	
	private void setUI() {
		mainLayout.setWidth("400px");
		mainLayout.setHeight("200px");
		this.setButtonName("OK");
		this.setTitle("Cluster Selection");

		messageLabel.setText("Are you sure you want to select this cluster for all operations?");
		messageLabel1.setText("Cluster Name: \""+ cluster_name + "\"");
		messageLabel2.setText("Cluster ID:   " + "\"" + cluster_id + "\"");
		this.content.add(mainLayout);
		SaveDialogUI();	
	}
	
	public void SelectClusterEvent() {
		// TODO Auto-generated method stub
		
		this.saveButton.addClickListener(event -> {
			if(!awsFlag) {
				PH_KeyVault.update_OR_ADD_Secret("Databricks-Cluster", cluster_id);
				System.out.println(PH_KeyVault.getSecretKey("Databricks-Cluster"));
			} else {
				AWS_Key_Vault.updateSecret("Databricks-Cluster", cluster_id);
				System.out.println(AWS_Key_Vault.getSecret("Databricks-Cluster"));
			}

			cluster_status = cluster.getStatus();

			this.close();	
			
		});
	}


	private void SaveDialogUI() {
		// TODO Auto-generated method stub

		VerticalLayout headerlayout = new VerticalLayout();
		headerlayout.add(messageLabel,messageLabel1,messageLabel2);
		messageLabel.setWidth("100%");
		messageLabel1.setWidth("100%");
		messageLabel2.setWidth("100%");
		// messageLabel.getStyle().set("padding-left", "20px");

		add(headerlayout);

		mainLayout.add(headerlayout);
	}

	public String getCluster_status() {
		return cluster_status;
	}

	public void setCluster_status(String cluster_status) {
		this.cluster_status = cluster_status;
	}
	
	
}
