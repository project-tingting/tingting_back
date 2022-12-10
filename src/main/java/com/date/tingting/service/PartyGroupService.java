package com.date.tingting.service;

import com.date.tingting.domain.partyGroup.PartyGroup;
import com.date.tingting.domain.partyGroup.PartyGroupRepository;
import com.date.tingting.domain.user.UserRepository;
import com.date.tingting.handler.exception.TingTingCommonException;
import com.date.tingting.web.requestDto.UserInvitation;
import com.date.tingting.web.responseDto.PartyUserInterface;
import com.date.tingting.web.responseDto.PartyUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class PartyGroupService {
    @Autowired
    private final PartyGroupRepository partyGroupRepository;
    @Autowired
    private final UserService userService;
    @Autowired
    private final UserRepository userRepository;

    public void addGuest(UserInvitation userInvitation) {
        if (partyGroupRepository.findByHostAndGuest(userInvitation.getHost(), userInvitation.getGuest()).isPresent()) {
            throw new TingTingCommonException("이미 초대한 유저입니다.");
        }

        PartyGroup partyGroup = PartyGroup.builder()
                .host(userInvitation.getHost())
                .guest(userInvitation.getGuest())
                .build();
        partyGroupRepository.save(partyGroup);
    }

    public void removeGuest(UserInvitation userInvitation) {
        PartyGroup partyGroup = partyGroupRepository.findByHostAndGuest(userInvitation.getHost(), userInvitation.getGuest())
                .orElseThrow(() -> {
                            throw new TingTingCommonException("초대 되지 않은 유저입니다.");
                        }
                );

        partyGroupRepository.delete(partyGroup);
    }

    public void acceptInvitation(UserInvitation userInvitation) {
        PartyGroup partyGroup = partyGroupRepository.findByHostAndGuest(userInvitation.getHost(), userInvitation.getGuest())
                .orElseThrow(() -> {
                            throw new TingTingCommonException("초대 되지 않은 유저입니다.");
                        }
                );
        partyGroup.changeIsAccepted("1");
    }

    public List<PartyUserInterface> getPartyUserList(User user) {
        com.date.tingting.domain.user.User host = userService.getUser(user.getUsername());

        return userRepository.getPartyUserList(host.getUserId(), "1");
    }
}
