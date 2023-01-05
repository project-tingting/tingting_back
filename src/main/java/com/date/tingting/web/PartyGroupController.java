package com.date.tingting.web;

import com.date.tingting.response.ResponseService;
import com.date.tingting.response.TingTingResponse;
import com.date.tingting.service.PartyGroupService;
import com.date.tingting.web.requestDto.UserInvitation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PartyGroupController {
    @Autowired
    private final PartyGroupService partyGroupService;
    @Autowired
    private final ResponseService responseService;

    @GetMapping("/party/user/list")
    public TingTingResponse getPartyUserList(@AuthenticationPrincipal User user) {
        return responseService.getTingTingResponse(partyGroupService.getPartyUserList(user));
    }

    @PostMapping("/party/invitation")
    public TingTingResponse addGuest(UserInvitation userInvitation) {
        partyGroupService.addGuest(userInvitation);
        return responseService.getTingTingResponse("초대 완료");
    }

    @PostMapping("/party/accept")
    public TingTingResponse acceptInvitation(UserInvitation userInvitation) {
        partyGroupService.acceptInvitation(userInvitation);
        return responseService.getTingTingResponse("초대 승락 완료");
    }

}
