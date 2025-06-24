package com.reddit.vote;

import com.reddit.vote.dto.VoteDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votes")
public class VoteController {
    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping("/up-voted/users/{userId}")
    public ResponseEntity<Page<VoteDto>> getUpVotedByUserId(
            @PathVariable Long userId,
            @PageableDefault(size = 25, sort = "created", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<VoteDto> votes = voteService.getUpVotedByUserId(userId, pageable);

        return ResponseEntity
                .ok()
                .body(votes);
    }

    @GetMapping("/down-voted/users/{userId}")
    public ResponseEntity<Page<VoteDto>> getDownVotedByUserId(
            @PathVariable Long userId,
            @PageableDefault(size = 25, sort = "created", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<VoteDto> votes = voteService.getDownVotedByUserId(userId, pageable);

        return ResponseEntity
                .ok()
                .body(votes);
    }

    @PostMapping
    public ResponseEntity<VoteDto> toggleVote(@RequestBody @Valid VoteDto voteDto) {
        VoteDto result = voteService.toggleVote(voteDto);
        return ResponseEntity
                .ok()
                .body(result);
    }
}
