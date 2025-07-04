package com.reddit.savedcontent;

import com.reddit.savedcontent.dto.SavedContentDto;
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

    @GetMapping("/{userId}")
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
    public ResponseEntity<SavedContentDto> toggleSavedContent(@RequestBody @Valid SavedContentDto savedContentDto) {
        SavedContentDto result = savedContentService.toggleSavedContent(savedContentDto);
        return ResponseEntity
                .ok()
                .body(result);
    }
}
