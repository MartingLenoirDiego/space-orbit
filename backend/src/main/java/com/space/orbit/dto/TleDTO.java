package com.space.orbit.dto;

public record TleDTO(
        int NORAD_CAT_ID,
        String OBJECT_NAME,
        String TLE_LINE1,
        String TLE_LINE2
) {}
