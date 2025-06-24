package com.reddit.vote;

import com.reddit.vote.dto.VoteDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoteController {
    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping
    public ResponseEntity<VoteDto> toggleVote(@RequestBody @Valid VoteDto voteDto) {
        VoteDto result = voteService.toggleVote(voteDto);
        return ResponseEntity
                .ok()
                .body(result);
    }
}
