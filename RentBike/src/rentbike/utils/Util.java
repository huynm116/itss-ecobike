/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rentbike.utils;

import rentbike.models.Millisecond;

public class Util {
    public static int calcMoney(Millisecond startDate, Millisecond endDate, int moneyPerMinute){
        long time = endDate.asLong() - startDate.asLong();
        
        if(milisToMinute(time) >= 30){
            return (milisToMinute(time) - 10) * moneyPerMinute;
        }
        
        return 0;
    }
    
    public static int milisToMinute(long milis){
        return (int) milis/(1000*60);
    }
    
}
