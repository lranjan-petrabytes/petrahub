package com.petrabytes.dialogUI;

import java.util.Properties;



public class PetrahubNotification_Utilities {
	private static PetrahubNotification_Utilities _singleton = null;
	
    private Properties notificationGlobal = new Properties();
	public static PetrahubNotification_Utilities getInstance() {
		if (_singleton == null) {
			_singleton = new PetrahubNotification_Utilities();
		}
		return _singleton;
	}
	
	
	private PetrahubNotification_Utilities() {
		try {
		
			notificationGlobal.load(this.getClass().getResourceAsStream("/petrahub_notifications.properties"));
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String userloginNotification() {
		return notificationGlobal.getProperty("user_login.key1","abcd");
	}
	
	public String  createProject() {
		return notificationGlobal.getProperty("toptoolProjectUI.key7");
	}
	public String selectBasin() {
		return notificationGlobal.getProperty("basin_view.key1");
	}
	
	public String queryBasin() {
		return notificationGlobal.getProperty("basin_view.key2");
	}
	
	public String createBasin() {
		return notificationGlobal.getProperty("create_edit_basin.key1");
	}
	
	public String basinnotcreated() {
		return notificationGlobal.getProperty("create_edit_basin.key2");
	}
	
	public String basinAlreadyExist() {
		return notificationGlobal.getProperty("create_edit_basin.key3");
	}
	
	public String editBasin() {
		return notificationGlobal.getProperty("create_edit_basin.key4");
	}
	public String wellListSelection() {
		return notificationGlobal.getProperty("well_view.key1");
	}
	public String selectBasinInWellView() {
		return notificationGlobal.getProperty("well_view.key2");
	}
	public String queringWellListInWellView() {
		return notificationGlobal.getProperty("well_view.key3");
	}
	public String queringBasinListInWellView() {
		return notificationGlobal.getProperty("well_view.key4");
	}
	public String addingWellInDaltalakeInWellInfoView() {
		return notificationGlobal.getProperty("well_view.key5");
	}
	public String createWellInWellInfoView() {
		return notificationGlobal.getProperty("well_view.key6");
	}
	public String wellnameAlreadyExistInWellInfoView() {
		return notificationGlobal.getProperty("well_view.key7");
	}
	public String editingWellInDeltaLakeInWellInfoView() {
		return notificationGlobal.getProperty("well_view.key8");
	}
	public String plsFillWellnameInWellInfoView() {
		return notificationGlobal.getProperty("well_view.key9");
	}
	public String plsSelectBasinInWellInfoView() {
		return notificationGlobal.getProperty("well_view.key10");
	}
	public String deletingWellInDeltalakeInWellDelView() {
		return notificationGlobal.getProperty("well_view.key11");
	}
	public String queringCompanyInfoInWellMapsView() {
		return notificationGlobal.getProperty("well_view.key12");
	}
	public String queryDoneInWellMapsView() {
		return notificationGlobal.getProperty("well_view.key13");
	}
	public String queryErrorInWellMapsView() {
		return notificationGlobal.getProperty("well_view.key14");
	}
	public String queryStateInfoInWellMapsView() {
		return notificationGlobal.getProperty("well_view.key15");
	}
//	public String queryDoneInWellMapsView() {
//		return notificationGlobal.getProperty("well_view.key16");
//	}
//	public String queryErrorInWellMapsView() {
//		return notificationGlobal.getProperty("well_view.key17");
//	}
	public String queryStatusInfoInWellMapsView() {
		return notificationGlobal.getProperty("well_view.key18");
	}
//	public String queryDoneInWellMapsView() {
//		return notificationGlobal.getProperty("well_view.key19");
//	}
//	public String queryErrorInWellMapsView() {
//		return notificationGlobal.getProperty("well_view.key20");
//	}
	public String queryWelltypeInfolistInWellMapsView() {
		return notificationGlobal.getProperty("well_view.key21");
	}
//	public String queryDoneInWellMapsView() {
//		return notificationGlobal.getProperty("well_view.key22");
//	}
//	public String queryErrorInWellMapsView() {
//		return notificationGlobal.getProperty("well_view.key23");
//	}
	
	public String selectWellinWellboreView() {
		return notificationGlobal.getProperty("wellbore_view.key2");
	}
	
	public String selectBasinWellboreView() {
		return notificationGlobal.getProperty("wellbore_view.key3");
	}
	
	public String selectWellboreView() {
		return notificationGlobal.getProperty("wellbore_view.key5");
	}
	
	public String queryWellList() {
		return notificationGlobal.getProperty("wellbore_view.key8");
	}
	
	public String queryWellboreList() {
		return notificationGlobal.getProperty("wellbore_view.key9");
	}
	public String addingWellbore() {
		return notificationGlobal.getProperty("wellbore_view.key10");
	}
	
	public String editingWellbore() {
		return notificationGlobal.getProperty("wellbore_view.key13");
	}
	public String deletingWellbore() {
		return notificationGlobal.getProperty("wellbore_view.key15");
	}
	
	public String wellboreNameAlreadyExist() {
		return notificationGlobal.getProperty("wellbore_view.key12");
	}
	
	public String fillWellboreName() {
		return notificationGlobal.getProperty("wellbore_view.key14");
	}
	
	public String selectArowFromgrid() {
		return notificationGlobal.getProperty("wellbore_view.key16");
	}
	
	
	public String pressCalculateButton() {
		return notificationGlobal.getProperty("wellbore_view.key17");
	}
	
	public String fillCasinInformation() {
		return notificationGlobal.getProperty("wellbore_view.key19");
	}
	
	public String fetchingWellboreData() {
		return notificationGlobal.getProperty("wellLogsView.key1");
	}
	
	public String queryLogData() {
		return notificationGlobal.getProperty("wellLogsView.key2");
	}
	
	public String noLogDataFound() {
		return notificationGlobal.getProperty("wellLogsView.key3");
	}
	
	public String plottingLogData() {
		return notificationGlobal.getProperty("wellLogsView.key4");
	}
	public String selectUnit() {
		return notificationGlobal.getProperty("wellLogsView.key7");
	}
	
	public String selectUnitCategory() {
		return notificationGlobal.getProperty("wellLogsView.key8");
	}
	
	public String logDataQC() {
		return notificationGlobal.getProperty("wellLogsView.key11");
	}
	
	public String populatingWells() {
		return notificationGlobal.getProperty("wellLogsView.key12");
	}
	
	public String populatingWellbores() {
		return notificationGlobal.getProperty("wellLogsView.key13");
	}
	
	public String populatingLogDataQC() {
		return notificationGlobal.getProperty("wellLogsView.key14");
	}
	

	public String dtsdataAndImage() {
		return notificationGlobal.getProperty("dtsview.key2");
	}
	
	public String populatingBasins() {
		return notificationGlobal.getProperty("dtsview.key3");
	}
	
	public String populatingStarttime() {
		return notificationGlobal.getProperty("dtsview.key6");
	}
	
	
	
	public String siesmicHeader() {
		return notificationGlobal.getProperty("seismicView.key2");
	}
	
	public String siemictrace() {
		return notificationGlobal.getProperty("seismicView.key3");
	}
	
	public String drillingReport() {
		return notificationGlobal.getProperty("drillreports.key2");
	}
	public String coreDataView() {
		return notificationGlobal.getProperty("drillreports.key2");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

