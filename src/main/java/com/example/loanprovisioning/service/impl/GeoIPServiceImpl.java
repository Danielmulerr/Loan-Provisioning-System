package com.example.loanprovisioning.service.impl;

import com.example.loanprovisioning.dto.IPInfo;
import com.example.loanprovisioning.service.GeoIPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.example.loanprovisioning.config.AppConstants.LOG_PREFIX;

@Service
@Slf4j
public class GeoIPServiceImpl implements GeoIPService {
    public static final String LOCALHOST = "127.0.0.1";
    private static final String IP_TO_LOCATION = "https://api.ip2location.io/";
    private static final String API_KEY = "09C206D75863EC2A053F440018E919C1";
    private final RestTemplate restTemplate;

    public GeoIPServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public IPInfo getLocationFromIp(String ipAddress) {
        if (ipAddress.equals(LOCALHOST))
            log.info(LOG_PREFIX, "Skipping location api call for localhost api", ipAddress);
        try {
            String url = IP_TO_LOCATION + "?key=" + API_KEY + "&ip=" + ipAddress;
            return restTemplate.getForObject(url, IPInfo.class);
        } catch (Exception e) {
            log.error("Failed to get geo location data for ip: {}", ipAddress, e);
            return null;
        }
    }
}
