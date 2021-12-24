package com.petrabytes.dashboard;

import java.sql.SQLException;
import java.util.List;

import com.petrabytes.views.MainLayout;
import com.petrabytes.views.View_Update;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.NativeButton;

public class Dashboard_Update {

	public static void updateTopLayout() {
		Dashboard_View mainUI = getInstance();
		if (mainUI != null) {
//				mainUI.updateTopBarLayout();
		}
	}
//
//		public static void updateloadWellTopLayout() {
//			Dashboard_View mainUI = getInstance();
//			if (mainUI != null) {
//				mainUI.updateLoadWellTopBarLayout();
//			}
//		}
//
//		public static void updateStatus(String status) {
//			Dashboard_View mainUI = getInstance();
//			if (mainUI != null) {
//				mainUI.setStatus(status);
//			}
//		}
//
//		public static void enableTopLayout(boolean flag) {
//			Dashboard_View mainUI = getInstance();
//			if (mainUI != null) {
//				mainUI.enableTopBarLayout(flag);
//			}
//		}
//
//		public static void updateSideLayout() {
//			Dashboard_View mainUI = getInstance();
//			if (mainUI != null) {
//				mainUI.updateSideLayout();
//			}
//		}
//
//		public static void updateSideLayout(List<NativeButton> navButtons) {
//			Dashboard_View mainUI = getInstance();
//			if (mainUI != null) {
//				mainUI.updateSideBar(navButtons);
//			}
//		}
//
//		
//		
//
//		public static void setSideBarOpen(boolean flag) {
//			Dashboard_View mainUI = getInstance();
//			if (mainUI != null) {
//				mainUI.setSideBarOpen(flag);
//			}
//		}
//
//		public static void updateheaderLayot(boolean flag) {
//			Dashboard_View mainUI = getInstance();
//			if (mainUI != null) {
//				mainUI.setToolBarVissible(flag);
//			}
//		}
//
//		public static void updateMainHeaderLayot(boolean flag) {
//			Dashboard_View mainUI = getInstance();
//			if (mainUI != null) {
//				mainUI.setMainHeaderVissible(flag);
//
//			}
//		}

	public static void updateCenter(String title) throws SQLException {
		MainLayout mainLayout = getAppLayoutInstance();
		if (mainLayout != null) {
			Component component = View_Update.getPresentView(title);
			mainLayout.updateView(component);
		}
	}

	public static void removeCenter() {
		MainLayout mainLayout = getAppLayoutInstance();
		if (mainLayout != null) {

			mainLayout.removeview();
		}
	}

	/**
	 * Returns the current AppLayout
	 * 
	 * @return
	 */
	public static Dashboard_View getInstance() {
		return (Dashboard_View) UI.getCurrent().getChildren()
				.filter(component -> component.getClass() == Dashboard_View.class).findFirst().orElse(null);
	}

	/**
	 * Returns the current AppLayout
	 * 
	 * @return
	 */
	public static MainLayout getAppLayoutInstance() {
		return (MainLayout) UI.getCurrent().getChildren().filter(component -> component.getClass() == MainLayout.class)
				.findFirst().orElse(null);
	}

}
