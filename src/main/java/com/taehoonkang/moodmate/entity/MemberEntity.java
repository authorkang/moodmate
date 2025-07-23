package com.taehoonkang.moodmate.entity;


import com.taehoonkang.moodmate.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity

@Setter
@Getter
@Table(name = "member_table")
public class MemberEntity {
    @Id // Specifies the primary key column
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;
    @Column(unique = true) // Prevents duplicate email entries
    private String memberEmail;
    @Column
    private String memberPassword;
    @Column
    private String memberName;
    @Column(nullable = false) // Prevents null values in the 'role' field
    private String role = "USER";
    @Column
    private String openaiApikey;
    @Column
    private Integer age;

    public static MemberEntity toMemberEntity(MemberDTO memberDTO){
        // Converts MemberDTO to MemberEntity (for insert operation)
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setRole(memberDTO.getRole() != null ? memberDTO.getRole() : "USER");
        memberEntity.setOpenaiApikey(memberDTO.getOpenaiApikey());
        memberEntity.setAge(memberDTO.getAge());
        return memberEntity;
    }

    public static MemberEntity toUpdateMemberEntity(MemberDTO memberDTO) {
        // Converts MemberDTO to MemberEntity (for update operation)
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setRole(memberDTO.getRole() != null ? memberDTO.getRole() : "USER");
        memberEntity.setOpenaiApikey(memberDTO.getOpenaiApikey());
        memberEntity.setAge(memberDTO.getAge());
        return memberEntity;
    }

}
