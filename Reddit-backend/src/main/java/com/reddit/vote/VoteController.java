package com.reddit.vote;

import com.reddit.util.PaginationConstants;
import com.reddit.vote.dto.VoteDto;
import com.reddit.vote.dto.VotedContentDto;
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

    @GetMapping
    public ResponseEntity<VoteDto> getVoteByContentAndUser(@RequestParam Long contentId, @RequestParam Long userId) {
        VoteDto voteDto = voteService.getVoteByContentAndUser(contentId, userId);
        return ResponseEntity
                .ok()
                .body(voteDto);
    }

    @GetMapping("/up-voted/users/{userId}")
    public ResponseEntity<Page<VotedContentDto>> getUpVotedByUserId(
            @PathVariable Long userId,
            @PageableDefault(
                    size = PaginationConstants.VOTE_DEFAULT_SIZE,
                    sort = PaginationConstants.VOTE_DEFAULT_SORT,
                    direction = Sort.Direction.DESC) Pageable pageable) {
        Page<VotedContentDto> votes = voteService.getUpVotedByUserId(userId, pageable);

        return ResponseEntity
                .ok()
                .body(votes);
    }

    @GetMapping("/down-voted/users/{userId}")
    public ResponseEntity<Page<VotedContentDto>> getDownVotedByUserId(
            @PathVariable Long userId,
            @PageableDefault(
                    size = PaginationConstants.VOTE_DEFAULT_SIZE,
                    sort = PaginationConstants.VOTE_DEFAULT_SORT,
                    direction = Sort.Direction.DESC) Pageable pageable) {
        Page<VotedContentDto> votes = voteService.getDownVotedByUserId(userId, pageable);

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
