package com.ef.configuration;

public enum Duration {

    HOURLY("hourly"),
    DAILY("daily");

    private final String value;

    Duration(String value) {
        this.value = value;
    }
}
