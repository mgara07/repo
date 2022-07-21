package tn.esprit.softib.utility;

import com.itextpdf.text.Font;

public class SystemDeclarations {
	
	//Credit
    public static final String CREDIT_NOT_POSSIBLE= "IMPOSSIBLE CREDIT APPLICATION";
    public static final String CREDIT_INSURANCE_NULL= "INSURANCE IS MISSING";
    public static final String CREDIT_NOT_ENOUGH_SALARY= "SALARY NOT ENOUGH";
    public static final String CREDIT_CONSUMPTION_AMOUNT_EXCEEDED= "MAXIMUM AMOUNT FOR CONSUMPTION CREDIT EXCEEDED";
    public static final String CREDIT_CONSUMPTION_CREDIT_TERM_EXCEEDED= "MAXIMUM CREDIT TERM FOR CONSUMPTION CREDIT EXCEEDED";
    public static final String CREDIT_CAR_CREDIT_TERM_EXCEEDED= "MAXIMUM CREDIT TERM FOR CAR CREDIT EXCEEDED";
    public static final String CREDIT_HOME_CREDIT_TERM_EXCEEDED= "MAXIMUM CREDIT TERM FOR HOME CREDIT EXCEEDED";
    
    // Credit Variable
    public static final Double CREDIT_INTEREST = 0.12;
    public static final Double INSURANCE_PERCENTAGE = 0.12;
    public static final Double CREDIT_FEES = 250d;
    
    // Patterns
    public static final String DATE_FORMAT = "MM/dd/yyyy";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'hh:mm";
    
  //PDF File config
    public static Font COURIER = new Font(Font.FontFamily.COURIER, 20, Font.BOLD);
    public static Font COURIER_SMALL = new Font(Font.FontFamily.COURIER, 16, Font.BOLD);
    public static Font COURIER_SMALL_FOOTER = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);

}
