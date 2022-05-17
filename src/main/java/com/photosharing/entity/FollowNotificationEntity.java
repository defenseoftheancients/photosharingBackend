package com.photosharing.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@DiscriminatorValue("FOLLOW_A_USER")
@Table(name = "follownotification")
@OnDelete(action = OnDeleteAction.CASCADE)
public class FollowNotificationEntity extends NotificationEntity {

}
