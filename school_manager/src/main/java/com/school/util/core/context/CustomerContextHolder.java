package com.school.util.core.context;

public abstract class CustomerContextHolder {
	 
    public final static String DATA_SOURCE_one = "dataSource";
    public final static String DATA_SOURCE_two = "dataSource2";
    
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
    
    public static void setCustomerType(String customerType) {  
        contextHolder.set(customerType);  
    }  
      
    public static String getCustomerType() {  
        return contextHolder.get();  
    }  
      
    public static void clearCustomerType() {  
        contextHolder.remove();  
    }  
}