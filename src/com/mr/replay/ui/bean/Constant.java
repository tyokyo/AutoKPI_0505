package com.mr.replay.ui.bean;

public class Constant {

	public static String mAKey = "/sys/devices/platform/mt6320-battery/FG_Battery_CurrentConsumption";
	public static String mA848Key = "/sys/devices/platform/battery/FG_Battery_CurrentConsumption";
	public static String mvKey = "/sys/class/power_supply/battery/batt_vol";
	public static String tempKey = "/sys/class/power_supply/battery/batt_temp";
	public static String powerKey = "/sys/class/power_supply/battery/capacity";
	public static String APATH = "/mnt/sdcard/CktPowerMonitor/battery.txt";
	public static String compileJarname = "AUT003_UIAutomator.jar";
	
	public static String defaultxml = "C:/MKTool/comsumption/default.xml";
	//public static String writexml = "src/app/resources/current.xml";
	public static String writexml_test = "E:\\AutoKPI\\config\\testxml.xml";
	public static String eighttxt = "C:/MKTool/comsumption/eight.txt";
	
	public static String appmk = "C:/MKTool/comsumption/";
	//public static String testdevname = "HIKe 828";
	public static String cmdSWversion = "shell getprop ro.mediatek.version.release";
}
