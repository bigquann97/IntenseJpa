package me.quann.taesunjpa.thread;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThreadSearchCond {
    private Long mentionedUserId; // 멘션된 유저 id
    private Long channelId;
}
