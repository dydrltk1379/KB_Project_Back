package com.finns.follow.controller;

import com.finns.follow.dto.FollowCountDTO;
import com.finns.follow.dto.FollowDTO;
import com.finns.follow.exception.AlreadyFollowingException;
import com.finns.follow.exception.FollowNotFoundException;
import com.finns.follow.service.FollowService;
import com.finns.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // 클라이언트의 도메인을 허용
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follow/{user_no}/{to_user_no}")
    public ResponseEntity<String> follow(@PathVariable Long user_no, @PathVariable Long to_user_no) {
        FollowDTO followDTO = new FollowDTO(user_no, to_user_no);
        try {
            followService.follow(followDTO);
            return ResponseEntity.ok("Followed successfully");
        } catch (AlreadyFollowingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/unfollow/{user_no}/{to_user_no}")
    public ResponseEntity<String> unfollow(@PathVariable Long user_no, @PathVariable Long to_user_no) {
        FollowDTO followDTO = new FollowDTO(user_no, to_user_no);
        try {
            followService.unfollow(followDTO);
            return ResponseEntity.ok("Unfollowed successfully");
        } catch (FollowNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/users/{user_no}/follower/{auth_no}")
    public ResponseEntity<List<MemberDTO>> getFollowerList(@PathVariable Long user_no, @PathVariable Long auth_no) {
        List<MemberDTO> followerList = followService.getFollowerList(user_no, auth_no);
        return ResponseEntity.ok(followerList);
    }

    @GetMapping("/users/{user_no}/following/{auth_no}")
    public ResponseEntity<List<MemberDTO>> getFollowingList(@PathVariable Long user_no, @PathVariable Long auth_no) {
        List<MemberDTO> followingList = followService.getFollowingList(user_no, auth_no);
        return ResponseEntity.ok(followingList);
    }

    @GetMapping("/users/{user_no}/followCounts")
    public ResponseEntity<FollowCountDTO> getFollowCounts(@PathVariable Long user_no) {
        FollowCountDTO counts = followService.getFollowCounts(user_no);
        return ResponseEntity.ok(counts);
    }

    @GetMapping("/followingcheck")
    public ResponseEntity<Boolean> checkFollowing(
            @RequestParam long user_no,
            @RequestParam long to_user_no) {
        boolean Following = followService.isFollowing(user_no, to_user_no);
        return ResponseEntity.ok(Following);
    }





}