package org.anuva04.Models;

import org.anuva04.Enums.Denomination;

public class Currency {
    public static int getValue(Denomination denomination) {
        switch (denomination) {
            case FIFTY:
                return 50;
            case HUNDRED:
                return 100;
            case FIVE_HUNDRED:
                return 500;
            case THOUSAND:
                return 1000;
            case TWO_THOUSAND:
                return 2000;
            default:
                throw new IllegalArgumentException("Denomination not supported!");
        }
    }
}
