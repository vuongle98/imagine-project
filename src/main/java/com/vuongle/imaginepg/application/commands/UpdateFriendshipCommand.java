package com.vuongle.imaginepg.application.commands;

import com.vuongle.imaginepg.domain.constants.FriendStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFriendshipCommand implements Serializable {

    private UUID friendId;

    private FriendStatus status;
}
