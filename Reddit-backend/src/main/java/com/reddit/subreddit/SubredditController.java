package com.reddit.subreddit;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subreddits")
public class SubredditController {
    private final SubredditService subredditService;

    public SubredditController(SubredditService subredditService) {
        this.subredditService = subredditService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubredditDto> getSubreddit(@PathVariable Long id) {
        SubredditDto subredditDto = subredditService.getSubreddit(id);
        return ResponseEntity
                .ok()
                .body(subredditDto);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<SubredditDto>> getSubredditsWhereTitleContainsWord(
            @RequestParam String word,
            @PageableDefault(size = 25, sort = "title", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<SubredditDto> subreddits = subredditService.getSubredditsWhereTitleContainsWord(word, pageable);

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
        SubredditDto subredditDto = subredditService.updateSubredditTitle(subredditId, subredditUpdateTitleDto.title(), subredditUpdateTitleDto.moderatorId());
        return ResponseEntity
                .ok()
                .body(subredditDto);
    }

    @PutMapping("/{subredditId}/moderators/add")
    public ResponseEntity<SubredditDto> addSubredditModerator(@PathVariable Long subredditId, @RequestBody @Valid ModeratorUpdateDto moderatorUpdateDto) {
        SubredditDto subredditDto = subredditService.addSubredditModerator(subredditId, moderatorUpdateDto.moderatorId(), moderatorUpdateDto.updatedModerator());
        return ResponseEntity
                .ok()
                .body(subredditDto);
    }

    @PutMapping("/{subredditId}/moderators/remove")
    public ResponseEntity<SubredditDto> removeSubredditModerator(@PathVariable Long subredditId, @RequestBody @Valid ModeratorUpdateDto moderatorUpdateDto) {
        SubredditDto subredditDto = subredditService.removeSubredditModerator(subredditId, moderatorUpdateDto.moderatorId(), moderatorUpdateDto.updatedModerator());
        return ResponseEntity
                .ok()
                .body(subredditDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubreddit(@PathVariable Long id) {
        subredditService.deleteSubreddit(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
