package com.reddit.vote;

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
    public ResponseEntity<Void> voteForContent(@RequestBody @Valid VoteDto voteDto) {
        voteService.voteForContent(voteDto);
        return ResponseEntity
                .noContent()
                .build();
    }
}
