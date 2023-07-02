package com.example.deliveryservice_courseproject;

public class DBConsts {
    public static final String CLIENTS_TABLE = "clients";
    public static final String CLIENTS_ID = "id";
    public static final String CLIENTS_NAME = "name";
    public static final String CLIENTS_NUMBER = "number";
    public static final String CLIENTS_ADDRESS = "address";
    public static final String CLIENTS_LOGIN = "login";

    public static final String CLIENTS_USERID = "user_id";
    //////////////////////////////////////////////////////

    public static final String USERS_TABLE = "users";
    public static final String USERS_LOGIN = "login";
    public static final String USERS_ID = "id";
    public static final String USERS_PASSWORD = "password";

    public static final String USERS_ACCESS = "accesslevel";

    //////////////////////////////////////////////////////

    public static final String PACKAGES_TABLE = "packages";
    public static final String PACKAGES_ID = "id";
    public static final String PACKAGES_TYPEOFDELIVERY= "type_of_delivery";
    public static final String PACKAGES_WEIGHT = "weight";
    public static final String PACKAGES_STATUS = "status";
    public static final String PACKAGES_DATESTART = "date_start";
    public static final String PACKAGES_DATEEND = "date_end";
    public static final String PACKAGES_COURIERID = "courier_id";
    public static final String PACKAGES_SENDERID = "sender_id";
    public static final String PACKAGES_RECIPIENTID = "recipient_id";
    public static final String PACKAGES_SENDCENTERID = "departcenter_id";
    public static final String PACKAGES_RECEIVINGCENTERID = "receivingcenter_id";

    //////////////////////////////////////////////////////

    public static final String COURIERS_TABLE = "couriers";
    public static final String COURIERS_NAME = "name";
    public static final String COURIERS_NUMBER = "number";
    public static final String COURIERS_DELIVERYCENTERID = "delivery_center_id";
    public static final String COURIERS_USERID = "user_id";

}
