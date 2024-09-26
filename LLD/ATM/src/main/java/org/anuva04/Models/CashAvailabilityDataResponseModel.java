package org.anuva04.Models;

public class CashAvailabilityDataResponseModel {
    public boolean isCashAvailable;
    public CashReserve cashReserve;

    // Response returned by getCashAvailability method Cash dispensing strategy
    public CashAvailabilityDataResponseModel(boolean isCashAvailable, CashReserve cashReserve) {
        this.isCashAvailable = isCashAvailable;
        this.cashReserve = cashReserve;
    }
}
