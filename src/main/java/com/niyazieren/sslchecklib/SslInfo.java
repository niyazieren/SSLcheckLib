package com.niyazieren.sslchecklib;

import java.util.Date;

public class SslInfo {
    private final boolean valid;
    private final Date startDate;
    private final Date endDate;

    public SslInfo(boolean valid, Date startDate, Date endDate) {
        this.valid = valid;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isValid() {
        return valid;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
