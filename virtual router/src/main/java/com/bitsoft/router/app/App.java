package com.bitsoft.router.app;

public class App {
	public static void main(String[] args) throws Exception {
		javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		//CommonUtil.BASE_URL = PropertiseReader.readPropertise();
		//VertualRouter window = new VertualRouter();
		VirtualRouter window = new VirtualRouter();
		window.setVisible(true);
	}
}
