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
public class User {
    private String username;
    private String password;
    private String mobile;
    private String email;
    private String address;
    private String cardHolderName;
    private String cardNumber;
    private String experiationDate;
    private String sercurityToken;
    private Integer balance;

    public User(String username) {
        this.username = username;
    }
    
    
    
}
