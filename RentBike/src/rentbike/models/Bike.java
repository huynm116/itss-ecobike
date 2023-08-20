/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rentbike.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bike {
    private Integer id;
    private String name;
    private Integer value;
    private Integer isRent;
    private Type type;
    private Dock dock;
    private Integer rentingPrice;
    private Integer percentDeposit;
    private String image;
    
    public enum Type{
        STANDARD_BIKE,
        ELECTRIC_BIKE,
        TWIN_BIKE    
    }
    
    public Object[] toObject(int index) {
        return new Object[]{index, name, rentingPrice, value * percentDeposit /100, type.name()};
    }
    
}
