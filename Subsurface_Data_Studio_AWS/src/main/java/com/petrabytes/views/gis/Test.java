package com.petrabytes.views.gis;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String st = "POLYGON ((-87.3700179989999697 33.0145720000000438))";
		st = st.replace("((", ":").replace("))", "");
		String[] sts = st.split(":");
		System.out.println(sts[0]);
		String stp = sts[1].trim();
		String[] stps = stp.split(" ");
		System.out.println(Double.valueOf(stps[0].trim())+" - "+Double.valueOf(stps[1].trim()));
	}

}
