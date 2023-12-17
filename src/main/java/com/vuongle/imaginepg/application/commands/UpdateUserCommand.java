package com.vuongle.imaginepg.application.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserCommand extends RegisterCommand implements Serializable {

    private List<UpdateFriendshipCommand> friendshipData;
}
