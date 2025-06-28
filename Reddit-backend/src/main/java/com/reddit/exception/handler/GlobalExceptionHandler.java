package com.reddit.exception.handler;

import com.reddit.exception.comment.CommentIsDeletedException;
import com.reddit.exception.comment.CommentNotFoundException;
import com.reddit.exception.content.ContentNotFoundException;
import com.reddit.exception.content.ContentUpdateNotAllowedException;
import com.reddit.exception.post.PostNotFoundException;
import com.reddit.exception.subreddit.MissingModeratorPrivilegesException;
import com.reddit.exception.subreddit.SubredditAlreadyExistsException;
import com.reddit.exception.subreddit.SubredditNotFoundException;
import com.reddit.exception.user.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CommentIsDeletedException.class)
    public ResponseEntity<ErrorObject> handleCommentIsDeletedException(CommentIsDeletedException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorObject> handleCommentNotFoundException(CommentNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ContentNotFoundException.class)
    public ResponseEntity<ErrorObject> handleContentNotFoundException(ContentNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ContentUpdateNotAllowedException.class)
    public ResponseEntity<ErrorObject> handleContentUpdateNotAllowedException(ContentUpdateNotAllowedException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MissingModeratorPrivilegesException.class)
    public ResponseEntity<ErrorObject> handleMissingModeratorPrivilegesException(MissingModeratorPrivilegesException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(PasswordsDoNotMatchException.class)
    public ResponseEntity<ErrorObject> handlePasswordsDoNotMatchException(PasswordsDoNotMatchException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NewPassWordCannotBeOldPasswordException.class)
    public ResponseEntity<ErrorObject> handlePasswordsDoNotMatchException(NewPassWordCannotBeOldPasswordException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorObject> handlePostNotFoundException(PostNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SubredditAlreadyExistsException.class)
    public ResponseEntity<ErrorObject> handleSubredditAlreadyExistsException(SubredditAlreadyExistsException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SubredditNotFoundException.class)
    public ResponseEntity<ErrorObject> handleSubredditNotFoundException(SubredditNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorObject> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorObject> handleUserNotFoundException(UserNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotSubscribedException.class)
    public ResponseEntity<ErrorObject> handleUserNotSubscribedException(UserNotSubscribedException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorObject> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        BindingResult bindingResult = ex.getBindingResult();
        bindingResult
                .getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorObject> buildResponse(String message, HttpStatus status) {
        return new ResponseEntity<>(new ErrorObject(message, status.value()), status);
    }
}
