package com.food.notificationservice.Service;

import com.food.notificationservice.Dto.NotificationDto;

public interface SentNotificationService {

    void sentMail(NotificationDto notificationDto);
}
