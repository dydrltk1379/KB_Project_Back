package com.finns.follow.service;

import com.finns.follow.dto.FollowCountDTO;
import com.finns.follow.dto.FollowDTO;
import com.finns.follow.exception.AlreadyFollowingException;
import com.finns.follow.exception.FollowNotFoundException;
import com.finns.follow.mapper.FollowMapper;
import com.finns.member.dto.MemberDTO;
import com.finns.security.account.domain.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowMapper followMapper;

    @Transactional
    public void follow(FollowDTO followDTO) {
        if (isFollowing(followDTO.getUser_no(), followDTO.getTo_user_no())) {
            throw new AlreadyFollowingException("이미 팔로우 중인 사용자입니다.");
        }
        followMapper.insertFollow(followDTO);
    }
    @Transactional
    public void unfollow(FollowDTO followDTO) {
        if (!isFollowing(followDTO.getUser_no(), followDTO.getTo_user_no())) {
            throw new FollowNotFoundException("팔로우 관계가 존재하지 않습니다.");
        }
        followMapper.deleteFollow(followDTO);
    }

    public List<MemberDTO> getFollowingList(long user_no, long auth_no) {
        List<MemberVO> followingList = followMapper.selectFollowingByUserNo(user_no);
        return followingList.stream()
                .map(this::convertToDTO)
                .peek(dto -> dto.setFollowing(isFollowing(auth_no, dto.getUser_no())))
                .collect(Collectors.toList());
    }

    public List<MemberDTO> getFollowerList(long user_no, long auth_no) {
        List<MemberVO> followerList = followMapper.selectFollowerByUserNo(user_no);
        return followerList.stream()
                .map(this::convertToDTO)
                .peek(dto -> dto.setFollowing(isFollowing(auth_no, dto.getUser_no())))
                .collect(Collectors.toList());
    }

    public boolean isFollowing(long user_no, long to_user_no) {
        return followMapper.checkFollowExists(user_no, to_user_no) > 0;
    }

    private MemberDTO convertToDTO(MemberVO vo) {
        return MemberDTO.builder()
                .user_no(vo.getUser_no())
                .username(vo.getUsername())
                .birth(vo.getBirth())
                .mbti_name(vo.getMbti_name())
                .img_url(vo.getImg_url())
                .renew_time(vo.getRenew_time())
                .following(false) // 기본값을 false로 설정
                .build();
    }

    public FollowCountDTO getFollowCounts(long user_no) {
        int followerCount = followMapper.countFollowers(user_no);
        int followingCount = followMapper.countFollowing(user_no);
        return new FollowCountDTO(followerCount, followingCount);
    }


//        @Transactional
//        public void follow(FollowDTO followDTO) {
//            if (isFollowing(followDTO.getUser_no(), followDTO.getTo_user_no())) {
//                throw new AlreadyFollowingException("이미 팔로우 중인 사용자입니다.");
//            }
//            followMapper.insertFollow(followDTO);
//        }
//
//        @Transactional
//        public void unfollow(FollowDTO followDTO) {
//            if (!isFollowing(followDTO.getUser_no(), followDTO.getTo_user_no())) {
//                throw new FollowNotFoundException("팔로우 관계가 존재하지 않습니다.");
//            }
//            followMapper.deleteFollow(followDTO);
//        }
//
//        public List<MemberDTO> getFollowingList(int user_no) {
//            List<MemberDTO> followingList = followMapper.selectFollowingByUserNo(user_no);
//            return followingList.stream()
//                    .peek(dto -> dto.setFollowing(true))
//                    .collect(Collectors.toList());
//        }
//
//        public List<MemberDTO> getFollowerList(int user_no) {
//            List<MemberDTO> followerList = followMapper.selectFollowersByUserNo(user_no);
//            return followerList.stream()
//                    .peek(dto -> dto.setFollowing(isFollowing(user_no, dto.getUser_no())))
//                    .collect(Collectors.toList());
//        }
//
//        private boolean isFollowing(int user_no, int to_user_no) {
//            return followMapper.checkFollowExists(user_no, to_user_no) > 0;
//        }
//    }
}