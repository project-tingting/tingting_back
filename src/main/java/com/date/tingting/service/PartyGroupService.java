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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public void addGuest(User user, UserInvitation userInvitation) {
        com.date.tingting.domain.user.User host = userService.getUser(user.getUsername());

        if (userRepository.findByUserId(userInvitation.getGuest()).isEmpty()) {
            throw new TingTingCommonException("존재하지 않는 유저입니다.");
        }

        if (partyGroupRepository.findByHostAndGuest(host.getUserId(), userInvitation.getGuest()).isPresent()) {
            throw new TingTingCommonException("이미 초대한 유저입니다.");
        }

        if (partyGroupRepository.findByGuest(userInvitation.getGuest()).isPresent()){
            throw new TingTingCommonException("초대할 수 없는 유저입니다.");
        }

        PartyGroup partyGroup = PartyGroup.builder()
                .host(host.getUserId())
                .guest(userInvitation.getGuest())
                .build();
        partyGroupRepository.save(partyGroup);
    }

    public void removeGuest(String host, String guest) {
        PartyGroup partyGroup = partyGroupRepository.findByHostAndGuest(host, guest)
                .orElseThrow(() -> {
                            throw new TingTingCommonException("초대 되지 않은 유저입니다.");
                        }
                );

        partyGroupRepository.delete(partyGroup);
    }

    public UserInvitation getHostList(User user) {
        com.date.tingting.domain.user.User guest = userService.getUser(user.getUsername());

        PartyGroup host = partyGroupRepository.findByGuest(guest.getUserId()).orElseThrow(
                ()-> { throw new TingTingCommonException("초대된 내역이 없습니다."); });

        if ( host.getIsAccepted().equals("1") ) {
            throw new TingTingCommonException("이미 수락한 초대입니다.");
        }

        UserInvitation userInvitation = new UserInvitation();
        userInvitation.setHost(host.getHost());
        userInvitation.setGuest(host.getGuest());

        return userInvitation;
    }

    public void acceptInvitation(User user) {
        com.date.tingting.domain.user.User guest = userService.getUser(user.getUsername());
        PartyGroup host = partyGroupRepository.findByGuest(guest.getUserId()).orElseThrow(
                ()-> { throw new TingTingCommonException("존재하지 않는 유저입니다."); });

        PartyGroup partyGroup = partyGroupRepository.findByHostAndGuest(host.getHost(), guest.getUserId())
                .orElseThrow(() -> {
                            throw new TingTingCommonException("초대 되지 않은 유저입니다.");
                        }
                );
        partyGroup.changeIsAccepted("1");
//        removeGuest(host.getHost(), guest.getUserId());
    }

    public List<PartyUserResponse> getPartyUserList(User user) {
        com.date.tingting.domain.user.User host = userService.getUser(user.getUsername());
        List<PartyUserResponse> partyUserList = new ArrayList<>();

        List<PartyUserInterface> userList = userRepository.getPartyUserList(host.getUserId(), "0").stream()
                .filter(u -> !u.getUserId().equals(host.getUserId()))
                .collect(Collectors.toList());

        userList.forEach(u -> {
            PartyUserResponse partyUser = PartyUserResponse.builder().userId(u.getUserId()).invitationState(u.getInvitationState()).build();
            Optional<PartyGroup> guest = partyGroupRepository.findByHostAndGuest(host.getUserId(), u.getUserId());
            if(guest.isPresent()) {
                partyUser.setInvitationState("-1");

                if("1".equals(guest.get().getIsAccepted())) {
                    partyUser.setInvitationState("9");
                }
            }


            partyUserList.add(partyUser);
        });

        return partyUserList.stream()
                .filter(partyUser -> !"0".equals(partyUser.getInvitationState()))
                .collect(Collectors.toList());
    }
}
