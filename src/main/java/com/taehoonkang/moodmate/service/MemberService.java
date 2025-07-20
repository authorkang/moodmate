package com.taehoonkang.moodmate.service;


import com.taehoonkang.moodmate.dto.MemberDTO;
import com.taehoonkang.moodmate.entity.MemberEntity;
import com.taehoonkang.moodmate.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
// One of the features of lombok, it automatically generates a constructor for final variables.

public class MemberService {

    private final MemberRepository memberRepository;
    // Constructor injection (DI)

    public void save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        // MemberDTO -> MemberEntity
        memberRepository.save(memberEntity);
        // Call the save method of repository (must pass a memberEntity object, not memberDTO)
    }

    public MemberDTO login(MemberDTO memberDTO) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        // Checks the 1) email and 2) password entered by the member sequentially in the DB
        if (byMemberEmail.isPresent()) {
            MemberEntity memberEntity = byMemberEmail.get();
            // Optional unpacking
            if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity memberEntity: memberEntityList) {
            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
            memberDTOList.add(memberDTO);
        }
        return memberDTOList;
    }

    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    public MemberDTO findByEmail(String email) {
        Optional<MemberEntity> optionalMember = memberRepository.findByMemberEmail(email);
        if (optionalMember.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMember.get());
        } else {
            return null;
        }
    }


}