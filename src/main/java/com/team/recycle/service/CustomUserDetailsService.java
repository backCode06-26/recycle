package com.team.recycle.service;
import com.team.recycle.domain.Member;
import com.team.recycle.domain.dto.CustomUserDetails;
import com.team.recycle.repository.DataMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final DataMemberRepository memberRepository;

    @Autowired
    public CustomUserDetailsService(DataMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(email);

        if (member != null) {

            return new CustomUserDetails(member);
        }

        return null;
    }
}