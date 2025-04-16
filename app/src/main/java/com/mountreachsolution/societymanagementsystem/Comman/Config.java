package com.mountreachsolution.societymanagementsystem.Comman;

public class Config {

    private static String OnlineAddress = "http://192.168.119.119:80/societymanagementsystem/";
    //users
    public static String url_addusers = OnlineAddress+ "registerUser.php";
    public static String url_login = OnlineAddress+ "loginUser.php";
    public static String url_view_all_resident = OnlineAddress+ "getAllResident.php";
    public static String url_view_all_maintenence_officer = OnlineAddress+ "getAllMaintenenceOfficer.php";
    public static String url_view_all_notice = OnlineAddress+ "getAllNotice.php";
    public static String url_view_all_complaint_feedback = OnlineAddress+ "getAllComplaintFeedback.php";
    public static String url_addResident = OnlineAddress+ "addResident.php";
    public static String url_addMaintenanceOfficer = OnlineAddress+ "addMaintenanceOfficer.php";
    public static String url_addnotice = OnlineAddress+ "addNotice.php";

    //resident
    public static String url_view_all_maintenance = OnlineAddress+ "getAllMaintenence.php";
    public static String url_addFeedback = OnlineAddress+ "addFeedback.php";

    //Maintenance
    public static String url_addMaintenance = OnlineAddress+ "addMaintenance.php";
    public static String url_view_individual_maintenence_officer = OnlineAddress+ "getIndividualMaintenenceOfficer.php";



}
