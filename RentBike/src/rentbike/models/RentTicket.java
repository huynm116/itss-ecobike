/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rentbike.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentTicket {
    private String id;
    private Millisecond startDate;
    private User user;
    private Bike bike;
    private Integer deposit;
    private Integer isReturn;
    
    public Object[] toObject() {
        String startDate = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy").format(new Date(this.startDate.asLong()));
        return new Object[]{id, startDate, bike.getName(), deposit, isReturn};
    }
}
