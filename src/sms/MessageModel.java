package sms;

public class MessageModel {

    public static final int GET_PRODUCT = 100;
    public static final int PUT_PRODUCT = 101;
    public static final int GET_ALL_PRODUCTS = 102;
    public static final int UPDATE_PRODUCT = 103;

    public static final int GET_PURCHASE = 200;
    public static final int PUT_PURCHASE = 201;
    public static final int GET_ALL_PURCHASES = 202;

    public static final int GET_CUSTOMER = 300;
    public static final int PUT_CUSTOMER = 301;
    public static final int GET_ALL_CUSTOMERS = 302;
    public static final int UPDATE_CUSTOMER = 303;

    public static final int GENERATE_PURCHASE_ID = 400;
    public static final int PUT_PURCHASE_ID = 401;

    public int code;
    public String data;

    public MessageModel() {

    }

    public MessageModel(int code, String data) {
        this.code = code;
        this.data = data;
    }
}
