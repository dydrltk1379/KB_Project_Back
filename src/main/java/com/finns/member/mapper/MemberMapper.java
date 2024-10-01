package com.finns.member.mapper;

import com.finns.member.dto.Auth;
import com.finns.member.dto.ChangePasswordDTO;
import com.finns.member.dto.Member;

import java.util.List;

public interface MemberMapper {
    List<Member> selectMemberAll();
    Member selectById(String id);
    int insertMember(Member member);
    int updateMember(Member member);
    int updatePassword(ChangePasswordDTO changePasswordDTO);
    int deleteMember(long mno);
    int insertAuth(Auth auth);
    int deleteAuth(Auth auth);
}
