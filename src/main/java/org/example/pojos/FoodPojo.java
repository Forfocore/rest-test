package org.example.pojos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FoodPojo {

    private String name;

    private String type;

    private boolean exotic;

    public boolean getExotic() {
        return this.exotic;
    }
}
