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

        // 1. 초대할 유저 존재여부 체크
        if (userRepository.findByUserId(userInvitation.getGuest()).isEmpty()) {
            throw new TingTingCommonException("존재하지 않는 유저입니다.");
        }

        // 2. partyGroup TB에 host-guest 존재여부 체크
        if (partyGroupRepository.findByHostAndGuest(host.getUserId(), userInvitation.getGuest()).isPresent()) {
            throw new TingTingCommonException("이미 초대한 유저입니다.");
        }

        // 3. 다른 파티에 초대된 유저 체크
        PartyGroup byGuest = partyGroupRepository.findByGuest(userInvitation.getGuest()).get();
        if ("1".equals(byGuest.getIsAccepted())) {
            throw new TingTingCommonException("초대할 수 없는 유저입니다.");
        }

        PartyGroup partyGroup = PartyGroup.builder()
                .host(host.getUserId())
                .guest(userInvitation.getGuest())
                .build();
        partyGroupRepository.save(partyGroup);
    }

    public void rejectInvitation(User user) {
        PartyGroup partyGroup = partyGroupRepository.findByGuest(user.getUsername())
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
    }

    public List<PartyUserResponse> getPartyUserList(User user) {
        com.date.tingting.domain.user.User host = userService.getUser(user.getUsername());
        List<PartyUserResponse> partyUserList = new ArrayList<>();

        // 1. 모든 유저 List에서 자기 자신 제외
        List<PartyUserInterface> userList = userRepository.getPartyUserList(host.getUserId(), "0").stream()
                .filter(u -> !u.getUserId().equals(host.getUserId()))
                .collect(Collectors.toList());

        // 2. 유저 filter
        // 2-1. [초대중] invitationState = "0" -> partyGroup TB에 host-guest가 존재
        // 2-2. [초대완료] invitationState = "-1" -> partyGroup TB에 host-guest가 존재 && isAccepted = "1"
        // 2-3. [초대가능] invitationState = "1"
        userList.forEach(u -> {
            PartyUserResponse partyUser = PartyUserResponse.builder()
                                            .userId(u.getUserId())
                                            .invitationState(u.getInvitationState())
                                            .build();
            Optional<PartyGroup> guest = partyGroupRepository.findByHostAndGuest(host.getUserId(), u.getUserId());
            if(guest.isPresent()) {
                partyUser.setInvitationState("0");

                if("1".equals(guest.get().getIsAccepted())) {
                    partyUser.setInvitationState("-1");
                }
            }
            partyUserList.add(partyUser);
        });

        return partyUserList;
    }

    public List<UserInvitation> getPartyGroup(User user) {
        com.date.tingting.domain.user.User me = userService.getUser(user.getUsername());
        List<UserInvitation> partyGroup = new ArrayList<>();

        PartyGroup host = partyGroupRepository.findByGuest(me.getUserId()).orElseThrow(
                () -> new TingTingCommonException("초대된 이력이 없습니다."));

        List<PartyUserInterface> userList = userRepository.getPartyUserList(host.getHost(), "0").stream()
                .filter(u -> !u.getUserId().equals(host.getHost()))
                .toList();

        userList.forEach(u -> {
            UserInvitation userInvitation = new UserInvitation();
            userInvitation.setHost(u.getUserId());

            Optional<PartyGroup> guest = partyGroupRepository.findByHostAndGuest(host.getHost(), u.getUserId());

            if(guest.isPresent()) {
                if("1".equals(guest.get().getIsAccepted())) {
                    userInvitation.setGuest(guest.get().getGuest());
                    partyGroup.add(userInvitation);
                }
            }
        });

        return partyGroup;
    }
}
