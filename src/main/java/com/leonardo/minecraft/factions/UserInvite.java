package com.leonardo.minecraft.factions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInvite {

    private static int counter = 0;
    private int id = getCounter();
    private String username;
    private String sender;
    private Long factionId;

    private static int getCounter() {
        return counter += 1;
    }

    public void accept() {

    }

    public void refuse() {

    }

}
