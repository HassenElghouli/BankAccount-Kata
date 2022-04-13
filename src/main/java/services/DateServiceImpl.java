package services;

import java.time.Instant;

public class DateServiceImpl implements  DateService{
    @Override
    public Instant getDate() {
        return Instant.now();
    }
}
