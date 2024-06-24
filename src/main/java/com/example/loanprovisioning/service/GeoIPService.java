package com.example.loanprovisioning.service;

import com.example.loanprovisioning.dto.IPInfo;

public interface GeoIPService {
    IPInfo getLocationFromIp(String ipAddress);
}
