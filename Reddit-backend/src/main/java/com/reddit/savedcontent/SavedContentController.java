package com.reddit.savedcontent;

import com.reddit.savedcontent.dto.SavedContentDto;
import com.reddit.savedcontent.dto.SavedDto;
import com.reddit.util.PaginationConstants;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/saved")
public class SavedContentController {
    private final SavedContentService savedContentService;

    public SavedContentController(SavedContentService savedContentService) {
        this.savedContentService = savedContentService;
    }

    @GetMapping
    public ResponseEntity<SavedDto> getSavedByContentAndUser(@RequestParam Long contentId, @RequestParam Long userId) {
        SavedDto savedDto = savedContentService.getSavedByContentAndUser(contentId, userId);
        return ResponseEntity
                .ok()
                .body(savedDto);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Page<SavedContentDto>> getSavedContentByUserId(
            @PathVariable Long userId,
            @PageableDefault(
                    size = PaginationConstants.SAVED_CONTENT_BY_USER_ID_SIZE,
                    sort = PaginationConstants.SAVED_CONTENT_BY_USER_ID_SORT,
                    direction = Sort.Direction.DESC) Pageable pageable) {
        Page<SavedContentDto> savedContents = savedContentService.getSavedContentByUserId(userId, pageable);

        return ResponseEntity
                .ok()
                .body(savedContents);
    }

    @PostMapping
    public ResponseEntity<SavedDto> toggleSavedContent(@RequestBody @Valid SavedDto savedDto) {
        SavedDto result = savedContentService.toggleSavedContent(savedDto);
        return ResponseEntity
                .ok()
                .body(result);
    }
}
