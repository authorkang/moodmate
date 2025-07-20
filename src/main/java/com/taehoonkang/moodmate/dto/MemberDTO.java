package com.taehoonkang.moodmate.dto;


import com.taehoonkang.moodmate.entity.MemberEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberDTO {
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String role = "USER";
    private String openaiApikey;
    private Integer age;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity){
        // MemberEntity -> MemberDTO 매서드
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setRole(memberEntity.getRole() != null ? memberEntity.getRole() : "USER");
        memberDTO.setOpenaiApikey(memberEntity.getOpenaiApikey());
        memberDTO.setAge(memberEntity.getAge());
        return memberDTO;
    }
}
