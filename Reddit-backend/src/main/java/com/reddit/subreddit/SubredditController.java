package com.reddit.subreddit;

import com.reddit.subreddit.dto.SubredditDto;
import com.reddit.subreddit.dto.SubredditUpdateTitleDto;
import com.reddit.util.PaginationConstants;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddits")
public class SubredditController {
    private final SubredditService subredditService;

    public SubredditController(SubredditService subredditService) {
        this.subredditService = subredditService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<SubredditDto> getSubreddit(@PathVariable Long id) {
        SubredditDto subredditDto = subredditService.getSubredditById(id);
        return ResponseEntity
                .ok()
                .body(subredditDto);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<SubredditDto> getSubredditByTitle(@PathVariable String title) {
        SubredditDto subredditDto = subredditService.getSubredditByTitle(title);
        return ResponseEntity
                .ok()
                .body(subredditDto);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<SubredditDto>> getSubredditsWhereTitleContainsWord(
            @RequestParam String word,
            @PageableDefault(
                    size = PaginationConstants.SUBREDDIT_BY_TITLE_CONTAINS_SIZE,
                    sort = PaginationConstants.SUBREDDIT_BY_TITLE_CONTAINS_SORT,
                    direction = Sort.Direction.ASC) Pageable pageable) {
        Page<SubredditDto> subreddits = subredditService.getSubredditsWhereTitleContainsWord(word, pageable);

        return ResponseEntity
                .ok()
                .body(subreddits);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<SubredditDto>> getSubredditsByUserId(@PathVariable Long userId) {
        List<SubredditDto> subreddits = subredditService.getSubredditsByUserId(userId);
        return ResponseEntity
                .ok()
                .body(subreddits);
    }

    @GetMapping("/moderators/{moderatorId}")
    public ResponseEntity<List<SubredditDto>> getSubredditsByModeratorId(@PathVariable Long moderatorId) {
        List<SubredditDto> subreddits = subredditService.getSubredditsByModeratorId(moderatorId);
        return ResponseEntity
                .ok()
                .body(subreddits);
    }

    @PostMapping
    public ResponseEntity<SubredditDto> addSubreddit(@RequestBody @Valid SubredditDto subredditDto, @RequestParam Long creatorId) {
        SubredditDto resultSubredditDto = subredditService.addSubreddit(subredditDto, creatorId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resultSubredditDto);
    }

    @PutMapping("/{subredditId}/title")
    public ResponseEntity<SubredditDto> updateSubredditTitle(@PathVariable Long subredditId, @RequestBody @Valid SubredditUpdateTitleDto subredditUpdateTitleDto) {
        SubredditDto subredditDto = subredditService.updateSubredditTitle(subredditId, subredditUpdateTitleDto);
        return ResponseEntity
                .ok()
                .body(subredditDto);
    }

    @PutMapping("/{subredditTitle}/users/add")
    public ResponseEntity<SubredditDto> addSubredditToUserSubscriptions(@PathVariable String subredditTitle, @RequestParam Long userId) {
        SubredditDto subredditDto = subredditService.addSubredditToUserSubscriptions(subredditTitle, userId);
        return ResponseEntity
                .ok()
                .body(subredditDto);
    }

    @PutMapping("/{subredditTitle}/users/remove")
    public ResponseEntity<SubredditDto> removeSubredditFromUserSubscriptions(@PathVariable String subredditTitle, @RequestParam Long userId) {
        SubredditDto subredditDto = subredditService.removeSubredditFromUserSubscriptions(subredditTitle, userId);
        return ResponseEntity
                .ok()
                .body(subredditDto);
    }

    @DeleteMapping("/{subredditTitle}")
    public ResponseEntity<Void> deleteSubreddit(@PathVariable String subredditTitle, @RequestParam Long moderatorId) {
        subredditService.deleteSubreddit(subredditTitle, moderatorId);
        return ResponseEntity
                .noContent()
                .build();
    }
}
