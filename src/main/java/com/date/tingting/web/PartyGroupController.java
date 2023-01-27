package com.date.tingting.web;

import com.date.tingting.response.ResponseService;
import com.date.tingting.response.TingTingResponse;
import com.date.tingting.service.PartyGroupService;
import com.date.tingting.web.requestDto.UserInvitation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/party")
public class PartyGroupController {
    @Autowired
    private final PartyGroupService partyGroupService;
    @Autowired
    private final ResponseService responseService;

    @GetMapping("/user/list")
    public TingTingResponse getPartyUserList(@AuthenticationPrincipal User user) {
        return responseService.getTingTingResponse(partyGroupService.getPartyUserList(user));
    }

    @PostMapping("/invitation")
    public TingTingResponse addGuest(@AuthenticationPrincipal User user, @RequestBody UserInvitation userInvitation) {
        partyGroupService.addGuest(user, userInvitation);
        return responseService.getTingTingResponse("초대 완료");
    }

    @GetMapping("/host/list")
    public TingTingResponse getHostList(@AuthenticationPrincipal User user) {
        return responseService.getTingTingResponse(partyGroupService.getHostList(user));
    }

    @PostMapping("/accept")
    public TingTingResponse acceptInvitation(@AuthenticationPrincipal User user) {
        partyGroupService.acceptInvitation(user);
        return responseService.getTingTingResponse("초대 승락 완료");
    }

}
