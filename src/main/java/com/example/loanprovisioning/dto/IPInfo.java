package com.example.loanprovisioning.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record IPInfo(
        @JsonProperty("ip") String ip,
        @JsonProperty("country_code") String countryCode,
        @JsonProperty("country_name") String countryName,
        @JsonProperty("region_name") String regionName,
        @JsonProperty("city_name") String cityName,
        @JsonProperty("latitude") double latitude,
        @JsonProperty("longitude") double longitude,
        @JsonProperty("zip_code") String zipCode,
        @JsonProperty("time_zone") String timeZone,
        @JsonProperty("asn") String asn,
        @JsonProperty("as") String asName,
        @JsonProperty("is_proxy") boolean isProxy
) {
}