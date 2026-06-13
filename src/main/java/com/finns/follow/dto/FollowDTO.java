package com.finns.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowDTO {
    private long user_no;      // 팔로우하는 사용자 번호
    private long to_user_no;   // 팔로우 당하는 사용자 번호
}