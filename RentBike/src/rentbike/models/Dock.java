/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rentbike.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dock {
    private Integer id;
    private String name;
    private String address;
    private Integer slotMax;
    
    public Object[] toObject(int index, long count) {
        return new Object[]{index, name, address, count, slotMax - count};
    }
}
