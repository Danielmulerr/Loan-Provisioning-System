package com.example.loanprovisioning.service;


import com.example.loanprovisioning.dto.NotificationDto;

public interface NotificationService {

    boolean sendNotification(NotificationDto emailDto);
}
