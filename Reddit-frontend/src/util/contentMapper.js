export const contentToPost = (content) => {
    const post = {
        'id': content.id, 
        'title': content.title, 
        'user': { 
            'id': content.userDto.id, 
            'username': content.userDto.username 
        },
        'created': content.created,
        'score': content.score,
        'description': content.text,
        'subreddit': { 
            'id': content.subredditDto.id, 
            'title': content.subredditDto.title 
        } 
    }

    return post
}