/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

/**
 *
 * @author HuyTuan
 */
public class StringUtils {
    public String convertToPrice(long price) {
        int i = 0;
        String res = "";
        while (price != 0) {
            i++;
            res = price % 10 + res;
            price /= 10;
            if (i % 3 == 0) {
                res = "." + res;
            }
        }
        if (res.charAt(0) == '.') res = res.substring(1, res.length());
        return res;
    }
    
    public String getStringPassword(char[] s){
        String res = "";
        for (int i=0; i<s.length; i++)
            res += s[i];
        return res;
    }
}
