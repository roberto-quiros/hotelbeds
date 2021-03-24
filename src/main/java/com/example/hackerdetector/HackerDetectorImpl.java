package com.example.hackerdetector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.config.HackingProperties;
import com.example.model.Login;
import com.example.model.Login.LoginStatus;

@Component
public class HackerDetectorImpl implements HackerDetector {
	
	@Autowired
	private HackingProperties hackingProperties;
	

	public String parseLine(String line) {

		StringTokenizer stringTokenizer = null;
		ArrayList<Login> loginList = new ArrayList<Login>();
		
		try {
			stringTokenizer = validateLine(line);
		} catch (Exception e) {
			
			e.printStackTrace();
		}

        String ipAddress = stringTokenizer.nextToken();
        
        loginList = getList(ipAddress);
        
		boolean a = detectionHack(loginList);
		
		if(a) {
			return ipAddress;
		} else {
			return null;
		}
		
		
	}
	
	
	private StringTokenizer validateLine(String line) throws Exception {
		
        if (line == null) {
            throw new Exception("Line is null!");
        }

        StringTokenizer stringTokenizer = new StringTokenizer(line, ",");

        if (stringTokenizer.countTokens() != 4) {
            throw new Exception("Error line format: " + line);
        }

        return stringTokenizer;
    }
	

    public ArrayList<Login> getList(String ip) {
    	
    	
    	ArrayList<Login> listProduct = new ArrayList<Login>();
    	//Map<String, ArrayList<Login>> mapLogin = new HashMap<String, ArrayList<Login>>();
    	
    	boolean existIp = false;

        
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\Hotelbeds2\\demo\\src\\main\\resources\\activity.txt"))) {

            String line;
            while ((line = br.readLine()) != null) {
            	
            	Login myLogin = new Login();
            	
            	String[] values = line.split(",");
            	
            	if(ip.equals(String.valueOf(values[0]))) {
            		
            		existIp = true;
            		
            		myLogin.setIp(String.valueOf(values[0]));
            		myLogin.setDate(String.valueOf(values[1]));
            		myLogin.setStatus(parseLoginStatus(String.valueOf(values[2])));
            		myLogin.setUser(String.valueOf(values[3]));
            		
            		listProduct.add(myLogin);
            	}

                
            }
        } catch (Exception e) {

    		e.printStackTrace();
    	}
    	
        if(existIp) {
        	
        	return listProduct;
        	
        } else {
            
    		return null;
    		
        }
        
   
    	
    }

    public boolean detectionHack(ArrayList<Login> LoginList){
    	
    	boolean hack = false;

    	LocalDateTime dateLocal;
    	
    	int attempt = 0;
    	
   
    	Login loginLast = LoginList.get(LoginList.size()-1);
    	
        for(Login Login: LoginList){
        	if(Login.getStatus().name().equals("SIGNIN_FAILURE")){
        		dateLocal = parseEpoch(Login.getDate());
        		if(compareDate(parseEpoch(loginLast.getDate()), parseEpoch(Login.getDate()), 5)) {
        			attempt++;
        		}
        	}
        }
        
        if(attempt >= 5) {
        	hack = true;
        }
    	
    	return hack;
    	
    }
    
    private static LoginStatus parseLoginStatus(String statusString) throws Exception {

        if (LoginStatus.SIGNIN_FAILURE.name().equals(statusString)) {
            return LoginStatus.SIGNIN_FAILURE;
        } else if (LoginStatus.SIGNIN_SUCCESS.name().equals(statusString)) {
            return LoginStatus.SIGNIN_SUCCESS;
        } else {
            throw new Exception("Invalid login action: " + statusString);
        }
    }
    
    private static LocalDateTime parseEpoch(String epochInSeconds) {

        try {
        	
            long epochInMillis = Long.valueOf(epochInSeconds) * 1000;
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochInMillis), ZoneId.systemDefault());
        
        } catch (NumberFormatException e) {
    			
        	e.printStackTrace();
			return null;
			
        }
		
    }
    
    private static boolean compareDate(LocalDateTime lastDate, LocalDateTime beforeDate, int min) {
    	
    	if(beforeDate.plusMinutes(min).isAfter(lastDate)) {
    		return true;
    	} else {
    		return false;
    	}
    	
    	
    }

}
