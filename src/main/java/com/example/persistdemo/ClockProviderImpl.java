package com.example.persistdemo;

import org.springframework.stereotype.Service;

import java.time.Clock;

@Service
public class ClockProviderImpl implements ClockProvider {
    private Clock clock = Clock.systemUTC();

    @Override
    public Clock getClock() {
        return clock;
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }
}
